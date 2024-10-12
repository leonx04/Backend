package com.example.backend.Library.model.dto.request.orders;

import lombok.Data;

import java.util.List;

@Data
public class ListOrderDTO {
    private String voucherCode;
    private String paymentMethod;
    private Double subtotal;
    private Double shippingCost;
    private Double total;
    private String trackingNumber;
    private String notes;
    private List<OrderItemDTO> products;
}
