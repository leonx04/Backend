package com.example.backend.Library.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cartdetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartid", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productDetailId", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;
}
