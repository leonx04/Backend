package com.example.backend.Admin.controller.login;

import com.example.backend.Library.model.dto.request.LoginRequest;
import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.service.impl.employee.EmployeeService;
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
@RequestMapping("${api.prefix}/admin/auth")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class LoginAdminController {
    private static final Logger logger = LoggerFactory.getLogger(LoginAdminController.class);

    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginAdminController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, EmployeeService employeeService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.employeeService = employeeService;
    }

    // Method đăng nhập => sử lý yêu cầu đăng nhập
//
    @PostMapping("login")
    public ResponseEntity<?> loginCustomer(
            @Valid @RequestBody LoginRequest request,
            BindingResult result) {
        // Ghi log thông tin khi người dùng cố gắng đăng nhập
        logger.info("Login attempt for user: {}" // Ghi log thông tin đăng nhập
                , request.getEmail() /* Email người dùng */);

        Map<String, String> results = new HashMap<>();
        try {
            // Kiểm tra null UserName(Email) và Password
            if (result.hasErrors()) {
                logger.warn("Login attempt with null username or password");
                // lấy ra từng lỗi
                result.getFieldErrors().forEach(error -> {
                    results.put(error.getField(), error.getDefaultMessage());
                });
                results.put("status", "error");
                return ResponseEntity.ok(results);
            }

            // Xác thực UserName(Email) và Password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( // Tạo đối tượng UsernamePasswordAuthenticationToken
                            request.getEmail(), request.getPassword() /* Email và Password */ ));

            // Lấy thông tin chi tiết của người dùng sau khi xác thực thành công
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().toArray()[0].toString();
            System.out.println(role);
            if (role.equals("ROLE_USER")) {
                results.put("message", "Thông tin đăng nhập không chính xác");
                results.put("status", "error");
                return ResponseEntity.ok(results);
            }

            Employee employee = employeeService.findByEmail(request.getEmail());

            // Tạo JWT token cho người dùng
            String jwt = jwtUtil.generateToken(userDetails, employee.getFullName(), employee.getPhone(), employee.getUserName());

            // Chuẩn bị phản hồi trả về cho client với token và thông báo đăng nhập thành công
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("role", userDetails.getAuthorities().toArray()[0].toString());
            response.put("username", employee.getUserName());
            response.put("message", "Đăng nhập thành công");
            response.put("status", "success");
            System.out.println(response.get("role"));

            // Ghi log khi đăng nhập thành công
            logger.info("Login successful for user: {}" , request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Ghi log lỗi khi đăng nhập thất bại
//            logger.error("Error during login for user: {}" , request.getEmail(), e);
            results.put("message", "Thông tin đăng nhập không chính xác");
            results.put("status", "error");
            System.out.println(e.getMessage());
            return ResponseEntity.ok(results);
        }
    }

//  Method đăng xuất
//    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("logout")
    public ResponseEntity<?> logoutCustomer() {
        Map<String, String> response = new HashMap<>();
        try {
            response.put("message", "Đăng xuất thành công");
            response.put("status", "success");
            System.out.println(response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Đăng xuất không thành công");
            response.put("status", "error");
            System.out.println(response);
            return ResponseEntity.ok(response);
        }
    }
}
