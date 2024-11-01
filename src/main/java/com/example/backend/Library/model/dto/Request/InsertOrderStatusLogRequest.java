package com.example.backend.Library.model.dto.Request;

import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.model.entity.orders.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertOrderStatusLogRequest {
    private int id;
    private String code;
    private int orderStatus;
    private LocalDateTime updatedAt;
}
