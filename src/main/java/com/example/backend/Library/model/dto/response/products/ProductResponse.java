package com.example.backend.Library.model.dto.reponse.products;

import com.example.backend.Library.enums.product.ProductStatus;
import com.example.backend.Library.model.entity.employee.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
     Long id;
     String name;
     String description;
     ProductStatus status;
     String category;
     String brand;
     String material;

     String sole;
     LocalDate createdAt;
     Employee createdBy;

     LocalDate updatedAt;
     Employee updatedBy;
     Long appliedVariantProductCount;
}
