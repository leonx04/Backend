package com.example.backend.Library.model.dto.request.attributes;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class AttributeRequest {
    @NotBlank(message = "Name must not be empty")
    String name;
    @Min(value = 1 , message = "Error when get staff id")
    int createdBy;
}
