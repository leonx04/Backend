package com.example.backend.Library.service.impl.products;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.products.variant.ProductVariantResponse;
import com.example.backend.Library.model.dto.request.products.variant.ProductVariantParamRequest;
import com.example.backend.Library.model.entity.products.ProductVariant;
import com.example.backend.Library.model.mapper.products.ProductVariantMapper;
import com.example.backend.Library.repository.products.productVariant.ProductVariantCustomizeQueryRepository;
import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import com.example.backend.Library.service.interfaces.products.ProductVariantService;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantServiceImpl implements ProductVariantService {

     ProductVariantRepository variantRepo;
     ProductVariantCustomizeQueryRepository variantCustomQueryRepo;

     ProductVariantMapper variantMapper;

    @Override
    public PageableResponse getPageData(ProductVariantParamRequest productVariantParamRequest) {
        PageableResponse pageableResponse = variantCustomQueryRepo.getPageData(productVariantParamRequest);
        List<ProductVariant> variants = Optional.ofNullable(pageableResponse.getContent())
                .filter(content -> content instanceof List<?> && !((List<?>) content).isEmpty() && ((List<?>) content).get(0) instanceof ProductVariant)
                .map(content -> (List<ProductVariant>) content)
                .orElse(Collections.emptyList());

        List<ProductVariantResponse> variantResponses = mapVariantsToVariantResponses(variants);

        pageableResponse.setContent(variantResponses);
        return pageableResponse;
    }

    @Override
    public PageableResponse getPageDataByPrefix(ProductVariantParamRequest productVariantParamRequest) {
        List<ProductVariant> variants = variantCustomQueryRepo.getWithPagination(productVariantParamRequest);
        List<String> variantSKUs = variants.stream().map(ProductVariant::getSKU).toList();
        Long totalElements = variantCustomQueryRepo.getTotalElements(productVariantParamRequest);

        return PageableResponse.<String>builder()
                .totalElements(totalElements)
                .pageSize(productVariantParamRequest.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / productVariantParamRequest.getPageSize()))
                .pageNo(productVariantParamRequest.getPageNo())
                .content(variantSKUs)
                .build();
    }
    private List<ProductVariantResponse> mapVariantsToVariantResponses(List<ProductVariant> variants) {
        return variants.stream()
                .map(variant -> {
                    ProductVariantResponse variantResponse = variantMapper.toProductVariantResponse(variant);
                    return variantResponse;
                })
                .collect(Collectors.toList());
    }



//    public ProductVariant create(ProductVariant productVariantDetail) {
//        productDetailRepo.save(productVariantDetail);
//        return productVariantDetail;
//    }
//
//
//    public Optional<ProductVariant> findById(Integer id) {
//        return productDetailRepo.findById(id);//optional<ProductDetail> // empty Optional
//    }
//
//
//    public List<ProductVariant> findAll() {
//        List<ProductVariant> productVariantDetails = productDetailRepo.findAll();
//        return (productVariantDetails.isEmpty() ? Collections.emptyList() : productVariantDetails);
//    }
//
//
//    public ProductVariant update(ProductVariant productVariantDetail) {
//        if (!productDetailRepo.existsById(productVariantDetail.getId())) {
//            throw new ResourceNotFoundException("Update failed: ProductDetail not found with ID: " + productVariantDetail.getId());
//        }
//        return productDetailRepo.save(productVariantDetail);
//    }
//
//
//    public boolean existsById(Integer id) {
//        return productDetailRepo.existsById(id);
//    }
//
//
//    public void changeStatus(int id, int status) {
//        ProductVariant productVariantDetail = productDetailRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("ProductDetail update status with id: " + id + " failed!"));
//        productVariantDetail.setStatus(status);
//        productDetailRepo.save(productVariantDetail);
//    }
}
