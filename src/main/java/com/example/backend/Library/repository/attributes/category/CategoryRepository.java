package com.example.backend.Library.repository.attributes.category;

import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.entity.attributes.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}
