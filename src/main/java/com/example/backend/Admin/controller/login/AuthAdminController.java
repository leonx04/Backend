package com.example.backend.Admin.controller.login;

import com.example.backend.Library.service.impl.employee.EmployeeService;
import com.example.backend.Library.service.interfaces.password_email.IPasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/${api.prefix}/admin/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthAdminController {
    private static final Logger logger = LoggerFactory.getLogger(AuthAdminController.class);

    private final IPasswordResetService passwordResetService;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    public AuthAdminController(IPasswordResetService passwordResetService, EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.passwordResetService = passwordResetService;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }

    // Quên mật khẩu
    @PostMapping("/get-otp")
    public ResponseEntity<Object> forgotPassword(HttpServletRequest request, @RequestParam("email") String email) {
        Map<String, String> response = new HashMap<>();
        // Kiểm tra định dạng email
        if (!isValidEmail(email)) {
            response.put("message", "Email không hợp lệ.");
            response.put("status", "error");

            return ResponseEntity.ok(response);
        }

        // Kiểm tra email có tồn tại trong hệ thống không
        if (employeeService.findByEmail(email) == null) {
            logger.warn("Không có tài khoản nào liên kết với email: {}", email);
            response.put("message", "Không tìm thấy tài khoản với email này.");
            response.put("status", "error");

            return ResponseEntity.ok(response);
        }
        // Gửi mã OTP đến email
        passwordResetService.initiatePasswordReset(request, email);
        // Trả về thông báo thành công
        response.put("message", "Mã OTP đã được gửi đến email của bạn.");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    // Xác thực mã OTP
//    @PostMapping("/validate-otp")
//    public ResponseEntity<String> validateOTP(@RequestParam String email, @RequestParam String otp) {
//        // Kiểm tra mã OTP có hợp lệ không
//        if (passwordResetService.validateOTP(email, otp)) { // Nếu mã OTP hợp lệ
//            // Trả về thông báo thành công
//            return ResponseEntity.ok("Mã OTP hợp lệ.");
//        } else {
//            // Trả về thông báo lỗi
//            return ResponseEntity.badRequest().body("Mã OTP không hợp lệ hoặc đã hết hạn.");
//        }
//    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            HttpServletRequest request,
            @RequestBody Map<String, String> requestParam) {
        String email = requestParam.get("email");
        String otp = requestParam.get("otp");
        String newPassword = requestParam.get("password");
        String confirmPassword = requestParam.get("confirmPassword");

        Map<String, String> response = new HashMap<>();

        try {
            // Kiểm tra mật khẩu mới có hợp lệ không
            if (newPassword.length() < 8) {
                response.put("message", "Mật khẩu phải chứa ít nhất 8 ký tự.");
                response.put("status", "errorPassword");
                return ResponseEntity.ok(response);
            }
            if (!newPassword.equals(confirmPassword)) {
                response.put("message", "Mật khẩu không khớp.");
                response.put("status", "errorPassword");
                return ResponseEntity.ok(response);
            }
            passwordResetService.resetPassword(request ,email, newPassword, otp);
            response.put("message", "Đặt lại mật khẩu thành công.");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

}
