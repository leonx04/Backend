package com.example.backend.Library.service.impl;

import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.repository.customer.CustomerRepository;
import com.example.backend.Library.service.interfaces.IEmailService;
import com.example.backend.Library.service.interfaces.IPasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService implements IPasswordResetService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CacheManager cacheManager;

    private static final Duration OTP_VALIDITY = Duration.ofSeconds(60);

    // Khởi tạo quá trình đặt lại mật khẩu
    @Override
    public void initiatePasswordReset(String email) {
        // Kiểm tra xem email có tồn tại trong hệ thống không
        Optional<Customer> existsCustomer = customerRepository.findByEmail(email);
        // Nếu email tồn tại
        if (existsCustomer.isPresent()) {
            // Sinh mã OTP
            String otp = generateOTP();
            // Lưu mã OTP vào cache
            cacheOTP(email, otp);
            // Gửi email chứa mã OTP đến email của khách hàng
            String emailBody = createEmailBody(otp);
            // Gửi email
            emailService.sendEmail(email, emailBody, "Cảnh báo: Mã OTP để đặt lại mật khẩu cho tài khoản ở Shop Shoes JN");
        }
    }

    // Sinh mã OTP
    @Override
    public boolean validateOTP(String email, String otp) {
        // Lấy cache chứa mã OTP
        Cache cache = cacheManager.getCache("passwordResetOTPs");
        // Nếu cache tồn tại
        if (cache != null) {
            // Lấy mã OTP từ cache
            Cache.ValueWrapper wrapper = cache.get(email);
            if (wrapper != null) {
                // Lấy thông tin mã OTP
                OTPInfo otpInfo = (OTPInfo) wrapper.get();
                // Kiểm tra mã OTP có hợp lệ không
                return otpInfo != null && otpInfo.otp.equals(otp) && Instant.now().isBefore(otpInfo.expiryTime);
            }
        }
        return false;
    }

    // Đặt lại mật khẩu
    @Override
    public Map<String, String> resetPassword(String email, String newPassword) {
        // Kiểm tra xem email có tồn tại trong hệ thống không
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        Map<String, String> response = new HashMap<>();
        // Nếu email tồn tại
        if (customerOpt.isPresent()) {
            // Lấy thông tin khách hàng
            Customer customer = customerOpt.get();
            // Kiểm tra mật khẩu mới có hợp lệ không
            if (newPassword == null || newPassword.isEmpty()) {
                response.put("message", "Mật khẩu không được để trống.");
                return response;
            }
            // Đặt lại mật khẩu
            customer.setPassword(passwordEncoder.encode(newPassword));
            // Lưu thông tin khách hàng
            customerRepository.save(customer);

            // Xóa mã OTP khỏi cache
            Cache cache = cacheManager.getCache("passwordResetOTPs");
            // Nếu cache tồn tại
            if (cache != null) {
                // Xóa mã OTP khỏi cache
                cache.evict(email);
            }

            // Chuẩn bị phản hồi trả về cho client
            response.put("message", "Mật khẩu đã được đặt lại thành công.");

            // Gửi email thông báo mật khẩu đã được đặt lại thành công
            sendPasswordResetSuccessEmail(customer.getEmail());
            return response;
        } else {
            response.put("message", "Không tìm thấy tài khoản với email này.");
            return response;
        }
    }

    // Gửi email thông báo mật khẩu đã được đặt lại thành công
    @Override
    public void sendPasswordResetSuccessEmail(String email) {
        // Tạo nội dung email
        String emailBody = createPasswordResetSuccessEmailBody();
        // Gửi email
        emailService.sendEmail(email, emailBody, "Thông báo: Mật khẩu của bạn đã được đặt lại thành công");
    }

    private String createPasswordResetSuccessEmailBody() {
        return "Kính gửi Quý khách,\n\n" +
                "Chúng tôi xin thông báo rằng mật khẩu của tài khoản của bạn tại Shop Shoes JN đã được đặt lại thành công.\n\n" +
                "Nếu bạn không thực hiện yêu cầu này, vui lòng liên hệ với bộ phận hỗ trợ khách hàng của chúng tôi ngay lập tức.\n\n" +
                "Trân trọng,\n" +
                "Đội ngũ hỗ trợ khách hàng\n" +
                "Shop Shoes JN\n\n" +
                "Lưu ý: Đây là email tự động, vui lòng không trả lời email này.";
    }

    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private void cacheOTP(String email, String otp) {
        Cache cache = cacheManager.getCache("passwordResetOTPs");
        if (cache != null) {
            cache.put(email, new OTPInfo(otp, Instant.now().plus(OTP_VALIDITY)));
        }
    }

    private String createEmailBody(String otp) {
        return String.format(
                "Kính gửi Quý khách,\n\n" +
                        "Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại Shop Shoes JN.\n\n" +
                        "Mã OTP của bạn là: %s\n\n" +
                        "Lưu ý quan trọng:\n" +
                        "1. Mã OTP này sẽ hết hạn sau 50 giây kể từ khi yêu cầu được gửi.\n" +
                        "2. Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này và liên hệ ngay với bộ phận hỗ trợ khách hàng của chúng tôi.\n" +
                        "3. Vì lý do bảo mật, chúng tôi khuyến nghị bạn không chia sẻ mã OTP này với bất kỳ ai.\n\n" +
                        "Nếu bạn gặp bất kỳ vấn đề nào trong quá trình đặt lại mật khẩu, đừng ngần ngại liên hệ với đội ngũ hỗ trợ của chúng tôi.\n\n" +
                        "Trân trọng,\n" +
                        "Đội ngũ hỗ trợ khách hàng\n" +
                        "Shop Shoes JN\n\n" +
                        "Lưu ý: Đây là email tự động, vui lòng không trả lời email này.",
                otp
        );
    }

    // Lớp chứa thông tin mã OTP
    private static class OTPInfo {
        private final String otp;
        private final Instant expiryTime;

        public OTPInfo(String otp, Instant expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}
