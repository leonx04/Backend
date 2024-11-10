package com.example.backend.Library.service.interfaces.orders;

import com.example.backend.Library.model.dto.reponse.orders.ListOrderDTO;
import com.example.backend.Library.model.dto.reponse.orders.OrderDTO;
import com.example.backend.Library.model.dto.reponse.orders.PageDTO;
import com.example.backend.Library.model.entity.orders.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderInterface {
    List<ListOrderDTO> OrderListAllfindCode(String code);
    PageDTO<OrderDTO> getOrder(int pageNo, int pageSize);

    PageDTO<OrderDTO> getOrderfindStatus(int pageNo, int pageSize);

    Optional<Order> updateOrder(String code, Integer orderStatus, LocalDateTime updatedAt);
    PageDTO<OrderDTO> getOrderfindByStatus(Integer status, int pageNo, int pageSize);

    PageDTO<OrderDTO> searchOrders(String keyword, int pageNo, int pageSize);
}
