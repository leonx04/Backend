package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.repository.attributes.CategoryRepository;
import com.example.backend.Library.service.interfaces.attributes.CategoryService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
}
