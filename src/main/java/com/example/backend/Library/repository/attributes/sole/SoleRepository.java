package com.example.backend.Library.repository.attributes.sole;

import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.model.entity.attributes.Sole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoleRepository extends JpaRepository<Sole, Integer> {
    Optional<Sole> findByName(String name);

}
