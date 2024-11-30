/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Library.component.customer;

import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.service.interfaces.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerComponent {
    @Autowired private ICustomerService customerService;

    public Map getCustomer (int id) {
        Map response = new HashMap();
        try {
            Customer customer = customerService.getCustomerById(id);
            response.put("message", "Lấy thông tin khách hàng thành công.");
            response.put("data", customer);
            response.put("status", 200);
            return response;
        } catch (Exception e) {
            response.put("message", "Lấy thông tin khách hàng thất bại do: " + e.getMessage());
            response.put("status", 400);
            return response;
        }
    }
}
