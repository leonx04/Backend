/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Client.controller.customer;

import com.example.backend.Library.component.address.AddressComponent;
import com.example.backend.Library.model.dto.request.customer.AddressRequest;
import com.example.backend.Library.service.interfaces.customer.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/user/addresses")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class ClientAddressController {
    @Autowired private AddressComponent addressComponent;

    @Autowired
    private IAddressService addressService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<?> createAddress(
            @PathVariable("customerId") Integer customerId,
            @Valid @RequestBody AddressRequest request,
            BindingResult result
    ) {
        return ResponseEntity.ok(addressComponent.createAddress(customerId, request, result));
    }

    // Lấy danh sách địa chỉ của khách hàng
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAllAddressOfCustomer(
            @PathVariable("customerId") Integer customerId) {
        return ResponseEntity.ok(addressComponent.getAddressesByCustomerId(customerId));
    }

    // Lấy thông tin địa chỉ theo id
    @GetMapping("/{id}")
    public ResponseEntity<Map> getAddressById(@PathVariable Integer id) {
        return ResponseEntity.ok(addressComponent.getAddressById(id));
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
