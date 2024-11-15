package com.example.backend.Library.service.payment;

import com.example.backend.Library.model.entity.orders.Order;
import com.example.backend.Library.repository.orders.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {
    private static final Logger logger = LoggerFactory.getLogger(VNPayService.class);

    private final OrderRepository orderRepository;

    @Value("${vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.payUrl}")
    private String vnp_PayUrl;

    @Value("${vnpay.returnUrl}")
    private String vnp_ReturnUrl;

    public VNPayService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String createPaymentUrl(int orderId, String ipAddress) {
        try {
            // Lấy thông tin đơn hàng
            Order order;
            order = orderRepository.findById(orderId).get(0);

            String vnp_TxnRef = String.valueOf(orderId);
            String vnp_IpAddr = getIpAddress(ipAddress);

            // Tạo thời gian giao dịch
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());

            // Tạo map chứa tham số
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", "2.1.0");
            vnp_Params.put("vnp_Command", "pay");
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(Math.round(order.getTotal() * 100)));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang " + order.getCode());
            vnp_Params.put("vnp_OrderType", "other");
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            // Sắp xếp tham số theo alphabet
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);

            // Tạo chuỗi hash
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                    // Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

                    // Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            // Tạo secure hash
            String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
            String queryUrl = query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;

            return vnp_PayUrl + "?" + queryUrl;
        } catch (Exception e) {
            logger.error("Error creating payment URL: ", e);
            throw new RuntimeException("Could not create payment URL");
        }
    }

    public boolean verifyPaymentResponse(Map<String, String> response) {
        try {
            // Tạo lại chuỗi hash từ dữ liệu trả về
            String vnp_SecureHash = response.get("vnp_SecureHash");
            String responseCode = response.get("vnp_ResponseCode");

            if (vnp_SecureHash == null || responseCode == null) {
                return false;
            }

            // Tạo map mới chỉ chứa các tham số cần thiết để verify
            Map<String, String> verifyParams = new HashMap<>(response);
            verifyParams.remove("vnp_SecureHash");
            verifyParams.remove("vnp_SecureHashType");

            // Sắp xếp tham số
            List<String> fieldNames = new ArrayList<>(verifyParams.keySet());
            Collections.sort(fieldNames);

            // Tạo chuỗi hash
            StringBuilder hashData = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = verifyParams.get(fieldName);
                if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
                    if (itr.hasNext()) {
                        hashData.append('&');
                    }
                }
            }

            // Verify hash
            String secureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
            boolean isValidHash = secureHash.equals(vnp_SecureHash);
            boolean isSuccessCode = "00".equals(responseCode);

            return isValidHash && isSuccessCode;
        } catch (Exception e) {
            logger.error("Error verifying payment response: ", e);
            return false;
        }
    }

    private String getIpAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            return "127.0.0.1";
        }
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            return "127.0.0.1";
        }
        if (ipAddress.contains(",")) {
            return ipAddress.split(",")[0].trim();
        }
        return ipAddress;
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            sha512_HMAC.init(secret_key);
            byte[] result = sha512_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("Error generating HMAC SHA-512", e);
            throw new RuntimeException("Could not generate hash");
        }
    }
}