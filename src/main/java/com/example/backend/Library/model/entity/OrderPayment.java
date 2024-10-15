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
public class OrderPayment {
    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "MethodName", nullable = false)
    private String methodName;

    @ColumnDefault("getdate()")
    @Column(name = "PaymentDate")
    private Instant paymentDate;

    @Column(name = "Amount", precision = 10)
    private BigDecimal amount;

    @Column(name = "Status")
    private Integer status;

    @Size(max = 255)
    @Nationalized
    @Column(name = "Note")
    private String note;

}