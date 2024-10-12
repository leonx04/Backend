package com.example.backend.Admin.controller.invoice;

import com.example.backend.Library.model.dto.request.orders.OrderDTO;
import com.example.backend.Library.service.impl.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class ControllerGetOrderList {
    @Autowired
    private OrderImpl orderImpl;

    @GetMapping
    public ResponseEntity<?> getOrderList() {
        List<OrderDTO> orderList = orderImpl.getOrder();
        if (orderList == null || orderList.isEmpty()) {
            return new ResponseEntity<>("No orders found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

}
