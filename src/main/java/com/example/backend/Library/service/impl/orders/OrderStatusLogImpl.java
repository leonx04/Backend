package com.example.backend.Library.service.impl.orders;

import com.example.backend.Library.model.dto.request.orders.OrderStatusLogDTO;
import com.example.backend.Library.model.dto.request.orders.PageDTO;
import com.example.backend.Library.model.entity.orders.OrderStatusLog;
import com.example.backend.Library.repository.orders.OrderStatusLogRepository;
import com.example.backend.Library.service.interfaces.orders.OrderStatusLogInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusLogImpl implements OrderStatusLogInterface {
    @Autowired
    private OrderStatusLogRepository orderStatusLogRepository;
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
                    dto.setStatus(orderStatusLog.getOrder().getOrderStatus());
                    dto.setUpdatedAt(orderStatusLog.getOrder().getUpdatedAt());
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
}
