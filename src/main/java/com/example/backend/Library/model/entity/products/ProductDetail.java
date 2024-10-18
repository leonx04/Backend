package com.example.backend.Library.model.entity.products;

import com.example.backend.Library.model.entity.attributes.Color;
import com.example.backend.Library.model.entity.attributes.Size;
import com.example.backend.Library.model.entity.promotion.Promotion;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ProductDetail")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail extends BaseEntity {
//    private String code;
    private int quantity;
    private BigDecimal price;
    private int weight;

    @ManyToOne()
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "sizeId", referencedColumnName = "id")
    private Size size;

    @ManyToOne()
    @JoinColumn(name = "colorId", referencedColumnName = "id")
    private Color color;

    @ManyToOne()
    @JoinColumn(name = "promotionId", referencedColumnName = "id")
    private Promotion promotion;

//    private int status;
}
