/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Library.security.auth.login;

import com.example.backend.Library.service.impl.customer.CustomerService;
import com.example.backend.Library.service.impl.employee.EmployeeService;
import com.example.backend.Library.service.impl.password_email.PasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.backend.Library.validation.pts_validator.Validator.isValidEmail;

@Component
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired private PasswordResetService passwordResetService;
    @Autowired private EmployeeService employeeService;
    @Autowired private CustomerService customerService;

    // Kiểm tra email và tạo OTP
    public Map getOTP (HttpServletRequest request, String email) {
        Map response = new HashMap();
        String uri = request.getRequestURI();

        logger.info("OTP attempt for user: {}", email + " from " + uri);
        if (email == null || email.isEmpty()) {
            response.put("message", "Email không được để trống.");
            response.put("status", 400);
            return response;
        }

        if (!isValidEmail(email)) {
            response.put("message", "Email không hợp lệ.");
            response.put("status", 400);
            return response;
        }

        if (uri.contains("api/ecm/admin/auth") || uri.matches("/admin/")) {
            if (employeeService.findByEmail(email) == null) {
                response.put("message", "Không tìm thấy tài khoản với email này.");
                response.put("status", 400);
                return response;
            }
        }
        if (uri.contains("api/ecm/user/auth") || uri.matches("/user/")) {
            if (customerService.findByEmail(email).isEmpty()) {
                response.put("message", "Không tìm thấy tài khoản với email này.");
                response.put("status", 400);
                return response;
            }
        }
        return createOTP(response, email);
    }

    // Đặt lại mật khẩu
    public Map resetPassword(HttpServletRequest request, Map<String, String> params) {
        String email = params.get("email");
        String otp = params.get("otp");
        String newPassword = params.get("password");
        String confirmPassword = params.get("confirmPassword");
        System.out.println(params);

        return passwordResetService.resetPassword(request, email, newPassword, confirmPassword, otp);
    }

    // Tạo mã OTP
    private Map createOTP(Map response, String email) {
        try {
            passwordResetService.createAndSendOTP(email);
            logger.info("OTP sent to email: {}", email);
            response.put("message", "Mã OTP đã được gửi đến email của bạn.");
            response.put("status", 200);
        } catch (Exception e) {
            logger.error("Error sending OTP to email: {}", email + " " + e.getMessage());
            response.put("message", "Có lỗi xảy ra khi gửi mã OTP.");
            response.put("status", 400);
        }
        return response;
    }
}
