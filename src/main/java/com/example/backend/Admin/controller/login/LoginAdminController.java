/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Admin.controller.login;

import com.example.backend.Library.model.dto.request.auth.LoginRequest;
import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.security.auth.Account;
import com.example.backend.Library.security.employee.EmployeeDetailService;
import com.example.backend.Library.service.impl.employee.EmployeeService;
import com.example.backend.Library.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}/admin/auth")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class LoginAdminController {
    private static final Logger logger = LoggerFactory.getLogger(LoginAdminController.class);

    private final Account account;
    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeDetailService employeeDetailService;
    private final JwtUtil jwtUtil;

    public LoginAdminController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, EmployeeService employeeService, EmployeeDetailService employeeDetailService, Account account) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.employeeService = employeeService;
        this.employeeDetailService = employeeDetailService;
        this.account = account;
    }

    @PostMapping("login")
    public ResponseEntity<?> loginCustomer(
            HttpServletRequest request,
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult result) {
        logger.info("Login attempt for user: {}", loginRequest.getEmail());
        return ResponseEntity.ok(account.loginAccount(request, loginRequest, result));
    }

    // Method đăng xuất
    // @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("logout")
    public ResponseEntity<?> logoutCustomer() {
        return ResponseEntity.ok(account.logoutAccount());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map> refreshToken(@RequestBody String refreshToken) {
        Map response = new HashMap();
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = employeeDetailService.loadUserByUsername(username);
            Employee employee = employeeService.findByEmail(username);
            String newToken = jwtUtil.generateToken(userDetails, employee.getFullName(), employee.getId());
            response.put("token", newToken);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }
}
