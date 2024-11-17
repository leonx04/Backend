package com.example.backend.Library.service.interfaces.products;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.request.products.ProductParamRequest;
import com.example.backend.Library.model.dto.request.products.ProductVariantParamRequest;
import com.example.backend.Library.model.entity.products.ProductVariant;

import java.util.List;
import java.util.Optional;

public interface ProductVariantService{
    PageableResponse getPageData(ProductVariantParamRequest productVariantParamRequest);

    PageableResponse getPageDataByPrefix(ProductVariantParamRequest productVariantParamRequest);
    //Dung
    List<ProductVariant> findByPromotionId(Integer promotionId);

    List<ProductVariant> findByProductId(Integer productId);

    Optional<ProductVariant> findById(Integer id);
    ProductVariant update(ProductVariant productVariant);
    boolean existsById(Integer id);
}
