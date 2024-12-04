package com.example.backend.Library.model.entity.products;

import com.example.backend.Library.model.entity.attributes.Color;
import com.example.backend.Library.model.entity.attributes.Size;
import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.model.entity.promotion.Promotion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ProductVariant")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
     String SKU;
     Integer quantity;
     BigDecimal price;
     Integer weight;

    @ManyToOne()
    @JoinColumn(name = "productId", referencedColumnName = "id")
     Product product;

    @ManyToOne()
    @JoinColumn(name = "sizeId", referencedColumnName = "id")
     Size size;

    @ManyToOne()
    @JoinColumn(name = "colorId", referencedColumnName = "id")
     Color color;

    @ManyToOne()
    @JoinColumn(name = "promotionId", referencedColumnName = "id")
     Promotion promotion;
    Integer status;
    @CreationTimestamp
    LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    Employee createdBy;

    @CreationTimestamp
    LocalDate updatedAt;
    @ManyToOne
    @JoinColumn(name = "updatedBy", nullable = false)
    Employee updatedBy;
}
