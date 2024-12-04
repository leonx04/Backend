package com.example.backend.Library.repository.products.product;

import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);

}
