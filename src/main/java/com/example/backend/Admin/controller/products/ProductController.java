package com.example.backend.Admin.controller.products;


import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.dto.request.products.ProductParamRequest;
import com.example.backend.Library.model.dto.response.ResponseData;
import com.example.backend.Library.model.entity.products.Product;
import com.example.backend.Library.service.interfaces.products.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
@RequestMapping("api/${api.version}/admin/products")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ProductController {

    ProductService productService;
//
//    @PostMapping
//    public ResponseData<?> create(
//            @Valid @RequestBody Product product
//    ){
//        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created product", this.productService.create(product));
//    }

    @GetMapping
    public ResponseData<?> getDataPage(
            @ModelAttribute  ProductParamRequest productParamRequest
    ) {
        // In ra giá trị các thuộc tính của productParamRequest
        System.out.println("Filter by Name: " + productParamRequest.getSearchByName());
        System.out.println("Page Number: " + productParamRequest.getPageNo());
        System.out.println("Page Size: " + productParamRequest.getPageSize());
        System.out.println("Sort By: " + productParamRequest.getSortBy());
        System.out.println("Sort Direction: " + productParamRequest.getSortDir());
        System.out.println("Status: " + productParamRequest.getStatus());
        System.out.println("Brand Id: " + productParamRequest.getBrandId());
        System.out.println("Category Id: " + productParamRequest.getCategoryId());
        System.out.println("Material Id: " + productParamRequest.getMaterialId());
        System.out.println("Sole Id: " + productParamRequest.getSoleId());

        // Trả về ResponseData
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all product successfully", this.productService.getPageData(productParamRequest));
    }

    @GetMapping("/search")
    public ResponseData<?> getDataPageByPrefix(
            @ModelAttribute ProductParamRequest productParamRequest
    ) {
        // In ra giá trị các thuộc tính của productParamRequest
        System.out.println("Filter by Name: " + productParamRequest.getSearchByName());
        System.out.println("Page Number: " + productParamRequest.getPageNo());
        System.out.println("Page Size: " + productParamRequest.getPageSize());
        System.out.println("Sort By: " + productParamRequest.getSortBy());
        System.out.println("Sort Direction: " + productParamRequest.getSortDir());
        System.out.println("Status: " + productParamRequest.getStatus());
        System.out.println("Brand Id: " + productParamRequest.getBrandId());
        System.out.println("Category Id: " + productParamRequest.getCategoryId());
        System.out.println("Material Id: " + productParamRequest.getMaterialId());
        System.out.println("Sole Id: " + productParamRequest.getSoleId());

        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved data successfully", this.productService.getPageDataByPrefix(productParamRequest));
    }


//    @GetMapping("{id}")
//    public ResponseData<?> getById(
//            @PathVariable @Min(value = 1) Integer id
//    ) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved product list", this.productService.findById(id));
//    }
//
//    @PutMapping("{id}")
//    public ResponseData<?> updateById(
//            @PathVariable @Min(value = 1) Integer id,
//            @Valid @RequestBody Product product
//    ) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated product", this.productService.update(product));
//    }
//
//    @PatchMapping("{id}")
//    public ResponseData<?> deleteById(
//            @PathVariable @Min(value = 1) Integer id
//    ) {
//        productService.changeStatus(id, 2);
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted product", null);
//    }

}
