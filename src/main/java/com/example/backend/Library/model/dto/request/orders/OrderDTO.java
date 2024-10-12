package com.example.backend.Library.model.dto.request.orders;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private String Code;
    private String customerName;
    private Double totalAmount;
    private Integer status;
    private String employee;
    private String orderType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
