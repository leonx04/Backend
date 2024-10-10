package com.example.backend.Library.model.entity.attributes;

import com.example.backend.Library.model.entity.products.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Size")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Size extends BaseEntity {
    private String name;
}
