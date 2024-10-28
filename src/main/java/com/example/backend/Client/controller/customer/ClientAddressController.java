package com.example.backend.Client.controller.customer;

import com.example.backend.Library.model.dto.request.customer.AddressRequest;
import com.example.backend.Library.model.dto.response.customer.AddressResponse;
import com.example.backend.Library.service.interfaces.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/user/address")
public class ClientAddressController {
    @Autowired
    private IAddressService addressService;

    @PostMapping("/{customerId}")
    public ResponseEntity<?> createAddress(
            @PathVariable("customerId") Integer customerId,
            @Valid @RequestBody AddressRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessgages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessgages);
        }
        try {
            addressService.createAddress(customerId, request);
            return ResponseEntity.ok("Thêm địa chỉ thành công");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Thêm địa chỉ thất bại");
        }
    }

    // Lấy danh sách địa chỉ của khách hàng
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAllAddressOfCustomer(@PathVariable("customerId") Integer customerId) {
        try {
            List<AddressResponse> addresses = addressService.getAllAddressOfCustomer(customerId);
            if (addresses.isEmpty()) {
                return ResponseEntity.ok("Không có địa chỉ nào");
            }
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lấy danh sách địa chỉ thất bại");
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
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Integer id, @RequestBody AddressRequest request) {
        try {
            addressService.updateAddress(id, request);
            return ResponseEntity.ok("Cập nhật địa chỉ thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cập nhật địa chỉ thất bại");
        }
    }

    // Xóa địa chỉ
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id) {
        try {
            if (Objects.isNull(addressService.getAddressById(id))) {
                return ResponseEntity.badRequest().body("Địa chỉ không tồn tại");
            }
            addressService.deleteAddress(id);
            return ResponseEntity.ok("Xóa địa chỉ thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xóa địa chỉ thất bại");
        }
    }

    // Cập nhật địa chỉ mặc định
    @PatchMapping("/default/{id}")
    public ResponseEntity<?> updateDefaultAddress(@PathVariable Integer id) {
        try {
            addressService.updateDefaultAddress(id);
            return ResponseEntity.ok("Cập nhật địa chỉ mặc định thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cập nhật địa chỉ mặc định thất bại");
        }
    }




}
