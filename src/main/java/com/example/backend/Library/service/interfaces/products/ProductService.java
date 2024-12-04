package com.example.backend.Library.service.interfaces.products;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.products.product.ProductResponse;
import com.example.backend.Library.model.dto.request.products.product.ProductParamRequest;
import com.example.backend.Library.model.dto.request.products.product.ProductRequest;
import com.example.backend.Library.model.entity.products.Product;

public interface ProductService {

    Integer createProduct(ProductRequest productRequest);

    PageableResponse getPageData(ProductParamRequest productParamRequest);

    PageableResponse getPageDataByPrefix(ProductParamRequest productParamRequest);

    PageableResponse getEntityByName(ProductParamRequest ProductParamRequest);
}
