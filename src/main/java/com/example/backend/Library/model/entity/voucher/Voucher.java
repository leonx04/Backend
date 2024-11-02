package com.example.backend.Library.model.entity.voucher;

import jakarta.persistence.*;
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

    // Phương thức này sẽ được gọi trước khi tạo bản ghi
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();   // Lấy thời gian hiện tại
        this.updatedAt = LocalDateTime.now();   // Đồng thời set updatedAt khi tạo
    }

    // Phương thức này sẽ được gọi trước khi cập nhật bản ghi
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();   // Lấy thời gian hiện tại khi cập nhật
    }

}