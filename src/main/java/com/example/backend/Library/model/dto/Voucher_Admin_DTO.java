package com.example.backend.Library.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Voucher_Admin_DTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    private String code;
    private String description;
    private LocalDateTime startDate;

    private LocalDateTime endDate;
    private BigDecimal discountValue;
    private BigDecimal minimumOrderValue;
    private String voucherType;
    private Integer customerLimit;
    private BigDecimal maximumDiscountAmount;
    private Integer quantity;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}