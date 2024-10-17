package com.example.backend.Library.model.entity.voucher;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Voucher")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal discountValue;

    private String voucherType;

    private BigDecimal minimumOrderValue;

    private BigDecimal maximumDiscountAmount;

    private int quantity;

    private int status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
