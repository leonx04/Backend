package com.example.backend.Admin.controller.attributes;

import com.example.backend.Library.model.dto.reponse.ResponseData;
import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.service.interfaces.attributes.BrandService;
import com.example.backend.Library.service.interfaces.attributes.MaterialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/${api.version}/admin/materials")
@RequiredArgsConstructor
@Validated
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public ResponseData<?> create(
            @Valid @RequestBody Material material
    ) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created material", this.materialService.create(material));
    }

    @GetMapping
    public ResponseData<?> getList() {
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all material successfully", this.materialService.findAll());
    }

    @GetMapping("{id}")
    public ResponseData<?> getById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved material list", this.materialService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseData<?> updateById(
            @PathVariable @Min(value = 1) Integer id,
            @Valid @RequestBody Material material
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated material", this.materialService.update(material));
    }

    @PatchMapping("{id}")
    public ResponseData<?> deleteById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        materialService.changeStatus(id, 2);
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted material", null);
    }
}
