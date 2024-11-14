package com.example.backend.Admin.controller.customer;

import com.example.backend.Library.model.dto.response.EntityResponse;
import com.example.backend.Library.model.dto.request.customer.CustomerRequest;
import com.example.backend.Library.model.dto.response.customer.CustomerResponse;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.service.interfaces.customer.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}/admin/customers")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CustomerAdminController {
    @Autowired
    private ICustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(
            @Valid @RequestBody CustomerRequest request,
            BindingResult result
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            customerService.createCustomer(request);
            response.put("message", "Thêm khách hàng thành công");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    // Method cập nhật thông tin và ảnh của khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable int id,
            @Valid @RequestBody CustomerRequest request
    ) {
        try {
            Customer customer = customerService.updateCustomer(id, request);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 10);
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

    // Method xóa cứng khách hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        try {
            customerService.deleteCustomer(id);
            response.put("message", "Xóa khách hàng thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message","Xóa không thành công " + e.getMessage());
            response.put("status", "error");
            return ResponseEntity.ok(response);
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
        Pageable pageable = PageRequest.of(page, 10);
        // Gọi phương thức tìm kiếm khách hàng
        Page<CustomerResponse> customers = customerService.searchCustomers(name, email, phone, gender, status, pageable);

        // Lấy tổng số trang
        int totalPage = customers.getTotalPages();
        // Lấy danh sách khách hàng
        List<CustomerResponse> customerList = customers.getContent();
        // Kiểm tra xem danh sách khách hàng có rỗng không
        if (customerList.isEmpty()) {
            response.put("message", "Không tìm thấy khách hàng nào");
            return ResponseEntity.ok().body(response);
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
