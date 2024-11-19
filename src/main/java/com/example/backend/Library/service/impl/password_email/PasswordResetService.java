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
    public void createAndSendOTP(String email) {
        // Sinh mã OTP
        String otp = generateOTP();
        // Lưu mã OTP vào cache
        cacheOTP(email, otp);
        // Gửi email chứa mã OTP đến email của khách hàng
        String emailBody = createEmailBody(otp);
        // Gửi email
        emailService.sendEmail(email, emailBody, "Cảnh báo: Mã OTP để đặt lại mật khẩu cho tài khoản ở Shop 360 Sneaker");
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
    public Map resetPassword(HttpServletRequest request, String email, String newPassword, String confirmPassword, String otp) {
        Map response = new HashMap<>();
        String uri = request.getRequestURI();
        try {
            if (uri.startsWith("/api/ecm/admin")) {
                System.out.println("admin");
                Optional<Employee> existsEmployee = employeeRepository.findByEmail(email);
                if (existsEmployee.isPresent()) {
                    Employee employee = existsEmployee.get();

                    validateCheck(response, email, newPassword, confirmPassword, otp);
                    if (response.get("status").equals(400)) {
                        System.out.println(response);
                        return response;
                    }
                    employee.setPassWord(passwordEncoder.encode(newPassword));
                    employeeRepository.save(employee);

                    return resetPasswordSuccess(response, email);
                }
            }
            if (uri.startsWith("/api/ecm/user")) {
                System.out.println("user");
                Optional<Customer> existsCustomer = customerRepository.findByEmail(email);
                if (existsCustomer.isPresent()) {
                    Customer customer = existsCustomer.get();

                    validateCheck(response, email, newPassword, confirmPassword, otp);
                    if (response.get("status").equals(400)) {
                        System.out.println(response);
                        return response;
                    }

                    customer.setPassword(passwordEncoder.encode(newPassword));
                    customerRepository.save(customer);

                    return resetPasswordSuccess(response, email);
                }
            }

            response.put("message", "Nhập sai email.");
            response.put("status", 400);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("status", 400);
            return response;
        }
    }

    // Gửi email thông báo mật khẩu đã được đặt lại thành công
    @Override
    public void sendPasswordResetSuccessEmail(String email) {
        // Tạo nội dung email
        String emailBody = createPasswordResetSuccessEmailBody();
        emailService.sendEmail(email, emailBody, "Thông báo: Mật khẩu của bạn đã được đặt lại thành công");
    }

    // Đặt lại mật khẩu thành công
    private Map resetPasswordSuccess(Map response, String email) {
        Cache cache = cacheManager.getCache("passwordResetOTPs");
        if (cache != null) {
            cache.evict(email); // Xóa mã OTP khỏi cache
        }
        sendPasswordResetSuccessEmail(email);

        response.put("message", "Mật khẩu đã được đặt lại thành công.");
        response.put("status", 200);
        return response;
    }

    // Hàm kiểm tra
    private Map validateCheck(Map response, String email, String newPassword, String confirmPassword, String otp) {
        // Kiểm tra mật khẩu mới có hợp lệ không
        if (newPassword == null || newPassword.isEmpty()) {
            response.put("message", "Mật khẩu không được để trống.");
            response.put("status", 400);
            return response;
        }

        // Kiểm tra mật khẩu mới có hợp lệ không
        if (newPassword.length() < 8) {
            response.put("message", "Mật khẩu phải chứa ít nhất 8 ký tự.");
            response.put("status", 400);
            return response;
        }

        // kiểm tra confirmPassword
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            response.put("message", "Nhập lại mật khẩu.");
            response.put("status", 400);
            return response;
        }

        // password and confirmPassword
        if (!newPassword.equals(confirmPassword)) {
            response.put("message", "Mật khẩu không khớp.");
            response.put("status", 400);
            return response;
        }

        // Kiểm tra mã OTP có hợp lệ không
        if (!validateOTP(email, otp)) {
            response.put("message", "Mã OTP không hợp lệ hoặc đã hết hạn.");
            response.put("status", 400);
            return response;
        }
        response.put("status", 200);
        return response;
    }

    // Tạo nội dung email thông báo mật khẩu đã được đặt lại thành công
    private String createPasswordResetSuccessEmailBody() {
        return "Chào bạn,\n\n" +
                "Chúng tôi xin thông báo rằng mật khẩu tài khoản bạn đã đăng ký tại Shop 360 Sneaker đã được đặt lại thành công.\n\n" +
                "Nếu bạn không thực hiện yêu cầu này, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi ngay lập tức.\n\n" +
                "Trân trọng,\n" +
                "Đội ngũ hỗ trợ\n" +
                "Shop 360 Sneaker\n\n" +
                "Lưu ý: Đây là email tự động, vui lòng không trả lời email này.";
    }

    // Tạo nội dung email thông báo thay đổi email thành công
//    private String createEmailChangeSuccessBody() {
//        return "Chào bạn,\n\n" +
//                "Chúng tôi xin thông báo rằng địa chỉ email của tài khoản của bạn tại Shop 360 Sneaker đã được thay đổi thành công.\n\n" +
//                "Nếu bạn không thực hiện yêu cầu này, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi ngay lập tức để bảo vệ tài khoản của bạn.\n\n" +
//                "Trân trọng,\n" +
//                "Đội ngũ hỗ trợ\n" +
//                "Shop 360 Sneaker\n\n" +
//                "Lưu ý: Đây là email tự động, vui lòng không trả lời email này.";
//    }

    // Sinh mã OTP
    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // Lưu mã OTP vào cache
    private void cacheOTP(String email, String otp) {
        Cache cache = cacheManager.getCache("passwordResetOTPs");
        if (cache != null) {
            cache.put(email, new OTPInfo(otp, Instant.now().plus(OTP_VALIDITY)));
        }
    }

    // Gửi mã OTP đến email
//    private void cacheEmailOTP(String email, String otp) {
//        Cache cache = cacheManager.getCache("changeEmailOTPs");
//        if (cache != null) {
//            cache.put(email, new OTPInfo(otp, Instant.now().plus(OTP_VALIDITY)));
//        }
//    }

    // Tạo nội dung email chứa mã OTP
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

    //
//    private String createEmailChangeEmailBody(String otp) {
//        return String.format(
//                "Chào bạn,\n\n" +
//                        "Chúng tôi đã nhận được yêu cầu thay đổi địa chỉ email cho tài khoản của bạn tại Shop 360 Sneaker.\n\n" +
//                        "Mã OTP của bạn để xác nhận thay đổi email là: %s\n\n" +
//                        "Lưu ý quan trọng:\n" +
//                        "1. Mã OTP này sẽ hết hạn sau 50 giây kể từ khi yêu cầu được gửi.\n" +
//                        "2. Nếu bạn không yêu cầu thay đổi địa chỉ email, vui lòng bỏ qua email này và liên hệ ngay với bộ phận hỗ trợ của chúng tôi.\n" +
//                        "3. Vì lý do bảo mật, chúng tôi khuyến nghị bạn không chia sẻ mã OTP này với bất kỳ ai.\n\n" +
//                        "Nếu bạn gặp bất kỳ vấn đề nào trong quá trình thay đổi email, đừng ngần ngại liên hệ với đội ngũ hỗ trợ của chúng tôi.\n\n" +
//                        "Trân trọng,\n" +
//                        "Đội ngũ hỗ trợ\n" +
//                        "Shop 360 Sneaker\n\n" +
//                        "Lưu ý: Đây là email tự động, vui lòng không trả lời email này.",
//                otp
//        );
//    }

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
