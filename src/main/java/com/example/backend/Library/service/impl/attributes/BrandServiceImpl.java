package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.attributes.BrandResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.mapper.attributes.BrandMapper;
import com.example.backend.Library.repository.attributes.brand.BrandCustomizeQueryRepository;
import com.example.backend.Library.repository.attributes.brand.BrandRepository;
import com.example.backend.Library.repository.employee.EmployeeRepo;
import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import com.example.backend.Library.service.interfaces.attributes.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class BrandServiceImpl implements BrandService {
     BrandRepository brandRepo;

     ProductVariantRepository variantRepo;

//    EmployeeRepo employeeRepo;

     BrandCustomizeQueryRepository brandCustomizeQueryRepository;
     BrandMapper brandMapper;

    @Override
    public PageableResponse getPageData(AttributeParamRequest paramRequest) {
        PageableResponse pageableResponse = brandCustomizeQueryRepository.getPageData(paramRequest);

        List<Brand> brands = Optional.ofNullable(pageableResponse.getContent())
                .filter(content -> content instanceof List<?> && !((List<?>) content).isEmpty() && ((List<?>) content).get(0) instanceof Brand)
                .map(content -> (List<Brand>) content)
                .orElse(Collections.emptyList());

        List<BrandResponse> brandResponses = mapAndEnhanceBrandsToBrandResponses(brands);//Map brands --> BrandResponse + bổ sung trường.
        pageableResponse.setContent(brandResponses);
        return pageableResponse;
    }

    @Override//lấy danh sách name trên 1 trang (pagination) dựa trên tiền tố.
    public PageableResponse getPageDataByPrefix(AttributeParamRequest paramRequest) {
        List<Brand> brands =  brandCustomizeQueryRepository.getWithPagination(paramRequest);
        List<String> brandNames = brands.stream().map(Brand::getName).toList();

        Long totalElements = brandCustomizeQueryRepository.getTotalElements(paramRequest);

        return PageableResponse.<String>builder()
                .totalElements(totalElements)
                .pageSize(paramRequest.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / paramRequest.getPageSize()))// tính tổng số trang = tổng số elements / kích thước 1 trang.
                .pageNo(paramRequest.getPageNo())
                .content(brandNames)
                .build();
    }

    @Override
    public PageableResponse getEntityByName(AttributeParamRequest paramRequest) {
        Optional<Brand> brand = brandRepo.findByName(paramRequest.getSearchByName());//entity || null.

        List<Brand> brands = brand.map(Collections::singletonList).orElse(Collections.emptyList());

        List<BrandResponse> content = mapAndEnhanceBrandsToBrandResponses(brands);
        return PageableResponse.builder()
                .totalElements(content.isEmpty() ? 0L : 1L)
                .pageSize(paramRequest.getPageSize())
                .totalPages(content.isEmpty() ? 0 : 1)
                .pageNo(paramRequest.getPageNo())//1
                .content(content)
                .build();
    }

    private List<BrandResponse> mapAndEnhanceBrandsToBrandResponses(List<Brand> brands) {
        List<BrandResponse> brandResponses = brands.stream()
                .map(brand -> {
                    BrandResponse brandResponse = brandMapper.toBrandResponse(brand);
                    brandResponse.setAppliedProductCount(variantRepo.countByBrand(brand.getId()));
                    return brandResponse;
                })
                .collect(Collectors.toList());
        return brandResponses;
    }
}
