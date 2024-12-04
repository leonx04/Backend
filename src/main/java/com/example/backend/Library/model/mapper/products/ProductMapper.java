package com.example.backend.Library.model.mapper.products;

import com.example.backend.Library.enums.product.ProductStatus;
import com.example.backend.Library.model.dto.reponse.products.product.ProductResponse;
import com.example.backend.Library.model.dto.request.products.product.ProductRequest;
import com.example.backend.Library.model.entity.products.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", source = "product.category.name")
    @Mapping(target = "brand", source = "product.brand.name")
    @Mapping(target = "material", source = "product.material.name")
    @Mapping(target = "sole", source = "product.sole.name")
    @Mapping(target = "status", source = "product.status" , qualifiedByName = "convertToProductStatus")
    ProductResponse toProductResponse(Product product);



    @Mapping(target = "category.name", source = "category")
    @Mapping(target = "brand.name", source = "brand")
    @Mapping(target = "material.name", source = "material")
    @Mapping(target = "sole.name", source = "sole")
    @Mapping(target = "createdBy.id", source = "userId")
    @Mapping(target = "status", source = "status", qualifiedByName = "convertToStatusCode")
    Product toProduct(ProductRequest productRequest
//            ,
//                      @Context CategoryRepository categoryRepository,
//                      @Context BrandRepository brandRepository,
//                      @Context MaterialRepository materialRepository,
//                      @Context SoleRepository soleRepository,
//                      @Context EmployeeRepository employeeRepository
    );

    @Named("convertToProductStatus")
    default ProductStatus convertToProductStatus(int statusCode) {//status can be : 1,2,3
        return ProductStatus.fromValue(statusCode);//return : ALL,ACTIVE,INACTIVE.
    }
    @Named("convertToStatusCode")
    default int convertToStatusCode(ProductStatus status) {
        return status.getValue();
    }

//    @Named("mapCategory")
//    default Category mapCategory(Integer categoryId, @Context CategoryRepository categoryRepository) {
//        return categoryId != null ? categoryRepository.findById(categoryId).orElse(null) : null;
//    }
//
//    @Named("mapBrand")
//    default Brand mapBrand(Integer brandId, @Context BrandRepository brandRepository) {
//        return brandId != null ? brandRepository.findById(brandId).orElse(null) : null;
//    }
//
//    @Named("mapMaterial")
//    default Material mapMaterial(Integer materialId, @Context MaterialRepository materialRepository) {
//        return materialId != null ? materialRepository.findById(materialId).orElse(null) : null;
//    }
//
//    @Named("mapSole")
//    default Sole mapSole(Integer soleId, @Context SoleRepository soleRepository) {
//        return soleId != null ? soleRepository.findById(soleId).orElse(null) : null;
//    }
//
//    @Named("mapEmployee")
//    default Employee mapEmployee(Integer userId, @Context EmployeeRepository employeeRepository) {
//        return userId != null ? employeeRepository.findById(userId).orElse(null) : null;
//    }

}
