package com.example.backend.Library.model.dto.request.orders;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ListOrderDTO extends OrderDTO {
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
    private List<OrderItemDTO> products;
}
