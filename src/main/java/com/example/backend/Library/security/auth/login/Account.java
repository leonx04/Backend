/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Library.security.auth.login;

import com.example.backend.Library.model.dto.request.auth.LoginRequest;
import com.example.backend.Library.model.dto.request.auth.RegisterRequest;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.security.customer.CustomerDetailService;
import com.example.backend.Library.security.employee.EmployeeDetailService;
import com.example.backend.Library.service.impl.employee.EmployeeService;
import com.example.backend.Library.service.interfaces.customer.ICustomerService;
import com.example.backend.Library.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static com.example.backend.Library.validation.pts_validator.Validator.errorField;

@Component
public class Account {
    private final EmployeeDetailService employeeDetailService;
    private final CustomerDetailService customerDetailService;
    private AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final ICustomerService customerService;
    private final JwtUtil jwtUtil;

    public Account(AuthenticationManager authenticationManager, EmployeeDetailService employeeDetailService, CustomerDetailService customerDetailService, EmployeeService employeeService, ICustomerService customerService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.employeeDetailService = employeeDetailService;
        this.customerDetailService = customerDetailService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    public Map loginAccount(HttpServletRequest request, LoginRequest login, BindingResult result) {
        String uri = request.getRequestURI();
        Map response = new HashMap();

        try {
            errorField(response, result);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().toArray()[0].toString();
            if (uri.contains("/api/ecm/admin/") || uri.contains("/admin/")) {
                System.out.println("Role: " + role);
                if (role.equals("ROLE_USER")) {
                    response.put("message", "Thông tin đăng nhập không chính xác");
                    response.put("status", 400);
                    return response;
                }

                Employee employee = employeeService.findByEmail(login.getEmail());// Tạo JWT token cho người dùng
                requestLoginSuccess(response, userDetails, employee.getFullName(), employee.getId(), login.getEmail());

                System.out.println("Đăng nhập cho admin");
                return response;
            } else if (uri.contains("/api/ecm/user/" ) || uri.contains("/user/")) {
                Customer customer = customerService.findByEmail(login.getEmail())
                        .orElseThrow(
                                () -> new Exception("Tài khoản không tồn tại"));// Tạo JWT token cho người dùng
                requestLoginSuccess(response, userDetails, customer.getFullName(), customer.getId(), login.getEmail());

                System.out.println("Đăng nhập cho user");
                return response;
            }

        } catch (Exception e) {
            response.put("message", "Thông tin đăng nhập không chính xác");
            response.put("status", 400);
            System.out.println(e.getMessage());
            return response;
        }
        return null;
    }

    public Map logoutAccount() {
        Map response = new HashMap();
        try {
            response.put("message", "Đăng xuất thành công");
            response.put("status", 200);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            response.put("message", "Đăng xuất không thành công");
            response.put("status", 400);
            System.out.println(response + "\n" + e.getMessage());
            return response;
        }
    }

    public Map refreshToken(HttpServletRequest request, String refreshToken) {
        Map response = new HashMap();
        String url = request.getRequestURI();
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            if (url.contains("/api/ecm/admin/") || url.contains("/admin/")) {
                UserDetails userDetails = employeeDetailService.loadUserByUsername(username);
                Employee employee = employeeService.findByEmail(username);
                return refreshTokenSuccess(response, userDetails, employee.getFullName(), employee.getId());
            } else if (url.contains("/api/ecm/user/") || url.contains("/user/")) {
                UserDetails userDetails = customerDetailService.loadUserByUsername(username);
                Customer customer = customerService.findByEmail(username).orElse(null);
                return refreshTokenSuccess(response, userDetails, customer.getFullName(), customer.getId());
            }
        } else {
            response.put("status", "error");
            return response;
        }
        return null;
    }

    public Map signupAccount(RegisterRequest request, BindingResult result) {
        Map response = new HashMap();
        try {
            errorField(response, result);

            if (!request.getPassword().equals(request.getRetypePassword())) {
                response.put("message", "Mật khẩu không khớp");
                response.put("status", 400);
                return response;
            }

            customerService.registerCustomer(request);
            response.put("message", "Tạo tài khoản thành công");
            response.put("status", 200);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("status", 400);
            return response;
        }
    }

    private Map requestLoginSuccess(Map response, UserDetails userDetails, String fullname, int id, String email) {
        String jwt = jwtUtil.generateToken(userDetails, fullname, id);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        // Chuẩn bị phản hồi trả về cho client với token và thông báo đăng nhập thành công
        response.put("token", jwt);
        response.put("RFTK", refreshToken);
        response.put("message", "Đăng nhập thành công");
        response.put("status", 200);
        return response;
    }

    private Map refreshTokenSuccess(Map response, UserDetails userDetails, String fullname, int id) {
        String newJwt = jwtUtil.generateToken(userDetails, fullname, id);
        response.put("token", newJwt);
        response.put("message", "Tạo token mới thành công");
        response.put("status", "success");
        return response;
    }

}
