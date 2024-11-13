package com.example.backend.Library.model.dto.request.products;

import com.example.backend.Library.enums.product.ProductStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantParamRequest {
    String searchBySKU;
    String sortBy = "createdAt";//stt,quantity,price
    String sortDir = "desc";//desc

    ProductStatus status = ProductStatus.ACTIVE;

    Integer pageNo = 1;
    Integer pageSize = 10;

    Integer sizeId;
    Integer colorId;

//    Integer weightId;
    Integer promotionId;

    BigDecimal minPrice;
    BigDecimal maxPrice;
}
