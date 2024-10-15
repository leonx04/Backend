package com.example.backend.Library.model.entity;

import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.model.entity.employee.Employee;
import jakarta.persistence.*;
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
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Nationalized
    @Column(name = "Code", nullable = false, length = 250)
    private String code;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserId", nullable = false)
    private Customer user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EmployeeId", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VoucherId")
    private Voucher voucher;

    @Column(name = "OrderStatus")
    private Integer orderStatus;

    @Column(name = "ShippingStatus")
    private Integer shippingStatus;

    @Column(name = "PaymentDate")
    private Instant paymentDate;

    @NotNull
    @Column(name = "Subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "ShippingCost", precision = 10, scale = 2)
    private BigDecimal shippingCost;

    @NotNull
    @Column(name = "Total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Size(max = 50)
    @Nationalized
    @Column(name = "OrderType", length = 50)
    private String orderType;

    @Size(max = 255)
    @Column(name = "TrackingNumber")
    private String trackingNumber;

    @Nationalized
    @Lob
    @Column(name = "Notes")
    private String notes;

    @ColumnDefault("getdate()")
    @Column(name = "CreatedAt")
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "UpdatedAt")
    private Instant updatedAt;

    @NotNull
    @Column(name = "CreatedBy", nullable = false)
    private Integer createdBy;

    @NotNull
    @Column(name = "UpdatedBy", nullable = false)
    private Integer updatedBy;

}