/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Client.controller.login;

import com.example.backend.Library.model.dto.request.auth.RegisterRequest;
import com.example.backend.Library.security.auth.login.Account;
import com.example.backend.Library.security.auth.login.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/${api.prefix}/user/auth")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class AuthClientController {
    private static final Logger logger = LoggerFactory.getLogger(AuthClientController.class);

    @Autowired private AuthService authService;
    @Autowired private Account account;


    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }

    // Method tạo tài khoản khách hàng
    @PostMapping(value = "register")
    public ResponseEntity<?> registerCustomer(
            @Valid @RequestBody RegisterRequest request,
            BindingResult result) {
        logger.info("Register attempt for user: {}", request.getEmail());
        return ResponseEntity.ok(account.signupAccount(request, result));
    }

    // Quên mật khẩu
    @PostMapping("get-otp")
    public ResponseEntity<?> createOTP(
            HttpServletRequest request,
            @RequestParam(name = "email", required = false) String email) {
        return ResponseEntity.ok(authService.getOTP(request, email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map> resetPassword(
            HttpServletRequest request,
            @RequestBody Map<String, String> requestPassword) {
        return ResponseEntity.ok(authService.resetPassword(request, requestPassword));
    }

}
