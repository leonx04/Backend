package com.example.backend.Library.model.dto.request.cart;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartDetailDTO {
    private String fullNameproduct;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal  subtotal;
}
