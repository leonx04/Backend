package com.example.backend.Library.service.interfaces;

import com.example.backend.Library.model.dto.OrderListDTO;

import java.util.List;

public interface OrderInterface {
    List<OrderListDTO> OrderListAll();
    List<OrderListDTO> getOrderList();
}
