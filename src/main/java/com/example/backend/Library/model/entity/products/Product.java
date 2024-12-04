package com.example.backend.Library.model.entity.products;

import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.model.entity.attributes.Sole;
import com.example.backend.Library.model.entity.employee.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Product")
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
    LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    Employee createdBy;

    LocalDate updatedAt;
    @ManyToOne
    @JoinColumn(name = "updatedBy")
    Employee updatedBy;



}
