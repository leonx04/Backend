package com.example.backend.Admin.controller.invoice;

import com.example.backend.Library.model.dto.Request.InsertOrderStatusLogRequest;
import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.model.entity.orders.OrderStatusLog;
import com.example.backend.Library.service.impl.orders.OrderStatusLogImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders/UpdateStatusLog")
public class UpdateOrderStatusLog {
    @Autowired
    private OrderStatusLogImpl orderStatusLog;
    @PostMapping()
    public ResponseEntity<?> saveOrderStatusLog(@RequestBody InsertOrderStatusLogRequest request){
        try {
            // Gọi service để lưu log trạng thái đơn hàng
            OrderStatusLog order= orderStatusLog.saveOrderStatusLog(request);
            // Trả về phản hồi thành công cùng với thông tin bản ghi đã lưu
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Cập nhật trạng thái thành công"));
        }catch (Exception e){
            // Xử lý lỗi nếu không tìm thấy Employee hoặc Order
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
