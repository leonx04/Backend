package com.example.backend.Library.model.entity.orders;

import com.example.backend.Library.model.entity.customer.Address;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.model.entity.employee.Employee;
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
    private String ordersAddress;

    private String recipientName;

    private String recipientPhone;

    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private Customer customer;


    @ManyToOne
    @JoinColumn(name = "voucherId", referencedColumnName = "id")
    private Voucher voucher;

    private Integer orderPayment;


    private Integer orderStatus;
    private int shippingStatus;
    private Double subtotal;
    private Double shippingCost;
    private Double total;
    private String orderType;
    private String trackingNumber;
    private String notes;
    @Column(name = "createdAt")
    private LocalDateTime  createdAt;
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

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

    public String getorderPaymentString(){
        if(orderPayment==0){
            return "Thanh toán chuyển khoản";
        }else if(orderPayment==1){
            return "Thanh toán tiền mặt";
        }else {
            return "Thanh toán khi nhận hàng";
        }

    }

}
