package com.example.backend.Library.service.interfaces.products;

import com.example.backend.Library.model.entity.products.ProductDetail;
import com.example.backend.Library.service.interfaces.GenericCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductDetailService extends GenericCrudService<ProductDetail, Integer> {
    List<ProductDetail> findByProductId(Integer productId);

    List<ProductDetail> findByPromotionId(Integer promotionId);
}