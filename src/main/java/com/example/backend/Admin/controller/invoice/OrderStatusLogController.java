package com.example.backend.Admin.controller.invoice;


import com.example.backend.Library.model.dto.reponse.orders.OrderStatusLogDTO;
import com.example.backend.Library.model.dto.reponse.orders.PageDTO;
import com.example.backend.Library.service.impl.orders.OrderStatusLogImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders/ordersStatus")
public class OrderStatusLogController {
    @Autowired
    private OrderStatusLogImpl orderStatusLog;
    @GetMapping()
    public ResponseEntity<?> getOrderStatusLogs(@RequestParam(defaultValue = "0") int pageNo,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        PageDTO<OrderStatusLogDTO> orderStatusLogDTO = orderStatusLog.getOrderStatusLog(pageNo, pageSize);
        return ResponseEntity.ok(orderStatusLogDTO);
    }


}
