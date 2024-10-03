package com.example.backend.Library.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CustomerVoucher")
public class CustomerVoucher {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "VoucherId", nullable = false)
    private Voucher voucher;

    private Integer usageCount;
    private Boolean isSaved;
}