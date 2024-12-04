package com.example.backend.Library.repository.attributes.brand;

import com.example.backend.Library.model.entity.attributes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByName(String name);
}
