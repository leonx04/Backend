package com.example.backend.Library.service.interfaces;

import com.example.backend.Library.model.dto.OrderListDTO;
import com.example.backend.Library.model.entity.Order;

import java.util.List;

public interface OrderInterface {
    List<OrderListDTO> OrderListAll();
    List<OrderListDTO> getOrderList();
}
