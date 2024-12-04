package com.example.backend.Library.service.interfaces.attributes;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;

public interface MaterialService {
    PageableResponse getPageData(AttributeParamRequest attributeParamRequest);

    PageableResponse getPageDataByPrefix(AttributeParamRequest attributeParamRequest);
    PageableResponse getEntityByName(AttributeParamRequest attributeParamRequest);
}
