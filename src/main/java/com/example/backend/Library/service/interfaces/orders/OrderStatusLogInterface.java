package com.example.backend.Library.service.interfaces.orders;

import com.example.backend.Library.model.dto.request.orders.OrderStatusLogDTO;
import com.example.backend.Library.model.dto.request.orders.PageDTO;

public interface OrderStatusLogInterface {
    PageDTO<OrderStatusLogDTO> getOrderStatusLog(int pageNo, int pageSize);
}
