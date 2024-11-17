package com.example.backend.Library.model.entity.products;

import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.model.entity.attributes.Sole;
import com.example.backend.Library.model.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
     String name;
     String description;

    @ManyToOne()
    @JoinColumn(name = "categoryId" , referencedColumnName = "id")
     Category category;

    @ManyToOne()
    @JoinColumn(name = "brandId" , referencedColumnName = "id")
     Brand brand;

    @ManyToOne()
    @JoinColumn(name = "materialId" , referencedColumnName = "id")
     Material material;

    @ManyToOne()
    @JoinColumn(name = "soleId" , referencedColumnName = "id")
     Sole sole;

    int status;
    @CreationTimestamp
    LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    Employee createdBy;

    @CreationTimestamp
    LocalDate updatedAt;
    @ManyToOne
    @JoinColumn(name = "updatedBy", nullable = false)
    Employee updatedBy;

}