package com.example.backend.Library.model.mapper.attributes;

import com.example.backend.Library.model.dto.reponse.attributes.ColorResponse;
import com.example.backend.Library.model.entity.attributes.Color;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    @Mapping(source = "hexColorCode", target = "hexColorCode")
    ColorResponse toColorResponse(Color color);

    @Mapping(source = "hexColorCode", target = "hexColorCode")
    List<ColorResponse> toColorResponse(List<Color> colors);
}
