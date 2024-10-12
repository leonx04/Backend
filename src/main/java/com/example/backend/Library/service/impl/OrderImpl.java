package com.example.backend.Library.service.impl;

import com.example.backend.Library.model.dto.request.orders.ListOrderDTO;
import com.example.backend.Library.model.dto.request.orders.OrderDTO;

import com.example.backend.Library.model.dto.request.orders.OrderItemDTO;
import com.example.backend.Library.model.entity.orders.Order;
import com.example.backend.Library.model.entity.payment.OrderPayment;
import com.example.backend.Library.model.entity.voucher.Voucher;
import com.example.backend.Library.repository.OrderDetailRepository;
import com.example.backend.Library.repository.OrderRepository;
import com.example.backend.Library.service.interfaces.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderImpl implements OrderInterface {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<ListOrderDTO> OrderListAllfindCode(Integer id) {
        return orderRepository.findByCodeContaining(id).stream().map(order -> {
            ListOrderDTO dto = new ListOrderDTO();
           dto.setNotes(order.getNotes());
           dto.setVoucherCode(Optional.ofNullable(order.getVoucher()).map(Voucher::getCode).orElse(null));
//           dto.setVoucherCode(order.getVoucher().getCode()!= null? order.getVoucher().getCode():null);
            dto.setPaymentMethod(Optional.ofNullable(order.getOrderPayment()).map(OrderPayment::getMethodName).orElse(null));
           // dto.setPaymentMethod(order.getOrderPayment().getMethodName()!=null? order.getOrderPayment().getMethodName():null);
           dto.setSubtotal(order.getSubtotal());
           dto.setTotal(order.getTotal());
           dto.setShippingCost(order.getShippingCost());
           dto.setTrackingNumber(order.getTrackingNumber());
           List<OrderItemDTO> dtos = order.getOrderDetails().stream().map(orderDetail ->
           {
                OrderItemDTO item = new OrderItemDTO();
                item.setProductName(orderDetail.getProductDetail().getProduct().getName());
                item.setQuantity(orderDetail.getQuantity());
                item.setPrice(orderDetail.getPrice());
                return item;

           }).collect(Collectors.toList());
           dto.setProducts(dtos);
           return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrder() {
        return orderRepository.findAll().stream().map(order->{
            OrderDTO dto = new OrderDTO();
            dto.setCode(order.getCode());
            dto.setCustomerName(order.getUser().getFullName());
            dto.setTotalAmount(order.getTotal());
            dto.setEmployee(order.getEmployee().getFullName());
            dto.setCreatedAt(order.getCreatedAt());
            dto.setUpdatedAt(order.getUpdatedAt());
            dto.setOrderType(order.getOrderType());
            dto.setStatus(order.getOrderStatus());
            return dto;
        }).collect(Collectors.toList());
    }


}
