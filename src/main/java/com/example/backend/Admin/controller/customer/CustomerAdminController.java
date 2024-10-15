package com.example.backend.Admin.controller.customer;

import com.example.backend.Library.model.dto.request.customer.CustomerRequest;
import com.example.backend.Library.model.dto.response.CustomerResponse;
import com.example.backend.Library.model.dto.response.EntityResponse;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.service.interfaces.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/admin/customers")
public class CustomerAdminController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<CustomerResponse> customers = customerService.getCustomers(pageable);

        int totalPage = customers.getTotalPages();
        List<CustomerResponse> customerList = customers.getContent();

        return ResponseEntity.ok().body(
                EntityResponse.builder()
                        .data(Collections.singletonList(customerList))
                        .totalPage(totalPage)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") int id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Method cập nhật thông tin và ảnh của khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable int id,
            @Valid @ModelAttribute CustomerRequest request,
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

    // Method cập nhật trạng thái của khách hàng
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateCustomerStatus(
            @PathVariable int id,
            @RequestParam("status") int status) {
        try {
            Customer customer = customerService.updateCustomerStatus(id, status);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Tìm kiếm khách hàng theo thuộc tính
    @GetMapping("/search")
    public ResponseEntity<?> searchCustomers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "gender", required = false) Integer gender,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        // Tạo một map để chứa thông báo khi không tìm thấy khách hàng
        Map<String, String> response = new HashMap<>();
        // Tìm kiếm khách hàng theo các thuộc tính
        Pageable pageable = PageRequest.of(page, 20);
        // Gọi phương thức tìm kiếm khách hàng
        Page<CustomerResponse> customers = customerService.searchCustomers(name, email, phone, gender, status, pageable);

        // Lấy tổng số trang
        int totalPage = customers.getTotalPages();
        // Lấy danh sách khách hàng
        List<CustomerResponse> customerList = customers.getContent();
        // Kiểm tra xem danh sách khách hàng có rỗng không
        if (customerList.isEmpty()) {
            response.put("message", "Không tìm thấy khách hàng nào");
            return ResponseEntity.badRequest().body(response);
        }

        // Trả về danh sách khách hàng tìm thấy
        return ResponseEntity.ok().body(
                EntityResponse.builder()
                        .data(Collections.singletonList(customerList))
                        .totalPage(totalPage)
                        .build()
        );
    }


    // Method tạo dữ liệu giả cho khách hàng
//    @PostMapping("/fake-data")
    public ResponseEntity<?> createFakeCustomers(@RequestParam int count) {
        try {
            customerService.createFakeCustomers(count);
            return ResponseEntity.ok("Đã tạo " + count + " khách hàng giả thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
