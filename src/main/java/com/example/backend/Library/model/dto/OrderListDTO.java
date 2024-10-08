package com.example.backend.Library.model.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderListDTO {
    private String orderId;
    private String customerName;
    private Double totalAmount;
    private Integer status;

}
