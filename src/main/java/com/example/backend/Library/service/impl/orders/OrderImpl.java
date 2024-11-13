package com.example.backend.Library.service.impl.orders;

import com.example.backend.Library.model.dto.Response.orders.ListOrderDTO;
import com.example.backend.Library.model.dto.request.order.FindByOrderStatusAndOrderType;
//import com.example.backend.Library.model.dto.response.orders.*;

import com.example.backend.Library.model.entity.orders.*;
import com.example.backend.Library.model.mapper.Orders.MapOrderFields;
import com.example.backend.Library.repository.orders.*;
import com.example.backend.Library.service.interfaces.orders.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderImpl implements OrderInterface {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MapOrderFields mapOrderFields;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<ListOrderDTO> OrderListAllfindCode(String code) {
        return orderRepository.findByCodeContaining(code).stream().map(order -> {
            ListOrderDTO dto = new ListOrderDTO();
            mapOrderFields.mapCommonOrderFields(order, dto);
            dto.setNotes(order.getNotes());
            if(order.getVoucher() == null)  {
                dto.setVoucherCode(BigDecimal.valueOf(0));

            }else{
                dto.setVoucherCode(order.getVoucher().getDiscountValue());
            }
            dto.setRecipientName(order.getRecipientName());
            dto.setRecipientPhone(order.getRecipientPhone());
            dto.setDetailAddress(order.getOrdersAddress());
            dto.setOrderPayment(order.getorderPaymentString());
            dto.setSubtotal(order.getSubtotal());
            dto.setTotal(order.getTotal());
            dto.setShippingCost(order.getShippingCost());
            dto.setTrackingNumber(order.getTrackingNumber());
            List<com.example.backend.Library.model.dto.response.orders.OrderItemDTO> dtos = order.getOrderDetails().stream().map(orderDetail ->
            {
                com.example.backend.Library.model.dto.response.orders.OrderItemDTO item = new com.example.backend.Library.model.dto.response.orders.OrderItemDTO();
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
    public com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> getOrder(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<Order> orders = orderPage.getContent();
        List<com.example.backend.Library.model.dto.response.orders.OrderDTO> orderDTOs = orders.stream()
                .map(order -> {
                    com.example.backend.Library.model.dto.response.orders.OrderDTO dto = new com.example.backend.Library.model.dto.response.orders.OrderDTO();
                    mapOrderFields.mapCommonOrderFields(order, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> pageDTO = new com.example.backend.Library.model.dto.response.orders.PageDTO<>();
        pageDTO.setContent(orderDTOs);
        pageDTO.setPageNo(orderPage.getNumber());
        pageDTO.setPageSize(orderPage.getSize());
        pageDTO.setTotalElements(orderPage.getTotalElements());
        pageDTO.setTotalPages(orderPage.getTotalPages());
        pageDTO.setLast(orderPage.isLast());

        return pageDTO;
    }

    @Override
    public com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> getOrderfindStatus(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Integer> excludeStatuses = Arrays.asList(
                OrderStatus.COMPLETED.ordinal() + 1,
                OrderStatus.CANCELED.ordinal() + 1
        );
        Page<Order> orderPage = orderRepository.findByOrderStatusNotIn(excludeStatuses, pageable);
        List<Order> orders = orderPage.getContent();
        List<com.example.backend.Library.model.dto.response.orders.OrderDTO> orderDTOs = orders.stream()
                .map(order -> {
                    com.example.backend.Library.model.dto.response.orders.OrderDTO dto = new com.example.backend.Library.model.dto.response.orders.OrderDTO();
                    mapOrderFields.mapCommonOrderFields(order, dto);
                    return dto;
                })
                .collect(Collectors.toList());

        com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> pageDTO = new com.example.backend.Library.model.dto.response.orders.PageDTO<>();
        pageDTO.setContent(orderDTOs);
        pageDTO.setPageNo(orderPage.getNumber());
        pageDTO.setPageSize(orderPage.getSize());
        pageDTO.setTotalElements(orderPage.getTotalElements());
        pageDTO.setTotalPages(orderPage.getTotalPages());
        pageDTO.setLast(orderPage.isLast());

        return pageDTO;
    }

    @Override
    public Optional<Order> updateOrder(String code, Integer orderStatus, LocalDateTime updatedAt) {
        Optional<Order> orderOptional = orderRepository.findByCode(code);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setOrderStatus(orderStatus);
            order.setUpdatedAt(updatedAt);
            orderRepository.save(order);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    @Override
    public com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> getOrderfindByStatusAndType(FindByOrderStatusAndOrderType request, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage =  orderRepository.findByOrderStatusAndOrderType(request.getOrderStatus(),request.getOrderType(),pageable);
        List<Order> orders = orderPage.getContent();
        List<com.example.backend.Library.model.dto.response.orders.OrderDTO> orderDTOs = orders.stream().map(order -> {
            com.example.backend.Library.model.dto.response.orders.OrderDTO dto = new com.example.backend.Library.model.dto.response.orders.OrderDTO();
            mapOrderFields.mapCommonOrderFields(order, dto);
            return dto;
        }).collect(Collectors.toList());
        com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> pageDTO = new com.example.backend.Library.model.dto.response.orders.PageDTO<>();
        pageDTO.setContent(orderDTOs);
        pageDTO.setPageNo(orderPage.getNumber());
        pageDTO.setPageSize(orderPage.getSize());
        pageDTO.setTotalElements(orderPage.getTotalElements());
        pageDTO.setTotalPages(orderPage.getTotalPages());
        pageDTO.setLast(orderPage.isLast());
        return pageDTO;
    }


}
