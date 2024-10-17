package com.example.backend.Library.service.impl.products;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import com.example.backend.Library.model.entity.products.ProductDetail;
import com.example.backend.Library.repository.products.ProductDetailRepository;
import com.example.backend.Library.service.interfaces.products.ProductDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailServiceImpl implements ProductDetailService {

    final ProductDetailRepository productDetailRepo;
    @Override
    public ProductDetail create(ProductDetail productDetail) {
        productDetailRepo.save(productDetail);
        return productDetail;
    }

    @Override
    public Optional<ProductDetail> findById(Integer id) {
        return productDetailRepo.findById(id);//optional<ProductDetail> // empty Optional
    }

    @Override
    public List<ProductDetail> findAll() {
        List<ProductDetail> productDetails = productDetailRepo.findAll();
        return (productDetails.isEmpty() ? Collections.emptyList() : productDetails);
    }

    @Override
    public ProductDetail update(ProductDetail productDetail) {
        if (!productDetailRepo.existsById(productDetail.getId())) {
            throw new ResourceNotFoundException("Update failed: ProductDetail not found with ID: " + productDetail.getId());
        }
        return productDetailRepo.save(productDetail);
    }

    @Override
    public boolean existsById(Integer id) {
        return productDetailRepo.existsById(id);
    }

    @Override
    public void changeStatus(int id, int status) {
        ProductDetail productDetail = productDetailRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("ProductDetail update status with id: " + id + " failed!"));
        productDetail.setStatus(status);
        productDetailRepo.save(productDetail);
    }
}
