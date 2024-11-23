package com.example.backend.Admin.controller.attributes;

import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.dto.response.ResponseData;
import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.service.interfaces.attributes.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/${api.version}/admin/brands")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class BrandController {
    BrandService brandService;

    @GetMapping
    public ResponseData<?> getDataPage(
            @ModelAttribute AttributeParamRequest attributeParamRequest
    ) {
        // In ra giá trị các thuộc tính của attributeParamRequest
        System.out.println("Filter by Name: " + attributeParamRequest.getSearchByName());
        System.out.println("Page Number: " + attributeParamRequest.getPageNo());
        System.out.println("Page Size: " + attributeParamRequest.getPageSize());
        System.out.println("Sort By: " + attributeParamRequest.getSortBy());
        System.out.println("Sort Direction: " + attributeParamRequest.getSortDir());

        // Trả về ResponseData
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved data successfully", this.brandService.getPageData(attributeParamRequest));
    }

    @GetMapping("/search")
    public ResponseData<?> getDataPageByPrefix(
            @ModelAttribute AttributeParamRequest attributeParamRequest
    ) {
        // In ra giá trị các thuộc tính của attributeParamRequest
        System.out.println("Filter by Name: " + attributeParamRequest.getSearchByName());
        System.out.println("Page Number: " + attributeParamRequest.getPageNo());
        System.out.println("Page Size: " + attributeParamRequest.getPageSize());
        System.out.println("Sort By: " + attributeParamRequest.getSortBy());
        System.out.println("Sort Direction: " + attributeParamRequest.getSortDir());

        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved data successfully", this.brandService.getPageDataByPrefix(attributeParamRequest));
    }


//    @GetMapping("{id}")
//    public ResponseData<?> getById(
//            @PathVariable @Min(value = 1) Integer id
//    ) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved brand list", this.brandService.findById(id));
//        return null;
//    }

//    @PostMapping
//    public ResponseData<?> create(
//            @Valid @RequestBody Brand brand
//    ) {
//        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created brand", this.brandService.create(brand));
//    }

//    @PutMapping("{id}")
//    public ResponseData<?> updateById(
//            @PathVariable @Min(value = 1) Integer id,
//            @Valid @RequestBody Brand brand
//    ) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated brand", this.brandService.update(brand));
//    }

//    @PatchMapping("{id}")
//    public ResponseData<?> deleteById(
//            @PathVariable @Min(value = 1) Integer id
//    ) {
//        brandService.changeStatus(id, 2);
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted brand", null);
//    }
}
