package com.example.backend.Admin.controller;

import com.example.backend.Library.model.dto.OrderListDTO;
import com.example.backend.Library.service.impl.OrderImpl;
import com.example.backend.Library.service.interfaces.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<OrderListDTO> getOrderList() {
        return orderImpl.getOrderList();

    }

}
