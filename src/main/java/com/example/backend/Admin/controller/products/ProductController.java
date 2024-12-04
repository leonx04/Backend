package com.example.backend.Admin.controller.products;


import com.example.backend.Library.model.dto.reponse.ResponseData;
import com.example.backend.Library.model.dto.request.products.product.ProductParamRequest;
import com.example.backend.Library.model.dto.request.products.product.ProductRequest;
import com.example.backend.Library.service.interfaces.products.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/${api.version}/admin/products")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ProductController {
    ProductService productService;
    @PostMapping
    public ResponseData<?> create(
            @Valid @RequestBody ProductRequest productRequest
    ) {
        System.out.println("Nhận");
        System.out.println(productRequest.toString());
        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created product", this.productService.createProduct(productRequest));
    }


    @GetMapping
    public ResponseData<?> getDataPage(
            @ModelAttribute  ProductParamRequest productParamRequest
    ) {
        System.out.println( "searchByName " + productParamRequest.getSearchByName());
        System.out.println("sortBy " + productParamRequest.getSortBy());
        System.out.println("sortDir " + productParamRequest.getSortDir());
        System.out.println("status " + productParamRequest.getStatus());

        System.out.println("getDataPage");
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all product successfully", this.productService.getPageData(productParamRequest));
    }
    @GetMapping("/search")
    public ResponseData<?> getDataPageByPrefix(
            @ModelAttribute ProductParamRequest productParamRequest
    ) {
        System.out.println( "searchByName " + productParamRequest.getSearchByName());
        System.out.println("sortBy " + productParamRequest.getSortBy());
        System.out.println("sortDir " + productParamRequest.getSortDir());
        System.out.println("status " + productParamRequest.getStatus());
        System.out.println("getDataPageByPrefix");
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved data successfully", this.productService.getPageDataByPrefix(productParamRequest));
    }

    @GetMapping("/name")
    public ResponseData<?> getEntityByName(
            @ModelAttribute ProductParamRequest productParamRequest
    ) {
        System.out.println( "searchByName " + productParamRequest.getSearchByName());
        System.out.println("sortBy " + productParamRequest.getSortBy());
        System.out.println("sortDir " + productParamRequest.getSortDir());
        System.out.println("status " + productParamRequest.getStatus());
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved data successfully", this.productService.getEntityByName(productParamRequest));
    }

}
