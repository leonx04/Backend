package com.example.backend.Library.model.mapper.attributes;

import com.example.backend.Library.model.dto.reponse.attributes.BrandResponse;
import com.example.backend.Library.model.dto.reponse.attributes.CategoryResponse;
import com.example.backend.Library.model.entity.attributes.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponses(List<Category> categories);
}
