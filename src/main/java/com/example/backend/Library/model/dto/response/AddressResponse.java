package com.example.backend.Library.model.dto.response;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

@Value
public class AddressResponse implements Serializable {
    Integer id;
    String detailAddress;
    String city;
    String district;
    String commune;
    Integer status;
    Instant createdAt;
    Instant updatedAt;
}