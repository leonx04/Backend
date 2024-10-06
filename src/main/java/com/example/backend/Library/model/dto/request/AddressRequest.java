package com.example.backend.Library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class AddressRequest implements Serializable {
    @NotBlank(message = "Không được để trống dịa chỉ chi tiết!")
    String detailAddress;
    @NotBlank(message = "Không được để trống quốc gia!")
    String country;
    @NotBlank(message = "Không được để trống thành phố!")
    String city;
    @NotBlank(message = "Không được để trống quận/huyện!")
    String district;
    @NotBlank(message = "Không được để trống xã/phường!")
    String commune;
    Integer status;
    Instant createdAt;
    Instant updatedAt;
}