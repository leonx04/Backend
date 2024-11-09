package com.example.backend.Library.model.dto.reponse;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EntityResponse {
    List<Object> data;
    int totalPage;
}
