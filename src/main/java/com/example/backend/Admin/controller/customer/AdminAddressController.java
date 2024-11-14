package com.example.backend.Admin.controller.customer;

import com.example.backend.Library.model.dto.request.customer.AddressRequest;
import com.example.backend.Library.model.dto.response.customer.AddressResponse;
import com.example.backend.Library.service.interfaces.customer.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/admin/address")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class AdminAddressController {

    @Autowired
    private IAddressService addressService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<?> createAddress(
            @PathVariable("customerId") Integer customerId,
            @Valid @RequestBody AddressRequest request,
            BindingResult result
    ) {
        Map<String, String> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errorMessgages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessgages);
        }
        try {
            addressService.createAddress(customerId, request);
            response.put("message", "Thêm địa chỉ thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message", "Thêm địa chỉ thành công");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Lấy danh sách địa chỉ của khách hàng
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAllAddressOfCustomer(@PathVariable("customerId") Integer customerId) {
        Map<String, String> response = new HashMap<>();
        try {
            List<AddressResponse> addresses = addressService.getAllAddressOfCustomer(customerId);
            if (addresses.isEmpty()) {
                response.put("message", "Không có địa chỉ nào");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            response.put("message", "Lấy danh sách địa chỉ thất bại");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Lấy thông tin địa chỉ theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Integer id) {
        AddressResponse addressResponse = addressService.getAddressById(id);
        if (addressResponse != null) {
            return ResponseEntity.ok(addressResponse);
        } else {
            return ResponseEntity.badRequest().body("Địa chỉ không tồn tại");
        }
    }

    // Cập nhật thông tin địa chỉ
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable Integer id,
            @RequestBody AddressRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            addressService.updateAddress(id, request);
            response.put("message", "Cập nhật địa chỉ thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message", "Cập nhật địa chỉ thất bại");
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    // Xóa địa chỉ
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            addressService.deleteAddress(id);
            response.put("message", "Xóa địa chỉ thành công");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    // Cập nhật địa chỉ mặc định
    @PatchMapping("/default/{id}")
    public ResponseEntity<?> updateDefaultAddress(
            @PathVariable Integer id
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            addressService.updateDefaultAddress(id);
            response.put("message", "Cập nhật địa chỉ mặc định thành công");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

}
