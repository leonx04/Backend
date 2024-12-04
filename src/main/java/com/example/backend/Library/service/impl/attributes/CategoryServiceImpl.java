package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.attributes.CategoryResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.mapper.attributes.CategoryMapper;
import com.example.backend.Library.repository.attributes.category.CategoryCustomizeQueryRepository;
import com.example.backend.Library.repository.attributes.category.CategoryRepository;
import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import com.example.backend.Library.service.interfaces.attributes.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepo;
    ProductVariantRepository variantRepo;

    CategoryCustomizeQueryRepository categoryCustomizeQueryRepository;
    CategoryMapper categoryMapper;


    @Override
    public PageableResponse getPageData(AttributeParamRequest paramRequest) {
        PageableResponse pageableResponse = categoryCustomizeQueryRepository.getPageData(paramRequest);

        List<Category> categories = Optional.ofNullable(pageableResponse.getContent())
                .filter(content -> content instanceof List<?> && !((List<?>) content).isEmpty() && ((List<?>) content).get(0) instanceof Category)
                .map(content -> (List<Category>) content)
                .orElse(Collections.emptyList());

        List<CategoryResponse> categoryResponses = mapAndEnhanceCategoriesToCategoryResponses(categories);
        pageableResponse.setContent(categoryResponses);
        return pageableResponse;
    }

    @Override
    public PageableResponse getPageDataByPrefix(AttributeParamRequest paramRequest) {
        List<Category> categories = categoryCustomizeQueryRepository.getWithPagination(paramRequest);
        List<String> categoryNames = categories.stream().map(Category::getName).toList();

        Long totalElements = categoryCustomizeQueryRepository.getTotalElements(paramRequest);

        return PageableResponse.<String>builder()
                .totalElements(totalElements)
                .pageSize(paramRequest.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / paramRequest.getPageSize()))
                .pageNo(paramRequest.getPageNo())
                .content(categoryNames)
                .build();
    }

    @Override
    public PageableResponse getEntityByName(AttributeParamRequest paramRequest) {
        Optional<Category> category = categoryRepo.findByName(paramRequest.getSearchByName());

        List<Category> categories = category.map(Collections::singletonList).orElse(Collections.emptyList());

        List<CategoryResponse> content = mapAndEnhanceCategoriesToCategoryResponses(categories);
        return PageableResponse.builder()
                .totalElements(content.isEmpty() ? 0L : 1L)
                .pageSize(paramRequest.getPageSize())
                .totalPages(content.isEmpty() ? 0 : 1)
                .pageNo(paramRequest.getPageNo())
                .content(content)
                .build();
    }

    private List<CategoryResponse> mapAndEnhanceCategoriesToCategoryResponses(List<Category> categories) {
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> {
                    CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);
                    categoryResponse.setAppliedProductCount(variantRepo.countByCategory(category.getId()));
                    return categoryResponse;
                })
                .collect(Collectors.toList());
        return categoryResponses;
    }
}
