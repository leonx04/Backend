package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.repository.attributes.CategoryRepository;
import com.example.backend.Library.repository.attributes.MaterialRepository;
import com.example.backend.Library.service.interfaces.attributes.CategoryService;
import com.example.backend.Library.service.interfaces.attributes.MaterialService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository materialRepo;

    //Cần xử lý ex cho add , update , changeStatus

    @Transactional
    @Override
    public Material create(Material material) {
        materialRepo.save(material);
        return material;
    }

    @Override
    public Optional<Material> findById(Integer id) {
        return materialRepo.findById(id);//optional<Material> // empty Optional
    }

    @Override
    public List<Material> findAll() {
        List<Material> brands = materialRepo.findAll();
        return (brands.isEmpty() ? Collections.emptyList() : brands);
    }

    @Transactional
    @Override
    public Material update(Material material) {
        if (!materialRepo.existsById(material.getId())) {
            throw new ResourceNotFoundException("Update failed: Material not found with ID: " + material.getId());
        }
        return materialRepo.save(material);
    }

    @Override
    public boolean existsById(Integer integer) {
        return materialRepo.existsById(integer);
    }

    @Override
    public void changeStatus(int id, int status) {
        Material material = materialRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Material update status with id: " + id + " failed!"));
        material.setStatus(status);
        materialRepo.save(material);
    }

}
