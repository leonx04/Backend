package com.example.backend.Library.model.dto.request.products;

import com.example.backend.Library.enums.product.ProductStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class ProductRequest {

    Integer id;
    String name;
    String description;
    Integer categoryId;
    Integer brandId;
    Integer materialId;
    Integer soleId;

    ProductStatus status;
}
