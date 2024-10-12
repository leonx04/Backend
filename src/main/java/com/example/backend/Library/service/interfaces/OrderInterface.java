package com.example.backend.Library.service.interfaces;

import com.example.backend.Library.model.dto.request.orders.ListOrderDTO;
import com.example.backend.Library.model.dto.request.orders.OrderDTO;
import com.example.backend.Library.model.entity.orders.Order;

import java.util.List;

public interface OrderInterface {
    List<ListOrderDTO> OrderListAllfindCode(Integer id);
    List<OrderDTO> getOrder();


}
