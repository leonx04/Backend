package com.example.backend.Library.service.impl.products;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import com.example.backend.Library.model.entity.products.Product;
import com.example.backend.Library.model.entity.products.Product;
import com.example.backend.Library.repository.attributes.BrandRepository;
import com.example.backend.Library.repository.products.ProductRepository;
import com.example.backend.Library.service.interfaces.GenericCrudService;
import com.example.backend.Library.service.interfaces.products.ProductService;
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
public class ProductServiceImpl implements ProductService {
    final ProductRepository productRepo;

    @Override
    public Product create(Product product) {
        productRepo.save(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepo.findById(id);//optional<Product> // empty Optional
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = productRepo.findAll();
        return (products.isEmpty() ? Collections.emptyList() : products);
    }

    @Override
    public Product update(Product product) {
        if (!productRepo.existsById(product.getId())) {
            throw new ResourceNotFoundException("Update failed: Product not found with ID: " + product.getId());
        }
        return productRepo.save(product);
    }

    @Override
    public boolean existsById(Integer id) {
        return productRepo.existsById(id);
    }

    @Override
    public void changeStatus(int id, int status) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product update status with id: " + id + " failed!"));
        product.setStatus(status);
        productRepo.save(product);
    }
}
