package com.example.backend.Library.model.entity.products;

import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.model.entity.attributes.Sole;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "categoryId" , referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandId" , referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "materialId" , referencedColumnName = "id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "soleId" , referencedColumnName = "id")
    private Sole sole;

    private int status;

}
