package com.example.backend.Library.model.dto.reponse.orders;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusLogDTO {
    private String nameEmployee;
    private String status;
    private String code;
    private LocalDateTime updatedAt;
}
