package com.example.backend.Library.service.interfaces.products;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.dto.request.products.ProductParamRequest;

public interface ProductService {
    PageableResponse getPageData(ProductParamRequest productParamRequest);

    PageableResponse getPageDataByPrefix(ProductParamRequest productParamRequest);
}
