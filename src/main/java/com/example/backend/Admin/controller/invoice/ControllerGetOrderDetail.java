package com.example.backend.Admin.controller.invoice;

import com.example.backend.Library.model.dto.response.orders.ListOrderDTO;
//import com.example.backend.Library.model.dto.response.orders.ListOrderDTO;
import com.example.backend.Library.service.impl.orders.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class ControllerGetOrderDetail {
    @Autowired
    private OrderImpl orderImpl;
    @GetMapping("/{code}")
    public ResponseEntity<List<ListOrderDTO>> getOrdersByCode(@PathVariable String code) {
        List<ListOrderDTO> orders = orderImpl.OrderListAllfindCode(code);
        return ResponseEntity.ok(orders);
    }

}
