package com.example.backend.Library.model.mapper.attributes;

import com.example.backend.Library.model.dto.reponse.attributes.SoleResponse;
import com.example.backend.Library.repository.attributes.sole.SoleRepository;
import com.example.backend.Library.model.entity.attributes.Sole;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SoleMapper {
    SoleResponse toSoleResponse(Sole sole);

    List<SoleResponse> toSoleResponse(List<Sole> soles);
}
