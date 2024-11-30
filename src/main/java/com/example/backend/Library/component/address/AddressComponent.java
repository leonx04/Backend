package com.example.backend.Library.component.address;

import com.example.backend.Library.model.dto.request.customer.AddressRequest;
import com.example.backend.Library.model.dto.response.customer.AddressResponse;
import com.example.backend.Library.service.interfaces.customer.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.backend.Library.validation.pts_validator.Validator.errorField;

@Component
public class AddressComponent {
    @Autowired private IAddressService addressService;

    public Map getAddressesByCustomerId(int customerId) {
        Map response = new HashMap();
        try {
            List<AddressResponse> list = addressService.getAllAddressOfCustomer(customerId);
            if (list.isEmpty()) {
                response.put("message", "Không có địa chỉ nào");
                response.put("status", 404);
                return response;
            }
            response.put("message", "Lấy thông tin địa chỉ thành công.");
            response.put("data", list);
            response.put("status", 200);
            return response;
        } catch (Exception e) {
            response.put("message", "Lấy thông tin địa chỉ thất bại do: " + e.getMessage());
            response.put("status", 400);
            return response;
        }
    }

    public Map createAddress(int customerId, AddressRequest request, BindingResult result) {
        Map response = new HashMap<>();
        try {
            errorField(response, result, 400);
            if (response.get("status").equals(400)) {
                return response;
            } else {
                addressService.createAddress(customerId, request);
                response.put("message", "Thêm địa chỉ thành công");
                response.put("status", 200);
                return response;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message", "Thêm địa chỉ thất bại");
            response.put("status", 400);
            return response;
        }
    }

    public Map getAddressById(int id) {
        Map response = new HashMap<>();
        AddressResponse addressResponse = addressService.getAddressById(id);
        if (addressResponse == null) {
            response.put("message", "Địa chỉ đã xóa hoặc không tồn tại");
            response.put("status", 404);
            return response;
        }
        response.put("data", addressResponse);
        response.put("status", 200);
        return response;
    }
}
