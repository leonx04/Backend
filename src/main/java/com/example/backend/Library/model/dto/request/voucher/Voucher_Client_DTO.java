package com.example.backend.Library.model.dto.request.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Voucher_Client_DTO {
    private Integer id;
    private String code;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal discountValue;
    private BigDecimal minimumOrderValue;
    private String voucherType;
    private BigDecimal maximumDiscountAmount;
    private Integer quantity;
    private Integer status;
}