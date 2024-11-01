package com.example.backend.Admin.controller.invoice;

import com.example.backend.Library.model.dto.Request.FindByOrderStatusAndOrderType;
import com.example.backend.Library.model.dto.Request.UpdateStatus;
import com.example.backend.Library.model.dto.Response.orders.ApiResponse;
import com.example.backend.Library.model.dto.Response.orders.OrderDTO;
import com.example.backend.Library.model.dto.Response.orders.OrderStatusLogDTO;
import com.example.backend.Library.model.dto.Response.orders.PageDTO;
import com.example.backend.Library.model.entity.orders.Order;
import com.example.backend.Library.service.impl.orders.OrderImpl;
import com.example.backend.Library.service.impl.orders.OrderStatusLogImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class ControllerGetOrderList {
    @Autowired
    private OrderImpl orderImpl;

    @GetMapping
    public ResponseEntity<?> getOrderList(@RequestParam(defaultValue = "0") int pageNo,
                                          @RequestParam(defaultValue = "10") int pageSize) {

        PageDTO<OrderDTO> pageDTO = orderImpl.getOrder(pageNo, pageSize);

        System.out.println("Page: " + pageNo);
        System.out.println("Size: " + pageSize);
        System.out.println("Total Elements: " + pageDTO.getTotalElements());
        System.out.println("Total Pages: " + pageDTO.getTotalPages());

        return ResponseEntity.ok(pageDTO);
    }

    @Autowired
    private OrderStatusLogImpl orderStatusLog;
    @GetMapping("/orderStatus")
    public ResponseEntity<?> getOrderStatusLogs(@RequestParam(defaultValue = "0") int pageNo,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        PageDTO<OrderStatusLogDTO> orderStatusLogDTO = orderStatusLog.getOrderStatusLog(pageNo, pageSize);
        return ResponseEntity.ok(orderStatusLogDTO);
    }
    // API cập nhật OrderStatus và UpdatedAt
    @PutMapping("{code}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("code") String code,
                                               @RequestBody UpdateStatus request) {
        Optional<Order> order = orderImpl.updateOrder(code, request.getOrderStatus(), request.getUpdatedAt());
        if (order.isPresent()) {
            return ResponseEntity.ok(new ApiResponse("Update thành công", "Đơn hàng với mã '" + code + "' không tồn tại."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Order not found", "Đơn hàng với mã '" + code + "' không tồn tại."));
        }
    }
    @GetMapping("/search")
    public ResponseEntity<?> findByStatusAndType(
            @RequestParam(required = false) Integer orderStatus,
            @RequestParam(required = false) String orderType,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        PageDTO<OrderDTO> result;

        // Chuyển đổi "null" string thành null thực sự
        if ("null".equalsIgnoreCase(orderType)) {
            orderType = null;
        }

        // Nếu status = 0 hoặc null VÀ orderType = null thì lấy tất cả
        if ((orderStatus == null || orderStatus == 0) && orderType == null) {
            result = orderImpl.getOrder(pageNo, pageSize);
        }
        // Ngược lại thì tìm theo điều kiện
        else {
            FindByOrderStatusAndOrderType request = new FindByOrderStatusAndOrderType(
                    orderStatus != null ? orderStatus : 0,
                    orderType
            );
            result = orderImpl.getOrderfindByStatusAndType(request, pageNo, pageSize);
        }

        return ResponseEntity.ok(result);
    }

}
