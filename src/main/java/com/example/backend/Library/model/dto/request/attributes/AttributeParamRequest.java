package com.example.backend.Library.model.dto.request.attributes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttributeParamRequest {
    //có thể đổi thành là Map<key, value> : để có thể lọc theo nhiều trường.
    String searchByName;
    String sortBy = "createdAt";//stt,name.
    String sortDir = "desc";//desc
    Integer pageNo = 1;
    Integer pageSize = 10;

}
