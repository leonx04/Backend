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
@Table(name = "order")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private Customer user;

    @ManyToOne
    @JoinColumn(name = "employeeid", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "voucherid")
    private Voucher voucher;

    @OneToOne(mappedBy = "order")
    private OrderPayment orderPayment;

    private String recipientName;
    private String recipientPhone;
    private String transactionCode;
    private Integer orderStatus;
    private Integer shippingStatus;
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private Double subtotal;

    private Double shippingCost;

    @Column(nullable = false)
    private Double total;

    private String orderType;
    private String trackingNumber;
    private String notes;


    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
