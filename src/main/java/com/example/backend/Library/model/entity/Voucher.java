package com.example.backend.Library.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Lớp thực thể Voucher đại diện cho bảng Voucher trong cơ sở dữ liệu.
 * Chứa thông tin chi tiết về voucher, bao gồm mã, mô tả, thời gian và giá trị giảm giá.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Code")
    private String code;

    @Column(name = "Description")
    private String description;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    @Column(name = "DiscountValue")
    private BigDecimal discountValue;

    @Column(name = "MinimumOrderValue")
    private BigDecimal minimumOrderValue;

    @Column(name = "VoucherType")
    private String voucherType;

    @Column(name = "CustomerLimit")
    private Integer customerLimit;

    @Column(name = "MaximumDiscountAmount")
    private BigDecimal maximumDiscountAmount;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "CreatedAt")
    private LocalDateTime createdDate;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedDate;

}
