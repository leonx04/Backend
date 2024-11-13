package com.example.backend.Library.model.mapper.attributes;

import com.example.backend.Library.model.dto.reponse.attributes.BrandResponse;
import com.example.backend.Library.model.entity.attributes.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandResponse toBrandResponse(Brand brand);

    List<BrandResponse> toBrandResponse(List<Brand> brands);
}
