package com.example.backend.Library.service.impl.products;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.reponse.products.product.ProductResponse;
import com.example.backend.Library.model.dto.reponse.products.variant.ProductVariantResponse;
import com.example.backend.Library.model.dto.request.products.product.ProductParamRequest;
import com.example.backend.Library.model.dto.request.products.product.ProductRequest;
import com.example.backend.Library.model.entity.products.Product;
import com.example.backend.Library.model.entity.products.ProductVariant;
import com.example.backend.Library.model.mapper.products.ProductMapper;
import com.example.backend.Library.model.mapper.products.ProductVariantMapper;
import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import com.example.backend.Library.repository.products.product.ProductCustomizeQueryRepository;
import com.example.backend.Library.repository.products.product.ProductRepository;
import com.example.backend.Library.service.input.ProductRequestValidator;
import com.example.backend.Library.service.interfaces.products.ProductService;
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
public class ProductServiceImpl implements ProductService {


    ProductRequestValidator validator;

    ProductCustomizeQueryRepository productCustomizeQueryRepository;
    ProductRepository productRepo;
    ProductMapper productMapper;
    ProductVariantRepository variantRepo;
    ProductVariantMapper variantMapper;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        //validator.checkRequiredEntitiesExist(productRequest);
        List<ProductVariant> productVariants = productRequest.getProductVariants().stream()
                .map(variantMapper::toProductVariant)
                .toList();

        productRepo.save(product);
        variantRepo.saveAll(productVariants);
        return product.getId();
    }

    @Override
    public PageableResponse getPageData(ProductParamRequest paramRequest) {
        PageableResponse pageableResponse = productCustomizeQueryRepository.getPageData(paramRequest);
        List<Product> products = Optional.ofNullable(pageableResponse.getContent())
                .filter(content -> content instanceof List<?> && !((List<?>) content).isEmpty() && ((List<?>) content).get(0) instanceof Product)
                .map(content -> (List<Product>) content)
                .orElse(Collections.emptyList());

        List<ProductResponse> productResponses = mapAndEnhanceBrandsToBrandResponses(products);
        pageableResponse.setContent(productResponses);
        return pageableResponse;
    }

    @Override
    public PageableResponse getPageDataByPrefix(ProductParamRequest paramRequest) {
        List<Product> products = productCustomizeQueryRepository.getWithPagination(paramRequest);
        List<String> productNames = products.stream().map(Product::getName).toList();
        Long totalElements = productCustomizeQueryRepository.getTotalElements(paramRequest);

        return PageableResponse.<String>builder()
                .totalElements(totalElements)
                .pageSize(paramRequest.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / paramRequest.getPageSize()))
                .pageNo(paramRequest.getPageNo())
                .content(productNames)
                .build();
    }


    @Override
    public PageableResponse getEntityByName(ProductParamRequest paramRequest) {
        Optional<Product> product = productRepo.findByName(paramRequest.getSearchByName());//entity || null.

        List<Product> products = product.map(Collections::singletonList).orElse(Collections.emptyList());

        List<ProductResponse> content = mapAndEnhanceBrandsToBrandResponses(products);
        return PageableResponse.builder()
                .totalElements(content.isEmpty() ? 0L : 1L)
                .pageSize(paramRequest.getPageSize())
                .totalPages(content.isEmpty() ? 0 : 1)
                .pageNo(paramRequest.getPageNo())//1
                .content(content)
                .build();
    }

    private List<ProductResponse> mapAndEnhanceBrandsToBrandResponses(List<Product> products) {
        List<ProductResponse> productResponses = products.stream()
                .map(product -> {
                    ProductResponse productResponse = productMapper.toProductResponse(product);
                    productResponse.setAppliedVariantProductCount(variantRepo.countByProductId(product.getId()));
                    return productResponse;
                })
                .collect(Collectors.toList());
        return productResponses;
    }
}
