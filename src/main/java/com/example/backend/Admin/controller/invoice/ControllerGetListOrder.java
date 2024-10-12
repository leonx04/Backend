package com.example.backend.Admin.controller.invoice;

import com.example.backend.Library.model.dto.request.orders.ListOrderDTO;
import com.example.backend.Library.service.impl.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class ControllerGetListOrder {
    @Autowired
    private OrderImpl orderImpl;
    @GetMapping("/{id}")
    public ResponseEntity<List<ListOrderDTO>> getOrdersByCode(@PathVariable Integer id) {
        List<ListOrderDTO> orders = orderImpl.OrderListAllfindCode(id);
        return ResponseEntity.ok(orders);
    }
}
