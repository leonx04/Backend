package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.attributes.SizeResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.entity.attributes.Size;
import com.example.backend.Library.model.mapper.attributes.SizeMapper;
import com.example.backend.Library.repository.attributes.size.SizeCustomizeQueryRepository;
import com.example.backend.Library.repository.attributes.size.SizeRepository;
import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import com.example.backend.Library.service.interfaces.attributes.SizeService;
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
public class SizeServiceImpl implements SizeService {

    SizeRepository sizeRepo;
    ProductVariantRepository variantRepo;
    SizeCustomizeQueryRepository sizeCustomizeQueryRepository;
    SizeMapper sizeMapper;

    @Override
    public PageableResponse getPageData(AttributeParamRequest paramRequest) {
        PageableResponse pageableResponse = sizeCustomizeQueryRepository.getPageData(paramRequest);

        List<Size> sizes = Optional.ofNullable(pageableResponse.getContent())
                .filter(content -> content instanceof List<?> && !((List<?>) content).isEmpty() && ((List<?>) content).get(0) instanceof Size)
                .map(content -> (List<Size>) content)
                .orElse(Collections.emptyList());

        List<SizeResponse> sizeResponses = mapAndEnhanceSizesToSizeResponses(sizes); // Map sizes --> SizeResponse + bổ sung trường.
        pageableResponse.setContent(sizeResponses);
        return pageableResponse;
    }

    @Override
    public PageableResponse getPageDataByPrefix(AttributeParamRequest paramRequest) {
        List<Size> sizes = sizeCustomizeQueryRepository.getWithPagination(paramRequest);
        List<String> sizeNames = sizes.stream().map(Size::getName).collect(Collectors.toList());

        Long totalElements = sizeCustomizeQueryRepository.getTotalElements(paramRequest);

        return PageableResponse.<String>builder()
                .totalElements(totalElements)
                .pageSize(paramRequest.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / paramRequest.getPageSize())) // tính tổng số trang = tổng số elements / kích thước 1 trang.
                .pageNo(paramRequest.getPageNo())
                .content(sizeNames)
                .build();
    }

    @Override
    public PageableResponse getEntityByName(AttributeParamRequest paramRequest) {
        Optional<Size> size = sizeRepo.findByName(paramRequest.getSearchByName()); // entity || null.

        List<Size> sizes = size.map(Collections::singletonList).orElse(Collections.emptyList());

        List<SizeResponse> content = mapAndEnhanceSizesToSizeResponses(sizes);
        return PageableResponse.builder()
                .totalElements(content.isEmpty() ? 0L : 1L)
                .pageSize(paramRequest.getPageSize())
                .totalPages(content.isEmpty() ? 0 : 1)
                .pageNo(paramRequest.getPageNo()) // 1
                .content(content)
                .build();
    }

    private List<SizeResponse> mapAndEnhanceSizesToSizeResponses(List<Size> sizes) {
        return sizes.stream()
                .map(size -> {
                    SizeResponse sizeResponse = sizeMapper.toSizeResponse(size);
                    sizeResponse.setAppliedProductCount(variantRepo.countBySize(size.getId())); // Cập nhật số lượng sản phẩm áp dụng với size này.
                    return sizeResponse;
                })
                .collect(Collectors.toList());
    }
}
