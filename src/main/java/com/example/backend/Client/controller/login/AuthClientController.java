package com.example.backend.Client.controller.login;

import com.example.backend.Library.model.dto.request.auth.RegisterRequest;
import com.example.backend.Library.security.auth.login.Account;
import com.example.backend.Library.service.interfaces.customer.ICustomerService;
import com.example.backend.Library.service.interfaces.password_email.IPasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/${api.prefix}/user/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthClientController {
    private static final Logger logger = LoggerFactory.getLogger(AuthClientController.class);

    private final IPasswordResetService passwordResetService;
    private final ICustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final Account account;

    public AuthClientController(IPasswordResetService passwordResetService, ICustomerService customerService, PasswordEncoder passwordEncoder, Account account) {
        this.passwordResetService = passwordResetService;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.account = account;
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }

    // Method tạo tài khoản khách hàng
    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerCustomer(
            @Valid @ModelAttribute RegisterRequest request,
            BindingResult result) {
        Map response = new HashMap();
        logger.info("Register attempt for user: {}", request.getEmail());
        return ResponseEntity.ok(account.signupAccount(response, request, result));
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
            passwordResetService.resetPassword(request ,email, newPassword, retypePassword, otp);
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
