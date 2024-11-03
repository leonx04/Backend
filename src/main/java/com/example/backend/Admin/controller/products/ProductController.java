package com.example.backend.Admin.controller.products;


import com.example.backend.Library.model.dto.reponse.ResponseData;
import com.example.backend.Library.model.entity.products.Product;
import com.example.backend.Library.service.interfaces.products.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/${api.version}/admin/products")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins =  "http://127.0.0.1:5500/") // Cho phép truy cập từ địa chỉ này
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseData<?> create(
            @Valid @RequestBody Product product
    ){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created product", this.productService.create(product));
    }


    @GetMapping
    public ResponseData<?> getList() {
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all product successfully", this.productService.findAll());
    }

    @GetMapping("{id}")
    public ResponseData<?> getById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved product list", this.productService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseData<?> updateById(
            @PathVariable @Min(value = 1) Integer id,
            @Valid @RequestBody Product product
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated product", this.productService.update(product));
    }

    @PatchMapping("{id}")
    public ResponseData<?> deleteById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        productService.changeStatus(id, 2);
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted product", null);
    }

}
