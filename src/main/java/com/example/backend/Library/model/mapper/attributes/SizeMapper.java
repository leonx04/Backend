package com.example.backend.Library.model.mapper.attributes;

import com.example.backend.Library.model.dto.reponse.attributes.ColorResponse;
import com.example.backend.Library.model.dto.reponse.attributes.SizeResponse;
import com.example.backend.Library.model.entity.attributes.Color;
import com.example.backend.Library.model.entity.attributes.Size;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    SizeResponse toSizeResponse(Size size);
    List<SizeResponse> toSizeResponse(List<Size> sizes);
}
