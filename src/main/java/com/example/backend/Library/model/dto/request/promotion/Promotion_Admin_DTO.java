package com.example.backend.Library.model.dto.request.promotion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promotion_Admin_DTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    private String code;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer quantity;
    private BigDecimal discountPercentage;
    private Integer status;
    private String createdAt;
    private String updatedAt;

}
