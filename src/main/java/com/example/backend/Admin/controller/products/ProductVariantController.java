package com.example.backend.Admin.controller.products;


import com.example.backend.Library.model.dto.reponse.ResponseData;
import com.example.backend.Library.model.dto.request.products.ProductParamRequest;
import com.example.backend.Library.model.dto.request.products.ProductVariantParamRequest;
import com.example.backend.Library.service.interfaces.products.ProductVariantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/${api.version}/admin/product-variants")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ProductVariantController {

     ProductVariantService productVariantService;
//
//    @PostMapping
//    public ResponseData<?> create(
//            @Valid @RequestBody ProductVariant productVariantDetail
//    ){
//        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created productDetail", this.productVariantService.create(productVariantDetail));
//    }
//
//
    @GetMapping
    public ResponseData<?> getList(
            @ModelAttribute ProductVariantParamRequest productVariantParamRequest
            ) {
        // In ra giá trị các thuộc tính của `productVariantParamRequest`
        System.out.println("ProductVariantParamRequest values:");
        System.out.println("searchBySKU: " + productVariantParamRequest.getSearchBySKU());
        System.out.println("sortBy: " + productVariantParamRequest.getSortBy());
        System.out.println("sortDir: " + productVariantParamRequest.getSortDir());
        System.out.println("status: " + productVariantParamRequest.getStatus());
        System.out.println("pageNo: " + productVariantParamRequest.getPageNo());
        System.out.println("pageSize: " + productVariantParamRequest.getPageSize());
        System.out.println("sizeId: " + productVariantParamRequest.getSizeId());
        System.out.println("colorId: " + productVariantParamRequest.getColorId());
        System.out.println("promotionId: " + productVariantParamRequest.getPromotionId());
        System.out.println("minPrice: " + productVariantParamRequest.getMinPrice());
        System.out.println("maxPrice: " + productVariantParamRequest.getMaxPrice());
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all productDetail successfully", this.productVariantService.getPageData(productVariantParamRequest));
    }

    @GetMapping("/search")
    public ResponseData<?> getDataPageByPrefix(
            @ModelAttribute ProductVariantParamRequest productVariantParamRequest
    ) {
        // In ra giá trị các thuộc tính của `productVariantParamRequest`
        System.out.println("ProductVariantParamRequest values:");
        System.out.println("searchBySKU: " + productVariantParamRequest.getSearchBySKU());
        System.out.println("sortBy: " + productVariantParamRequest.getSortBy());
        System.out.println("sortDir: " + productVariantParamRequest.getSortDir());
        System.out.println("status: " + productVariantParamRequest.getStatus());
        System.out.println("pageNo: " + productVariantParamRequest.getPageNo());
        System.out.println("pageSize: " + productVariantParamRequest.getPageSize());
        System.out.println("sizeId: " + productVariantParamRequest.getSizeId());
        System.out.println("colorId: " + productVariantParamRequest.getColorId());
        System.out.println("promotionId: " + productVariantParamRequest.getPromotionId());
        System.out.println("minPrice: " + productVariantParamRequest.getMinPrice());
        System.out.println("maxPrice: " + productVariantParamRequest.getMaxPrice());
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved data successfully", this.productVariantService.getPageDataByPrefix(productVariantParamRequest));
    }

//
//    @GetMapping("{id}")
//    public ResponseData<?> getById(
//            @PathVariable @Min(value = 1) Integer id
//    ) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved productDetail list", this.productVariantService.findById(id));
//    }
//
//    @PutMapping("{id}")
//    public ResponseData<?> updateById(
//            @PathVariable @Min(value = 1) Integer id,
//            @Valid @RequestBody ProductVariant productVariantDetail
//    ) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated productDetail", this.productVariantService.update(productVariantDetail));
//    }
//
//    @PatchMapping("{id}")
//    public ResponseData<?> deleteById(
//            @PathVariable @Min(value = 1) Integer id
//    ) {
//        productVariantService.changeStatus(id, 2);
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted productDetail", null);
//    }

}
