package com.example.backend.Library.service.impl;

import com.example.backend.Library.model.dto.OrderItemDTO;
import com.example.backend.Library.model.dto.OrderListDTO;
import com.example.backend.Library.model.entity.Order;
import com.example.backend.Library.repository.OrderDetailRepository;
import com.example.backend.Library.repository.OrderRepository;
import com.example.backend.Library.service.interfaces.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderImpl implements OrderInterface {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderListDTO> OrderListAll() {
//        return orderRepository.findAll().stream().map(order -> {
//            OrderListDTO dto = new OrderListDTO();
//            dto.setOrderId(order.getCode());
//            dto.setCustomerName(order.getUser().getFullName());
//            List<OrderItemDTO> items = order.getOrderDetails().stream().map(detail -> {
//                OrderItemDTO itemDTO = new OrderItemDTO();
//                itemDTO.setProductName(detail.getProductDetail().getProduct().getName());
//                itemDTO.setQuantity(detail.getQuantity());
//                itemDTO.setPrice(detail.getPrice());
//                return itemDTO;
//            }).collect(Collectors.toList());
//            dto.setItems(items);
//            dto.setTotalAmount(order.getTotal());
//            dto.setStatus(order.getOrderStatus());
//            return dto;
//        }).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<OrderListDTO> getOrderList() {
        return orderRepository.findAll().stream().map(order->{
            OrderListDTO dto = new OrderListDTO();
            dto.setOrderId(order.getCode());
            dto.setCustomerName(order.getUser().getFullName());
            dto.setTotalAmount(order.getTotal());
            dto.setStatus(order.getOrderStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}
