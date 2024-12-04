package com.example.backend.Library.service.input;

import com.example.backend.Library.exception.exceptioncustomer.EntityNotExistException;
import com.example.backend.Library.model.dto.request.products.product.ProductRequest;
import com.example.backend.Library.repository.attributes.category.CategoryRepository;
import com.example.backend.Library.repository.attributes.material.MaterialRepository;
import com.example.backend.Library.repository.attributes.sole.SoleRepository;
import com.example.backend.Library.repository.attributes.brand.BrandRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRequestValidator {
     CategoryRepository categoryRepository;
     BrandRepository brandRepository;
     MaterialRepository materialRepository;
     SoleRepository soleRepository;

    public void checkRequiredEntitiesExist(ProductRequest productRequest) {
        // TODO: check category, brand, material, sole exist by name --> query Id (exsist).
//        if (!categoryRepository.existsById(productRequest.getCategoryId())) {
//            throw new EntityNotExistException("Category with ID " + productRequest.getCategoryId() + " does not exist.");
//        }
//        if (!brandRepository.existsById(productRequest.getBrandId())) {
//            throw new EntityNotExistException("Brand with ID " + productRequest.getBrandId() + " does not exist.");
//        }
//        if (!materialRepository.existsById(productRequest.getMaterialId())) {
//            throw new EntityNotExistException("Material with ID " + productRequest.getMaterialId() + " does not exist.");
//        }
//        if (!soleRepository.existsById(productRequest.getSoleId())) {
//            throw new EntityNotExistException("Sole with ID " + productRequest.getSoleId() + " does not exist.");
//        }
    }

}
