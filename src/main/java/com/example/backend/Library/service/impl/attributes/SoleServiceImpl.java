package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.attributes.SoleResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.entity.attributes.Sole;
import com.example.backend.Library.model.mapper.attributes.SoleMapper;
import com.example.backend.Library.repository.attributes.sole.SoleCustomizeQueryRepository;
import com.example.backend.Library.repository.attributes.sole.SoleRepository;
import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import com.example.backend.Library.service.interfaces.attributes.SoleService;
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
public class SoleServiceImpl implements SoleService {
  SoleRepository soleRepository;
  ProductVariantRepository variantRepo;
  SoleCustomizeQueryRepository soleCustomizeQueryRepository;
  SoleMapper soleMapper;

  @Override
  public PageableResponse getPageData(AttributeParamRequest paramRequest) {
    PageableResponse pageableResponse = soleCustomizeQueryRepository.getPageData(paramRequest);

    List<Sole> soles = Optional.ofNullable(pageableResponse.getContent())
            .filter(content -> content instanceof List<?> && !((List<?>) content).isEmpty() && ((List<?>) content).get(0) instanceof Sole)
            .map(content -> (List<Sole>) content)
            .orElse(Collections.emptyList());

    List<SoleResponse> soleResponses = mapAndEnhanceSolesToSoleResponses(soles);
    pageableResponse.setContent(soleResponses);
    return pageableResponse;
  }

  @Override
  public PageableResponse getPageDataByPrefix(AttributeParamRequest paramRequest) {
    List<Sole> soles = soleCustomizeQueryRepository.getWithPagination(paramRequest);
    List<String> soleNames = soles.stream().map(Sole::getName).toList();

    Long totalElements = soleCustomizeQueryRepository.getTotalElements(paramRequest);

    return PageableResponse.<String>builder()
            .totalElements(totalElements)
            .pageSize(paramRequest.getPageSize())
            .totalPages((int) Math.ceil((double) totalElements / paramRequest.getPageSize()))
            .pageNo(paramRequest.getPageNo())
            .content(soleNames)
            .build();
  }

  @Override
  public PageableResponse getEntityByName(AttributeParamRequest paramRequest) {
    Optional<Sole> sole = soleRepository.findByName(paramRequest.getSearchByName());

    List<Sole> soles = sole.map(Collections::singletonList).orElse(Collections.emptyList());

    List<SoleResponse> content = mapAndEnhanceSolesToSoleResponses(soles);
    return PageableResponse.builder()
            .totalElements(content.isEmpty() ? 0L : 1L)
            .pageSize(paramRequest.getPageSize())
            .totalPages(content.isEmpty() ? 0 : 1)
            .pageNo(paramRequest.getPageNo())
            .content(content)
            .build();
  }

  private List<SoleResponse> mapAndEnhanceSolesToSoleResponses(List<Sole> soles) {
    List<SoleResponse> soleResponses = soles.stream()
            .map(sole -> {
              SoleResponse soleResponse = soleMapper.toSoleResponse(sole);
              soleResponse.setAppliedProductCount(variantRepo.countBySole(sole.getId()));
              return soleResponse;
            })
            .collect(Collectors.toList());
    return soleResponses;
  }
}
