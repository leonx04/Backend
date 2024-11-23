package com.example.backend.Library.model.mapper.products;

import com.example.backend.Library.enums.product.ProductStatus;
import com.example.backend.Library.model.dto.reponse.products.ProductResponse;
import com.example.backend.Library.model.entity.products.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", source = "product.category.name")
    @Mapping(target = "brand", source = "product.brand.name")
    @Mapping(target = "material", source = "product.material.name")
    @Mapping(target = "sole", source = "product.sole.name")
    @Mapping(target = "status", source = "product.status") // ánh xạ status từ Product sang ProductResponse
    ProductResponse toProductResponse(Product product);

    default ProductStatus mapStatus(int status) {
        return ProductStatus.fromValue(status);
    }
}
