package com.example.backend.Library.model.mapper.products;

import com.example.backend.Library.enums.product.ProductStatus;
import com.example.backend.Library.model.dto.reponse.products.variant.ProductVariantResponse;
import com.example.backend.Library.model.dto.request.products.variant.ProductVariantRequest;
import com.example.backend.Library.model.entity.products.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    @Mapping(target = "size", source = "size.name")
    @Mapping(target = "color", source = "color.name")
    @Mapping(target = "promotion", source = "promotion.name")
    @Mapping(target = "status", source = "productVariant.status" , qualifiedByName  = "convertToProductStatus")
    ProductVariantResponse toProductVariantResponse(ProductVariant productVariant);


    @Mapping(target = "SKU", source = "SKU")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
//    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "size.id", source = "sizeId")
    @Mapping(target = "color.id", source = "colorId")
    @Mapping(target = "status", source = "status", qualifiedByName = "convertToStatusCode")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "createdBy.id", source = "userId")
    ProductVariant toProductVariant(ProductVariantRequest productVariantRequest);
    // trên đang mapper cho create, nhưng nếu đối sang up thì bổ sung thêm id sp + đổi thaành upBy xem.

    @Named("convertToProductStatus")
    default ProductStatus convertToProductStatus(int statusCode) {//status can be : 1,2,3
        return ProductStatus.fromValue(statusCode);//return : ALL,ACTIVE,INACTIVE.
    }
    @Named("convertToStatusCode")
    default int convertToStatusCode(ProductStatus status) {
        return status.getValue();
    }
}
