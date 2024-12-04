package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.attributes.MaterialResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.model.mapper.attributes.MaterialMapper;
import com.example.backend.Library.repository.attributes.material.MaterialCustomizeQueryRepository;
import com.example.backend.Library.repository.attributes.material.MaterialRepository;
import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import com.example.backend.Library.service.interfaces.attributes.MaterialService;
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
public class MaterialServiceImpl implements MaterialService {
     MaterialRepository materialRepository;
     ProductVariantRepository variantRepo;
     MaterialCustomizeQueryRepository materialCustomizeQueryRepository;
     MaterialMapper materialMapper;

     @Override
     public PageableResponse getPageData(AttributeParamRequest paramRequest) {
          PageableResponse pageableResponse = materialCustomizeQueryRepository.getPageData(paramRequest);

          List<Material> materials = Optional.ofNullable(pageableResponse.getContent())
                  .filter(content -> content instanceof List<?> && !((List<?>) content).isEmpty() && ((List<?>) content).get(0) instanceof Material)
                  .map(content -> (List<Material>) content)
                  .orElse(Collections.emptyList());

          List<MaterialResponse> materialResponses = mapAndEnhanceMaterialsToMaterialResponses(materials);
          pageableResponse.setContent(materialResponses);
          return pageableResponse;
     }

     @Override
     public PageableResponse getPageDataByPrefix(AttributeParamRequest paramRequest) {
          List<Material> materials = materialCustomizeQueryRepository.getWithPagination(paramRequest);
          List<String> materialNames = materials.stream().map(Material::getName).toList();

          Long totalElements = materialCustomizeQueryRepository.getTotalElements(paramRequest);

          return PageableResponse.<String>builder()
                  .totalElements(totalElements)
                  .pageSize(paramRequest.getPageSize())
                  .totalPages((int) Math.ceil((double) totalElements / paramRequest.getPageSize()))
                  .pageNo(paramRequest.getPageNo())
                  .content(materialNames)
                  .build();
     }

     @Override
     public PageableResponse getEntityByName(AttributeParamRequest paramRequest) {
          Optional<Material> material = materialRepository.findByName(paramRequest.getSearchByName());

          List<Material> materials = material.map(Collections::singletonList).orElse(Collections.emptyList());

          List<MaterialResponse> content = mapAndEnhanceMaterialsToMaterialResponses(materials);
          return PageableResponse.builder()
                  .totalElements(content.isEmpty() ? 0L : 1L)
                  .pageSize(paramRequest.getPageSize())
                  .totalPages(content.isEmpty() ? 0 : 1)
                  .pageNo(paramRequest.getPageNo())
                  .content(content)
                  .build();
     }

     private List<MaterialResponse> mapAndEnhanceMaterialsToMaterialResponses(List<Material> materials) {
          List<MaterialResponse> materialResponses = materials.stream()
                  .map(material -> {
                       MaterialResponse materialResponse = materialMapper.toMaterialResponse(material);
                       materialResponse.setAppliedProductCount(variantRepo.countByMaterial(material.getId()));
                       return materialResponse;
                  })
                  .collect(Collectors.toList());
          return materialResponses;
     }
}
