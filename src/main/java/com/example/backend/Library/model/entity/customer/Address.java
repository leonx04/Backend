package com.example.backend.Library.model.entity.customer;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Address")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private Customer customer;

    private String detailAddress;

    private String recipientName;

    private String recipientPhone;

    private String city;

    private String district;

    private String commune;

    private int status;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
