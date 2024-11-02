package com.example.backend.Library.repository.attributes;

import com.example.backend.Library.model.entity.attributes.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
}
