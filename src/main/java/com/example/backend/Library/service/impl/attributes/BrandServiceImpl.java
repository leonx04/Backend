package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.repository.attributes.BrandRepository;
import com.example.backend.Library.service.interfaces.attributes.BrandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepo;

    //Cần xử lý ex cho add , update , changeStatus

    @Transactional
    @Override
    public Brand create(Brand brand) {
        brandRepo.save(brand);
        return brand;
    }

    @Override
    public Optional<Brand> findById(Integer id) {
        return brandRepo.findById(id);//optional<Brand> // empty Optional
    }

    @Override
    public List<Brand> findAll() {
        List<Brand> brands = brandRepo.findAll();
        return (brands.isEmpty() ? Collections.emptyList() : brands);
    }

    @Transactional
    @Override
    public Brand update(Brand brand) {
        if (!brandRepo.existsById(brand.getId())) {
            throw new ResourceNotFoundException("Update failed: Brand not found with ID: " + brand.getId());
        }
        return brandRepo.save(brand);
    }

    @Override
    public boolean existsById(Integer id) {
        return brandRepo.existsById(id);
    }

    @Override
    public void changeStatus(int id, int status) {
        Brand brand = brandRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand update status with id: " + id + " failed!"));
        brand.setStatus(status);
        brandRepo.save(brand);
    }

}
