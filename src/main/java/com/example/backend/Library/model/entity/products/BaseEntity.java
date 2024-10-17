package com.example.backend.Library.model.entity.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.IMessage;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @Min(value = 1, message = "Status must not be less than 1")

    private int status;


    @CreationTimestamp
    private LocalDate createdAt;
    @Min(value = 1, message = "createdBy not be less than 1")

    private int createdBy;
    @CreationTimestamp

    private LocalDate updatedAt;
    @Min(value = 1, message = "updatedBy not be less than 1")

    private int updatedBy;
}
