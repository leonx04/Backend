package com.example.backend.Library.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private BigDecimal discountAmount;

    private BigDecimal discountPercentage;

    @Column(nullable = false)
    private String voucherType;

    private BigDecimal minimumDiscountAmount;

    private BigDecimal maximumDiscountAmount;

    @Column(nullable = false)
    private Integer quantity;

    private Integer status = 1;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "voucher")
    private List<Order> orders;

    @OneToMany(mappedBy = "voucher")
    private List<CustomerVoucher> customerVouchers;
}