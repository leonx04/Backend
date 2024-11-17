package com.example.backend.Library.model.mapper.products;

import com.example.backend.Library.enums.product.ProductStatus;
import com.example.backend.Library.model.dto.reponse.products.ProductVariantResponse;
import com.example.backend.Library.model.entity.products.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    @Mapping(target = "SKU", source = "SKU")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "size", source = "size.name") // Giả sử bạn muốn ánh xạ tên kích thước
    @Mapping(target = "color", source = "color.name") // Giả sử bạn muốn ánh xạ tên màu sắc
    @Mapping(target = "promotion", source = "promotion.name") // Giả sử bạn muốn ánh xạ mô tả khuyến mãi
    @Mapping(target = "status", source = "productVariant.status")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "updatedBy", source = "updatedBy")
    ProductVariantResponse toProductVariantResponse(ProductVariant productVariant);

    default ProductStatus mapStatus(int status) {
        return ProductStatus.fromValue(status);
    }
}
