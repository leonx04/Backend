package com.example.backend.Library.service.interfaces.orders;

import com.example.backend.Library.model.dto.Request.InsertOrderStatusLogRequest;
import com.example.backend.Library.model.dto.Response.orders.OrderStatusLogDTO;
import com.example.backend.Library.model.dto.Response.orders.PageDTO;
import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.model.entity.orders.OrderStatusLog;

import java.util.Optional;

public interface OrderStatusLogInterface {
    PageDTO<OrderStatusLogDTO> getOrderStatusLog(int pageNo, int pageSize);
    OrderStatusLog saveOrderStatusLog(InsertOrderStatusLogRequest request);

}
