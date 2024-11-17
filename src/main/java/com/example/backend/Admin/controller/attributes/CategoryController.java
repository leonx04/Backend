package com.example.backend.Admin.controller.attributes;

import com.example.backend.Library.model.dto.response.ResponseData;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.service.interfaces.attributes.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/${api.version}/admin/categories")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins =  "http://127.0.0.1:5500/") // Cho phép truy cập từ địa chỉ này

public class CategoryController {

    private final CategoryService categoryService;
//
//    @PostMapping
//    public ResponseData<?> create(
//            @Valid @RequestBody Category Category
//    ) {
//        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created Category", this.categoryService.create(Category));
//    }
//
//    @GetMapping
//    public ResponseData<?> getList() {
//        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all category successfully", this.categoryService.findAll());
//    }
//
//    @GetMapping("{id}")
//    public ResponseData<?> getById(
//            @PathVariable @Min(value = 1) Integer id
//    ) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved product list", this.categoryService.findById(id));
//    }
//
//    @PutMapping("{id}")
//    public ResponseData<?> updateById (
//            @PathVariable @Min(value = 1) Integer id,
//            @Valid @RequestBody Category Category
//    ) {
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated Category", this.categoryService.update(Category));
//    }
//
//    @PatchMapping("{id}")
//    public ResponseData<?> deleteById(
//            @PathVariable @Min(value = 1) Integer id
//    ) {
//        categoryService.changeStatus(id, 2);
//        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted Category", null);
//    }
}
