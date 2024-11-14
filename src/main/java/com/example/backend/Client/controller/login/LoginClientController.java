/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Client.controller.login;

import com.example.backend.Library.model.dto.request.auth.LoginRequest;
import com.example.backend.Library.model.dto.request.auth.RegisterRequest;
import com.example.backend.Library.security.auth.Account;
import com.example.backend.Library.service.interfaces.customer.ICustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}/user")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class LoginClientController {
    private static final Logger logger
            = LoggerFactory.getLogger(LoginClientController.class);

    private final ICustomerService customerService;
    private final Account account;

    public LoginClientController(ICustomerService customerService, Account account) {
        this.customerService = customerService;
        this.account = account;
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

}
