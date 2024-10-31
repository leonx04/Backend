package com.example.backend.Client.controller.login;

import com.example.backend.Library.model.dto.request.LoginRequest;
import com.example.backend.Library.model.dto.request.RegisterRequest;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.service.interfaces.ICustomerService;
import com.example.backend.Library.util.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}/user")
public class LoginClientController {
    // Khai báo đối tượng Logger
    private static final Logger logger // Đối tượng Logger
            // Ghi log thông tin và lỗi
            = LoggerFactory // Factory để tạo ra các đối tượng Logger
            .getLogger( // Lấy ra đối tượng Logger
                    LoginClientController.class /* Class hiện tại */);

    // Khai báo đối tượng ICustomerService để sử dụng các phương thức của CustomerService
    private final ICustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginClientController(ICustomerService customerService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    // Method tạo tài khoản khách hàng
    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerCustomer(
            @Valid @ModelAttribute RegisterRequest request,
            BindingResult result
    ) {
        List<String> errors = new ArrayList<>();

        if (result.hasErrors()) {
            logger.warn("Register attempt with invalid information");
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            errors.addAll(errorMessages);
        }
        if (!request.getPassword().equals(request.getRetypePassword())) {
            String error = "Mật khẩu không khớp";
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            customerService.registerCustomer(request);
            return ResponseEntity.ok("Tạo tài khoản thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method đăng nhập => sử lý yêu cầu đăng nhập
    @PostMapping("user/login")
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
            Optional<Customer> cus = customerService.getCustomerByEmail(request.getEmail());
            Customer customer = cus.get();
            if (customer == null) {
                return ResponseEntity.badRequest().body("Tài khoản không tồn tại");
            }
            // Tạo JWT token cho người dùng
            String jwt = jwtUtil.generateToken(userDetails, customer.getFullName());

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
