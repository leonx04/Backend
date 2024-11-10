package com.example.backend.Library.model.dto.Response.orders;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ListOrderDTO extends com.example.backend.Library.model.dto.response.orders.OrderDTO {
    private BigDecimal voucherCode;
    private Double subtotal;
    private Double shippingCost;
    private Double total;
    private String trackingNumber;
    private String notes;
    private String detailAddress;
    private String recipientName;
    private String recipientPhone;
    private String orderPayment;
    private List<com.example.backend.Library.model.dto.response.orders.OrderItemDTO> products;
}
