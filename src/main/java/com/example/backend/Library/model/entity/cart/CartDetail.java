package com.example.backend.Library.model.entity.cart;

import com.example.backend.Library.model.entity.products.BaseEntity;
import com.example.backend.Library.model.entity.products.ProductDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CartDetail")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "cartId", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productDetailId", referencedColumnName = "id")
    private ProductDetail productDetail;
    private int quantity;

}
