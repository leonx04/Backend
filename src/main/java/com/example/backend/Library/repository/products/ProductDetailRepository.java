package com.example.backend.Library.repository.products;

import com.example.backend.Library.model.entity.products.Product;
import com.example.backend.Library.model.entity.products.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
}
