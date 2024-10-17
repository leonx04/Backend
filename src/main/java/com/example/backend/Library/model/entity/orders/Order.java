package com.example.backend.Library.model.entity.orders;

import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.model.entity.payment.OrderPayment;
import com.example.backend.Library.model.entity.voucher.Voucher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Code")
    private String code;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private Customer user;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "voucherId", referencedColumnName = "id")
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "orderPaymentId", referencedColumnName = "id")
    private OrderPayment orderPayment;


    private int orderStatus;
    private int shippingStatus;
    private Double subtotal;
    private Double shippingCost;
    private Double total;
    private String orderType;
    private String trackingNumber;
    private String notes;
    @Column(name = "createdat")
    private LocalDateTime  createdAt;
    @Column(name = "updatedat")
    private LocalDateTime   updatedAt;
    @Column(name = "createdby")
    private int createdBy;
    @Column(name = "updatedby")
    private int updatedBy;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
    public String getOrderStatus() {
        return OrderStatus.getDescriptionByCode(orderStatus);
    }
     //Phương thức để lấy ngày từ LocalDateTime
    public LocalDate getCreatedDate() {
        return createdAt.toLocalDate();
    }

    public LocalDate getUpdatedDate() {
        return updatedAt.toLocalDate();
    }
}
