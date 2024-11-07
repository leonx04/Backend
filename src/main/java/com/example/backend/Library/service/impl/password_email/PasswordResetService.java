package com.example.backend.Library.service.impl.password_email;

import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.repository.employee.EmployeeRepo;
import com.example.backend.Library.repository.customer.CustomerRepository;
import com.example.backend.Library.service.interfaces.password_email.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class PasswordResetService implements IPasswordResetService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepo employeeRepository;
    private final IEmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;

    // Thời gian hiệu lực của mã OTP
    private static final Duration OTP_VALIDITY = Duration.ofSeconds(60);

    public PasswordResetService(CustomerRepository customerRepository, IEmailService emailService, PasswordEncoder passwordEncoder, CacheManager cacheManager, EmployeeRepo employeeRepository) {
        this.customerRepository = customerRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
        this.employeeRepository = employeeRepository;
    }

    // Khởi tạo quá trình đặt lại mật khẩu
    @Override
    public void initiatePasswordReset(HttpServletRequest request, String email) {
        // Kiểm tra xem email có tồn tại trong hệ thống không
        Object exists = null;
        String check = null;
        if (request.getRequestURI().startsWith("/api/ecm/admin")) {
            exists = employeeRepository.findByEmail(email);
            check = "admin";
        } else if (request.getRequestURI().startsWith("/api/ecm/user")) {
            exists = customerRepository.findByEmail(email);
            check = "customer";
        }
        System.out.println(request.getRequestURI() + " " + check);

        // Nếu email tồn tại
        if (exists != null) {
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
        Cache cache = cacheManager.getCache("passwordResetOTPs"); // passwordResetOTPs là tên cache
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
    public Map<String, String> resetPassword(HttpServletRequest request, String email, String newPassword, String otp) {
        Map<String, String> response = new HashMap<>();

        Optional<Employee> existsEmployee = null;
        Optional<Customer> existsCustomer = null;

        // Kiểm tra xem email có tồn tại trong hệ thống không
        boolean check = false;
        if (request.getRequestURI().startsWith("/api/ecm/admin")) {
            existsEmployee = employeeRepository.findByEmail(email);
            check = true;
        } else if (request.getRequestURI().startsWith("/api/ecm/user")) {
            existsCustomer = customerRepository.findByEmail(email);
            check = false;
        }
        System.out.println(request.getRequestURI() + " " + check + "true = admin, false = customer");

        if (check) {
            if (existsEmployee.isPresent()) {
                Employee employee = existsEmployee.get();
                // Kiểm tra mật khẩu mới có hợp lệ không
                if (newPassword == null || newPassword.isEmpty()) {
                    response.put("message", "Mật khẩu không được để trống.");
                    response.put("status", "error");
                    return response;
                }
                // Kiểm tra mã OTP có hợp lệ không
                if (!validateOTP(email, otp)) {
                    response.put("message", "Mã OTP không hợp lệ hoặc đã hết hạn.");
                    response.put("status", "error");
                    return response;
                }
                // Đặt lại mật khẩu
                employee.setPassWord(passwordEncoder.encode(newPassword));
                // Lưu thông tin khách hàng
                employeeRepository.save(employee);

                // Xóa mã OTP khỏi cache
                Cache cache = cacheManager.getCache("passwordResetOTPs");
                // Nếu cache tồn tại
                if (cache != null) {
                    // Xóa mã OTP khỏi cache
                    cache.evict(email);
                }

                // Chuẩn bị phản hồi trả về cho client
                response.put("message", "Mật khẩu đã được đặt lại thành công.");
                response.put("status", "success");

                // Gửi email thông báo mật khẩu đã được đặt lại thành công
                sendPasswordResetSuccessEmail(employee.getEmail());
                return response;
            } else {
                response.put("message", "Không tìm thấy tài khoản với email này.");
                return response;
            }
        } else {
            if (existsCustomer.isPresent()) {
                Customer customer = existsCustomer.get();
                // Kiểm tra mật khẩu mới có hợp lệ không
                if (newPassword == null || newPassword.isEmpty()) {
                    response.put("message", "Mật khẩu không được để trống.");
                    response.put("status", "error");
                    return response;
                }
                // Kiểm tra mã OTP có hợp lệ không
                if (!validateOTP(email, otp)) {
                    response.put("message", "Mã OTP không hợp lệ hoặc đã hết hạn.");
                    response.put("status", "error");
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
                response.put("status", "success");

                // Gửi email thông báo mật khẩu đã được đặt lại thành công
                sendPasswordResetSuccessEmail(customer.getEmail());
                return response;
            } else {
                response.put("message", "Không tìm thấy tài khoản với email này.");
                return response;
            }
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

    // Tạo nội dung email thông báo mật khẩu đã được đặt lại thành công
    private String createPasswordResetSuccessEmailBody() {
        return "Chào bạn,\n\n" +
                "Chúng tôi xin thông báo rằng mật khẩu của tài khoản của bạn tại Shop 360 Sneaker đã được đặt lại thành công.\n\n" +
                "Nếu bạn không thực hiện yêu cầu này, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi ngay lập tức.\n\n" +
                "Trân trọng,\n" +
                "Đội ngũ hỗ trợ\n" +
                "Shop 360 Sneaker\n\n" +
                "Lưu ý: Đây là email tự động, vui lòng không trả lời email này.";
    }

    // Sinh mã OTP
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
                "Chào bạn,\n\n" +
                        "Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại Shop 360 Sneaker.\n\n" +
                        "Mã OTP của bạn là: %s\n\n" +
                        "Lưu ý quan trọng:\n" +
                        "1. Mã OTP này sẽ hết hạn sau 50 giây kể từ khi yêu cầu được gửi.\n" +
                        "2. Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này và liên hệ ngay với bộ phận hỗ trợ của chúng tôi.\n" +
                        "3. Vì lý do bảo mật, chúng tôi khuyến nghị bạn không chia sẻ mã OTP này với bất kỳ ai.\n\n" +
                        "Nếu bạn gặp bất kỳ vấn đề nào trong quá trình đặt lại mật khẩu, đừng ngần ngại liên hệ với đội ngũ hỗ trợ của chúng tôi.\n\n" +
                        "Trân trọng,\n" +
                        "Đội ngũ hỗ trợ\n" +
                        "Shop 360 Sneaker\n\n" +
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
