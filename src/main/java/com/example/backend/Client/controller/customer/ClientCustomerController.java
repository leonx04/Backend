/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Client.controller.customer;

import com.example.backend.Library.component.customer.CustomerComponent;
import com.example.backend.Library.model.dto.request.customer.CustomerRequest;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.service.interfaces.customer.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/user/customers")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class ClientCustomerController {
    @Autowired private ICustomerService customerService;
    @Autowired private CustomerComponent customerComponent;

    // Method cập nhật thông tin và ảnh của khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable int id,
            @Valid CustomerRequest request,
            BindingResult result,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) {
        List<FieldError> filteredErrors = result.getFieldErrors()
                .stream()
                .filter(error -> !error.getField().equals("email"))
                .filter(error -> !error.getField().equals("password"))
                .toList();

        if (!filteredErrors.isEmpty()) {
            List<String> errorMessages = filteredErrors
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            // Kiểm tra và xóa ảnh cũ nếu có avatar mới
            customerService.updateImage(id, request, avatar);

            // Lưu khách hàng đã cập nhật
            Customer customer = customerService.updateCustomer(id, request);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Method lấy thông tin khách hàng theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") int id) {
        return ResponseEntity.ok(customerComponent.getCustomer(id));
    }

    // Method xóa cứng khách hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Xóa khách hàng thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Method xóa mềm khách hàng
    @DeleteMapping("/{id}/customer")
    public ResponseEntity<?> softDeleteCustomer(@PathVariable int id) {
        try {
            customerService.softDeleteCustomer(id);
            return ResponseEntity.ok("Khách hàng đã xóa");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
