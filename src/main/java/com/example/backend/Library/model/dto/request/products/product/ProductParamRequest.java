package com.example.backend.Library.model.dto.request.products.product;

import com.example.backend.Library.enums.product.ProductStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductParamRequest {

    String searchByName;
    String sortBy = "createdAt";//stt,name.
    String sortDir = "desc";//desc
    ProductStatus status = ProductStatus.ACTIVE;
    Integer pageNo = 1;
    Integer pageSize = 10;

    Integer categoryId;
    Integer brandId;
    Integer materialId;
    Integer soleId;
}
