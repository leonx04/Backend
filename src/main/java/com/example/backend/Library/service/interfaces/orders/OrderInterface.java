package com.example.backend.Library.service.interfaces.orders;

import com.example.backend.Library.model.dto.request.orders.ListOrderDTO;
import com.example.backend.Library.model.dto.request.orders.OrderDTO;
import com.example.backend.Library.model.dto.request.orders.OrderStatusUpdateDTO;
import com.example.backend.Library.model.entity.orders.Order;

import java.util.List;
import java.util.Optional;

public interface OrderInterface {
    List<ListOrderDTO> OrderListAllfindCode(String code);
    List<OrderDTO> getOrder();

    Order Changestate(OrderStatusUpdateDTO dto);
}
