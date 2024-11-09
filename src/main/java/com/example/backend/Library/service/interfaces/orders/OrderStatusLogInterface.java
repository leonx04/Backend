package com.example.backend.Library.service.interfaces.orders;

import com.example.backend.Library.model.dto.response.orders.*;
import com.example.backend.Library.model.dto.request.order.InsertOrderStatusLogRequest;
import com.example.backend.Library.model.entity.orders.OrderStatusLog;

public interface OrderStatusLogInterface {
    PageDTO<OrderStatusLogDTO> getOrderStatusLog(int pageNo, int pageSize);
    OrderStatusLog saveOrderStatusLog(InsertOrderStatusLogRequest request);

}
