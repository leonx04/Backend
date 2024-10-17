package com.example.backend.Library.repository.attributes;

import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
}
