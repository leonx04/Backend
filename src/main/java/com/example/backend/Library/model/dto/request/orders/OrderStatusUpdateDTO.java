package com.example.backend.Library.model.dto.request.orders;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusUpdateDTO {
    private String code;
    private Integer status;
//    private LocalDateTime updatedAt;
}
