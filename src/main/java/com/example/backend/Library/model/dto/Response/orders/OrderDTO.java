package com.example.backend.Library.model.dto.Response.orders;

import lombok.Data;

@Data
public class OrderDTO {
    private String Code;
    private String customerName;
    private Double totalAmount;
    private String status;
    private String orderType;
    private String createdAt;
    private String updatedAt;


}
