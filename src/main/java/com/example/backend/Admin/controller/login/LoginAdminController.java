package com.example.backend.Admin.controller.login;

import com.example.backend.Library.model.dto.request.LoginRequest;
import com.example.backend.Library.util.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}")
public class LoginAdminController {
    private static final Logger logger = LoggerFactory.getLogger(LoginAdminController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginAdminController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // Method đăng nhập => sử lý yêu cầu đăng nhập
    @PostMapping("admin/login")
    public ResponseEntity<?> loginCustomer(
            @Valid @RequestBody LoginRequest request,
            BindingResult result) {
        // Ghi log thông tin khi người dùng cố gắng đăng nhập
        logger.info("Login attempt for user: {}" // Ghi log thông tin đăng nhập
                , request.getEmail() /* Email người dùng */);

        try {
            // Kiểm tra null UserName(Email) và Password
            if (result.hasErrors()) {
                logger.warn("Login attempt with null username or password");
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            // Xác thực UserName(Email) và Password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( // Tạo đối tượng UsernamePasswordAuthenticationToken
                            request.getEmail(), request.getPassword() /* Email và Password */ ));

            // Lấy thông tin chi tiết của người dùng sau khi xác thực thành công
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Tạo JWT token cho người dùng
            String jwt = jwtUtil.generateToken(userDetails);

            // Chuẩn bị phản hồi trả về cho client với token và thông báo đăng nhập thành công
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("message", "Đăng nhập thành công");

            // Ghi log khi đăng nhập thành công
            logger.info("Login successful for user: {}" , request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Ghi log lỗi khi đăng nhập thất bại
            logger.error("Error during login for user: {}" , request.getEmail(), e);
            return ResponseEntity.badRequest().body("Thông tin đăng nhập không chính xác");
        }
    }
}
