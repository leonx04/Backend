package com.example.backend.Library.model.dto.reponse.products.variant;

import com.example.backend.Library.enums.product.ProductStatus;
import com.example.backend.Library.model.entity.employee.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse {

    Long id;
    String SKU;
    Integer quantity;
    BigDecimal price;
    Integer weight;
    String size;
    String color;

    String promotion;
    ProductStatus status;
    LocalDate createdAt;
    Employee createdBy;
    LocalDate updatedAt;
    Employee updatedBy;
}
