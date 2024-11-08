package com.example.backend.Library.service.impl.orders;

import com.example.backend.Library.model.dto.response.orders.*;
import com.example.backend.Library.model.dto.request.order.InsertOrderStatusLogRequest;
import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.model.entity.orders.*;
import com.example.backend.Library.repository.employee.EmployeeRepository;
import com.example.backend.Library.repository.orders.*;
import com.example.backend.Library.service.interfaces.orders.OrderStatusLogInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderStatusLogImpl implements OrderStatusLogInterface {
    @Autowired
    private OrderStatusLogRepository orderStatusLogRepository;
    @Autowired
    private EmployeeRepository employees;
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public PageDTO<OrderStatusLogDTO> getOrderStatusLog(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<OrderStatusLog> orderStatusLogPage = orderStatusLogRepository.findAll(pageable);
        List<OrderStatusLog> orderStatusLogList = orderStatusLogPage.getContent();
        List<OrderStatusLogDTO> orderStatusLogDTOList = orderStatusLogList.stream().map(
                orderStatusLog -> {
                    OrderStatusLogDTO dto = new OrderStatusLogDTO();
                    dto.setNameEmployee(orderStatusLog.getEmployee().getFullName());
                    dto.setCode(orderStatusLog.getOrder().getCode());
                    dto.setStatus(orderStatusLog.getOrderStatus());
                    dto.setUpdatedAt(orderStatusLog.getUpdatedAt());
                    return dto;
                }
        ).collect(Collectors.toList());
        PageDTO<OrderStatusLogDTO> dto = new PageDTO<>();
        dto.setContent(orderStatusLogDTOList);
        dto.setPageNo(pageNo);
        dto.setPageSize(pageSize);
        dto.setTotalElements(orderStatusLogPage.getTotalElements());
        dto.setTotalPages(orderStatusLogPage.getTotalPages());
        dto.setLast(orderStatusLogPage.isLast());
        return dto;
    }

    @Override
    public OrderStatusLog saveOrderStatusLog(InsertOrderStatusLogRequest request) {
        Optional<Order> order = orderRepository.findByCode(request.getCode());
        if (!order.isPresent()){
            throw new RuntimeException("Order not found with code: " + request.getCode());
        }
        Optional<Employee> employeeOptional = employees.findById(request.getId());
        if (!employeeOptional.isPresent()){
            throw new RuntimeException("Employee not found with ID: " + request.getId());
        }

        OrderStatusLog orderStatusLog = new OrderStatusLog();
        orderStatusLog.setEmployee(employeeOptional.get());
        orderStatusLog.setOrder(order.get());
        orderStatusLog.setOrderStatus(request.getOrderStatus());
        orderStatusLog.setUpdatedAt(request.getUpdatedAt());
        return orderStatusLogRepository.save(orderStatusLog);
    }


}
