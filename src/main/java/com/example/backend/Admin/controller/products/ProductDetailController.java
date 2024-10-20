package com.example.backend.Admin.controller.products;


import com.example.backend.Library.model.dto.reponse.ResponseData;
import com.example.backend.Library.model.entity.products.ProductDetail;
import com.example.backend.Library.service.interfaces.products.ProductDetailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/${api.version}/admin/items")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins =  "http://127.0.0.1:5500/") // Cho phép truy cập từ địa chỉ này

public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @PostMapping
    public ResponseData<?> create(
            @Valid @RequestBody ProductDetail productDetail
    ){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created productDetail", this.productDetailService.create(productDetail));
    }


    @GetMapping
    public ResponseData<?> getList() {
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all productDetail successfully", this.productDetailService.findAll());
    }

    @GetMapping("{id}")
    public ResponseData<?> getById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved productDetail list", this.productDetailService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseData<?> updateById(
            @PathVariable @Min(value = 1) Integer id,
            @Valid @RequestBody ProductDetail productDetail
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated productDetail", this.productDetailService.update(productDetail));
    }

    @PatchMapping("{id}")
    public ResponseData<?> deleteById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        productDetailService.changeStatus(id, 2);
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted productDetail", null);
    }

    //New
    @GetMapping("/product/{productId}")
    public ResponseData<?> getByProductId(
            @PathVariable @Min(value = 1) Integer productId
    ) {
        return new ResponseData<>(HttpStatus.OK.value(),
                "Successfully retrieved product details for product ID: " + productId,
                this.productDetailService.findByProductId(productId));
    }

    @GetMapping("/promotion/{promotionId}")
    public ResponseData<?> getByPromotionId(
            @PathVariable @Min(value = 1) Integer promotionId
    ) {
        return new ResponseData<>(HttpStatus.OK.value(),
                "Successfully retrieved product details for promotion ID: " + promotionId,
                this.productDetailService.findByPromotionId(promotionId));
    }

}
