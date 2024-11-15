package com.example.backend.Library.service.interfaces.orders;

import com.example.backend.Library.model.dto.response.orders.ListOrderDTO;
import com.example.backend.Library.model.dto.request.order.FindByOrderStatusAndOrderType;
//import com.example.backend.Library.model.dto.response.orders.*;
import com.example.backend.Library.model.entity.orders.Order;

import java.time.LocalDateTime;
import java.util.*;

public interface OrderInterface {
    List<ListOrderDTO> OrderListAllfindCode(String code);
    com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> getOrder(int pageNo, int pageSize);

    com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> getOrderfindStatus(int pageNo, int pageSize);

    Optional<Order> updateOrder(String code, Integer orderStatus, LocalDateTime updatedAt);
    com.example.backend.Library.model.dto.response.orders.PageDTO<com.example.backend.Library.model.dto.response.orders.OrderDTO> getOrderfindByStatusAndType(FindByOrderStatusAndOrderType request, int pageNo, int pageSize);

}
