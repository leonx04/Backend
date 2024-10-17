package com.example.backend.Library.repository.attributes;

import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Sole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoleRepository extends JpaRepository<Sole, Integer> {
}
