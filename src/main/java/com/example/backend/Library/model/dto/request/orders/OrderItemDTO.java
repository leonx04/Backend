package com.example.backend.Library.model.dto.request.orders;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String productName;
    private Integer quantity;
    private Double price;
}
