package com.example.backend.Library.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
public class Voucher {
    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "Code", nullable = false)
    private String code;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "Description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "StartDate", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "EndDate", nullable = false)
    private Instant endDate;

    @Column(name = "DiscountValue", precision = 15)
    private BigDecimal discountValue;

    @Size(max = 50)
    @NotNull
    @Nationalized
    @Column(name = "VoucherType", nullable = false, length = 50)
    private String voucherType;

    @Column(name = "MinimumOrderValue", precision = 10)
    private BigDecimal minimumOrderValue;

    @Column(name = "MaximumDiscountAmount", precision = 10)
    private BigDecimal maximumDiscountAmount;

    @NotNull
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ColumnDefault("1")
    @Column(name = "Status")
    private Integer status;

    @ColumnDefault("getdate()")
    @Column(name = "CreatedAt")
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "UpdatedAt")
    private Instant updatedAt;

}