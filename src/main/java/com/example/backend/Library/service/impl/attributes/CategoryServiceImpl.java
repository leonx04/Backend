package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.repository.attributes.BrandRepository;
import com.example.backend.Library.repository.attributes.CategoryRepository;
import com.example.backend.Library.service.interfaces.attributes.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;

    //Cần xử lý ex cho add , update , changeStatus

    @Transactional
    @Override
    public Category create(Category category) {
        categoryRepo.save(category);
        return category;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepo.findById(id);//optional<Category> // empty Optional
    }

    @Override
    public List<Category> findAll() {
        List<Category> brands = categoryRepo.findAll();
        return (brands.isEmpty() ? Collections.emptyList() : brands);
    }

    @Transactional
    @Override
    public Category update(Category category) {
        if (!categoryRepo.existsById(category.getId())) {
            throw new ResourceNotFoundException("Update failed: Category not found with ID: " + category.getId());
        }
        return categoryRepo.save(category);
    }

    @Override
    public boolean existsById(Integer integer) {
        return categoryRepo.existsById(integer);
    }

    @Override
    public void changeStatus(int id, int status) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category update status with id: " + id + " failed!"));
        category.setStatus(status);
        categoryRepo.save(category);
    }

}
