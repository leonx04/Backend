/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Client.controller.login;

import com.example.backend.Library.model.dto.request.auth.LoginRequest;
import com.example.backend.Library.security.auth.login.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/user/auth")
@CrossOrigin(origins = "http://127.0.0.1:5501/")
public class LoginClientController {
    private static final Logger logger = LoggerFactory.getLogger(LoginClientController.class);

    private final Account account;

    public LoginClientController(Account account) {
        this.account = account;
    }

    // Method đăng nhập => sử lý yêu cầu đăng nhập
    @PostMapping("login")
    public ResponseEntity<?> loginCustomer(
            HttpServletRequest request,
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult result) {
        logger.info("Login attempt for user: {}", loginRequest.getEmail());
        return ResponseEntity.ok(account.loginAccount(request, loginRequest, result));
    }

    // Method đăng xuất
    @PostMapping("logout")
    public ResponseEntity<?> logoutAccount() {
        return ResponseEntity.ok(account.logoutAccount());
    }

    // Method kiểm tra token, tạo token mới
    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken (
            HttpServletRequest request,
            @RequestParam("param") String refreshToken) {
        return ResponseEntity.ok(account.refreshToken(request, refreshToken));
    }

}









