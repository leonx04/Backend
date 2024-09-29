package com.example.backend.Library.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class AddressDto implements Serializable {
    Integer id;
    String detailAddress;
    String city;
    String district;
    String commune;
    Integer status;
    Instant createdAt;
    Instant updatedAt;
}