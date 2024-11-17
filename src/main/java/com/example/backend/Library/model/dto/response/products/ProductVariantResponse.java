package com.example.backend.Library.model.dto.reponse.products;

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
    int quantity;
    BigDecimal price;
    int weight;
    String size;
    String color;

    String promotion;
    int status;

    LocalDate createdAt;
    Employee createdBy;
    LocalDate updatedAt;
    Employee updatedBy;
}
