package com.example.backend.Library.service.interfaces.orders;

import com.example.backend.Library.model.dto.request.orders.ListOrderDTO;
import com.example.backend.Library.model.dto.request.orders.OrderDTO;
import com.example.backend.Library.model.dto.request.orders.PageDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderInterface {
    List<ListOrderDTO> OrderListAllfindCode(String code);
    PageDTO<OrderDTO> getOrder(int pageNo, int pageSize);

    PageDTO<OrderDTO> getOrderfindStatus(int pageNo, int pageSize);
}
