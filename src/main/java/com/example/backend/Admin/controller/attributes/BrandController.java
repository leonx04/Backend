package com.example.backend.Admin.controller.attributes;

import com.example.backend.Library.model.dto.response.ResponseData;
import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.service.interfaces.attributes.BrandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/${api.version}/admin/brands")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins =  "http://127.0.0.1:5500/") // Cho phép truy cập từ địa chỉ này

public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseData<?> create(
            @Valid @RequestBody Brand brand
    ) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created brand", this.brandService.create(brand));
    }

    @GetMapping
    public ResponseData<?> getList() {
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all brands successfully", this.brandService.findAll());
    }

    @GetMapping("{id}")
    public ResponseData<?> getById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved brand list", this.brandService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseData<?> updateById(
            @PathVariable @Min(value = 1) Integer id,
            @Valid @RequestBody Brand brand
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated brand", this.brandService.update(brand));
    }

    @PatchMapping("{id}")
    public ResponseData<?> deleteById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        brandService.changeStatus(id, 2);
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted brand", null);
    }
}
