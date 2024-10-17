package com.example.backend.Admin.controller.attributes;

import com.example.backend.Library.model.dto.reponse.ResponseData;
import com.example.backend.Library.model.entity.attributes.Sole;
import com.example.backend.Library.service.interfaces.attributes.SoleService;
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
@RequestMapping("api/${api.version}/admin/soles")
@RequiredArgsConstructor
@Validated
public class SoleController {

    private final SoleService soleService;

    @PostMapping
    public ResponseData<?> create(
            @Valid @RequestBody Sole sole
    ) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "Successfully created sole", this.soleService.create(sole));
    }

    @GetMapping
    public ResponseData<?> getList() {
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved all sole successfully", this.soleService.findAll());
    }

    @GetMapping("{id}")
    public ResponseData<?> getById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully retrieved sole list", this.soleService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseData<?> updateById(
            @PathVariable @Min(value = 1) Integer id,
            @Valid @RequestBody Sole sole
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully updated sole", this.soleService.update(sole));
    }

    @PatchMapping("{id}")
    public ResponseData<?> deleteById(
            @PathVariable @Min(value = 1) Integer id
    ) {
        soleService.changeStatus(id, 2);
        return new ResponseData<>(HttpStatus.OK.value(), "Successfully deleted sole", null);
    }
}
