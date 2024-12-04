package com.example.backend.Library.model.entity.attributes;

import com.example.backend.Library.model.entity.employee.Employee;

import com.example.backend.Library.model.entity.products.ProductVariant;

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

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ProductImage")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String imageURL;
    @ManyToOne
    @JoinColumn(name = "productId" , referencedColumnName = "id")
    ProductVariant productVariant;
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
