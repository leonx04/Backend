package com.example.backend.Library.repository.attributes.material;

import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Optional<Material> findByName(String name);

}
