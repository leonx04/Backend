package com.example.backend.Library.model.entity.customer;

import com.example.backend.Library.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Customerid")
    private Customer customer;

    @Column(name = "Detailaddress", nullable = false, length = 255)

    private String detailAddress;

    @Column(name = "Recipientname", nullable = false, length = 255)
    private String recipientName;

    @Column(name = "Recipientphone", nullable = false, length = 20)
    private String recipientPhone;

    @Column(name = "City", nullable = false, length = 255)
    private String city;

    @Column(name = "District", nullable = false, length = 255)
    private String district;

    @Column(name = "Commune", nullable = false, length = 255)
    private String commune;

    @Column(name = "Status", nullable = false)
    private Integer status;

}