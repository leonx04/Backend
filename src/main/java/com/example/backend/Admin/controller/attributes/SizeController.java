package com.example.backend.Admin.controller.attributes;

import com.example.backend.Library.model.dto.reponse.ResponseData;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.service.interfaces.attributes.SizeService;
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
@RequestMapping("api/${api.version}/admin/sizes")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class SizeController {

    SizeService sizeService;

    @GetMapping
    public ResponseData<?> getDataPage(
            @ModelAttribute AttributeParamRequest attributeParamRequest
    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved data successfully", this.sizeService.getPageData(attributeParamRequest));
    }

}
