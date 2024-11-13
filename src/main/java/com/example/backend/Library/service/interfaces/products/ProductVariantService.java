package com.example.backend.Library.service.interfaces.products;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.request.products.ProductParamRequest;
import com.example.backend.Library.model.dto.request.products.ProductVariantParamRequest;

public interface ProductVariantService{
    PageableResponse getPageData(ProductVariantParamRequest productVariantParamRequest);

    PageableResponse getPageDataByPrefix(ProductVariantParamRequest productVariantParamRequest);
}
