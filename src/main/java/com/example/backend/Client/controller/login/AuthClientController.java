package com.example.backend.Client.controller.login;

import com.example.backend.Library.service.interfaces.ICustomerService;
import com.example.backend.Library.service.interfaces.IPasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/${api.prefix}/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthClientController {
    private static final Logger logger = LoggerFactory.getLogger(AuthClientController.class);

    @Autowired
    private IPasswordResetService passwordResetService;
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }

    // Quên mật khẩu
    @PostMapping("/forgot-password")
    public ResponseEntity<?> getOtp(HttpServletRequest request, @RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        // Kiem tra loi
        if (!isValidEmail(email)) {
            response.put("message", "Email không hợp lệ.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Kiểm tra email có tồn tại trong hệ thống không
        if (!customerService.existsByEmail(email)) {
            // Ghi log khi không tìm thấy tài khoản với email này
            logger.warn("Not found account with email: {}", email);
            // Trả về thông báo lỗi
            response.put("message", "Không tìm thấy tài khoản với email này.");
            // Trả về mã lỗi 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        // Gửi mã OTP đến email
        passwordResetService.initiatePasswordReset(request, email);
        // Trả về thông báo thành công
        return ResponseEntity.ok().body(Map.of("message", "Mã OTP đã được gửi đến email của bạn."));
    }

    // Xác thực mã OTP
    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOTP(@RequestParam String email, @RequestParam String otp) {
        // Kiểm tra mã OTP có hợp lệ không
        if (passwordResetService.validateOTP(email, otp)) { // Nếu mã OTP hợp lệ
            // Trả về thông báo thành công
            return ResponseEntity.ok("Mã OTP hợp lệ.");
        } else {
            // Trả về thông báo lỗi
            return ResponseEntity.badRequest().body("Mã OTP không hợp lệ hoặc đã hết hạn.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(
            HttpServletRequest request,
            @RequestParam("email") String email,
            @RequestParam("otp") String otp,
            @RequestParam("password") String newPassword,
            @RequestParam("reTypePassword") String retypePassword) {

        Map<String, String> response = new HashMap<>();
        // Kiểm tra mật khẩu mới có hợp lệ không
        if (newPassword.length() < 8) {
            response.put("message", "Mật khẩu phải chứa ít nhất 8 ký tự.");
            return ResponseEntity.badRequest().body(response);
        }
        if (!newPassword.equals(retypePassword)) {
            response.put("message", "Mật khẩu không khớp.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            passwordResetService.resetPassword(request ,email, newPassword, otp);
            response.put("message", "Đặt lại mật khẩu thành công.");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Đặt lại mật khẩu thất bại.");
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

}
