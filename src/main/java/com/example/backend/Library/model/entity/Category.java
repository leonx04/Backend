package com.example.backend.Library.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Code is mandatory")
    @Size(max = 255, message = "Code can have a maximum of 255 characters")
    private String code;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name can have a maximum of 255 characters")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Status is mandatory")
    private Integer status = 1;


}
