package com.example.backend.Library.model.entity.promotion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Promotion")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int quantity;

    private double discountPercentage;

    private int status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
