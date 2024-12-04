package com.example.backend.Library.repository.attributes.color;

import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    Optional<Color> findByName(String name);
}

