package com.example.backend.Library.model.dto.request.orders;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OrderDTO {
    private String Code;
    private String customerName;
    private Double totalAmount;
    private String status;
    private String employee;
    private String orderType;
    private String createdAt;
    private String updatedAt;


}
