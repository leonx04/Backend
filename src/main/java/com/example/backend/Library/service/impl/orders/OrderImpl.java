package com.example.backend.Library.service.impl.orders;

import com.example.backend.Library.model.dto.response.orders.ListOrderDTO;
import com.example.backend.Library.model.dto.response.orders.OrderDTO;
import com.example.backend.Library.model.dto.response.orders.OrderItemDTO;
import com.example.backend.Library.model.dto.response.orders.PageDTO;

import com.example.backend.Library.model.entity.orders.Order;
import com.example.backend.Library.model.entity.orders.OrderStatus;
import com.example.backend.Library.model.mapper.orders.MapOrderFields;
import com.example.backend.Library.repository.orders.OrderDetailRepository;
import com.example.backend.Library.repository.orders.OrderRepository;
import com.example.backend.Library.service.interfaces.orders.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
            List<OrderItemDTO> dtos = order.getOrderDetails().stream().map(orderDetail ->
            {
                com.example.backend.Library.model.dto.response.orders.OrderItemDTO item = new com.example.backend.Library.model.dto.response.orders.OrderItemDTO();
                item.setProductName(orderDetail.getProductVariantDetail().getProduct().getName());
                item.setQuantity(orderDetail.getQuantity());
                item.setPrice(orderDetail.getPrice());
                return item;

            }).collect(Collectors.toList());
            dto.setProducts(dtos);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public PageDTO<OrderDTO> getOrder(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(order -> {
                    OrderDTO dto = new OrderDTO();
                    mapOrderFields.mapCommonOrderFields(order, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        PageDTO<OrderDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(orderDTOs);
        pageDTO.setPageNo(orderPage.getNumber());
        pageDTO.setPageSize(orderPage.getSize());
        pageDTO.setTotalElements(orderPage.getTotalElements());
        pageDTO.setTotalPages(orderPage.getTotalPages());
        pageDTO.setLast(orderPage.isLast());

        return pageDTO;
    }

    @Override
    public PageDTO<OrderDTO> getOrderfindStatus(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Integer> excludeStatuses = Arrays.asList(
                OrderStatus.COMPLETED.ordinal() + 1,
                OrderStatus.CANCELED.ordinal() + 1
        );
        Page<Order> orderPage = orderRepository.findByOrderStatusNotIn(excludeStatuses, pageable);
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(order -> {
                    OrderDTO dto = new OrderDTO();
                    mapOrderFields.mapCommonOrderFields(order, dto);
                    return dto;
                })
                .collect(Collectors.toList());

        PageDTO<OrderDTO> pageDTO = new PageDTO<>();
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
    public PageDTO<OrderDTO> getOrderfindByStatus(Integer request, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage =  orderRepository.findByOrderStatus(request,pageable);
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOs = orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            mapOrderFields.mapCommonOrderFields(order, dto);
            return dto;
        }).collect(Collectors.toList());
        PageDTO<OrderDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(orderDTOs);
        pageDTO.setPageNo(orderPage.getNumber());
        pageDTO.setPageSize(orderPage.getSize());
        pageDTO.setTotalElements(orderPage.getTotalElements());
        pageDTO.setTotalPages(orderPage.getTotalPages());
        pageDTO.setLast(orderPage.isLast());
        return pageDTO;
    }

    @Override
    public PageDTO<OrderDTO> searchOrders(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.searchOrders(keyword, pageable);
        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOs = orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            mapOrderFields.mapCommonOrderFields(order, dto);
            return dto;
        }).collect(Collectors.toList());
        PageDTO<OrderDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(orderDTOs);
        pageDTO.setPageNo(orderPage.getNumber());
        pageDTO.setPageSize(orderPage.getSize());
        pageDTO.setTotalElements(orderPage.getTotalElements());
        pageDTO.setTotalPages(orderPage.getTotalPages());
        pageDTO.setLast(orderPage.isLast());
        return pageDTO;
    }

    public List<Order> listAll() {
        return orderRepository.findAll();
    }
}
