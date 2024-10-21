package com.example.backend.Library.model.mapper.Orders;

import com.example.backend.Library.model.dto.request.orders.OrderDTO;
import com.example.backend.Library.model.entity.orders.Order;
import com.example.backend.Library.repository.orders.OrderDetailRepository;
import com.example.backend.Library.repository.orders.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class MapOrderFields {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void mapCommonOrderFields(Order order, OrderDTO dto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dto.setCode(order.getCode());
        dto.setCustomerName(order.getAddress().getCustomer().getFullName());
        dto.setTotalAmount(order.getTotal());
        dto.setEmployee(order.getEmployee().getFullName());
        dto.setCreatedAt(order.getCreatedDate().format(dateTimeFormatter));
        dto.setUpdatedAt(order.getUpdatedDate().format(dateTimeFormatter));
        dto.setOrderType(order.getOrderType());
        dto.setStatus(order.getOrderStatus());
    }
}
