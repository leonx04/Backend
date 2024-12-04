package com.example.backend.Library.repository.attributes.size;

import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    Optional<Size> findByName(String name);
}
