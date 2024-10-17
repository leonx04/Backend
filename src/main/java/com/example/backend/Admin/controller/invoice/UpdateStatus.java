package com.example.backend.Admin.controller.invoice;

import com.example.backend.Library.model.dto.request.orders.OrderStatusUpdateDTO;
import com.example.backend.Library.model.entity.orders.Order;
import com.example.backend.Library.service.impl.orders.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class UpdateStatus {
    @Autowired
    private OrderImpl orderImpl;
    @PutMapping("/status")
    public ResponseEntity<?> updateOrderStatus(@RequestBody OrderStatusUpdateDTO dto) {
        Order order =orderImpl.Changestate(dto);
        if (order != null) {
            return ResponseEntity.ok(order); // Trả về 200 OK với đối tượng Order
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 NOT FOUND
        }
    }
}
