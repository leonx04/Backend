package com.example.backend.Library.model.entity.attributes;

import com.example.backend.Library.model.entity.products.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Brand")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends BaseEntity {
    @NotBlank(message = "Name must not be empty")
    private String name;
}
