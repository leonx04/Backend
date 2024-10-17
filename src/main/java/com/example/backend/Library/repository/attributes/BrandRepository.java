package com.example.backend.Library.repository.attributes;

import com.example.backend.Library.model.entity.attributes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
