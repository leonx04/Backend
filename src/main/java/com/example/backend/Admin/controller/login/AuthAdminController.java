/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Admin.controller.login;

import com.example.backend.Library.security.auth.login.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/${api.prefix}/admin/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthAdminController {

    @Autowired private AuthService authService;

    // Lấy mã OTP
    @PostMapping("/get-otp")
    public ResponseEntity<Object> createOTP(HttpServletRequest request, @RequestParam(name = "email", required = false) String email) {
        return ResponseEntity.ok(authService.getOTP(request, email));
    }

    // Đặt lại mật khẩu
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            HttpServletRequest request,
            @RequestBody Map<String, String> requestParam) {
        return ResponseEntity.ok(authService.resetPassword(request, requestParam));
    }

}
