package com.example.backend.Library.model.entity.attributes;

import com.example.backend.Library.model.entity.products.BaseEntity;
import com.example.backend.Library.model.entity.products.Product;
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
@Table(name = "ProductImage")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductImage extends BaseEntity {

    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "productId" , referencedColumnName = "id")
    private Product product;

}