package com.example.backend.Admin.controller.attributes;

import com.example.backend.Library.model.dto.reponse.ResponseData;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.service.interfaces.attributes.MaterialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class MaterialController {

    MaterialService materialService;

    @GetMapping
    public ResponseData<?> getDataPage(
            @ModelAttribute AttributeParamRequest attributeParamRequest
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved data successfully", this.materialService.getPageData(attributeParamRequest));
    }

}
