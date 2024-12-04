package com.example.backend.Library.model.mapper.attributes;

import com.example.backend.Library.model.dto.reponse.attributes.MaterialResponse;
import com.example.backend.Library.model.entity.attributes.Material;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    MaterialResponse toMaterialResponse(Material material);

    List<MaterialResponse> toMaterialResponse(List<Material> materials);
}
