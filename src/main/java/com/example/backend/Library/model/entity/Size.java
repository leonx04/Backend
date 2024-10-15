package com.example.backend.Library.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"Size\"")
public class Size {
    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @ColumnDefault("1")
    @Column(name = "Status")
    private Integer status;

    @ColumnDefault("getdate()")
    @Column(name = "CreatedAt")
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "UpdatedAt")
    private Instant updatedAt;

    @NotNull
    @Column(name = "CreatedBy", nullable = false)
    private Integer createdBy;

    @NotNull
    @Column(name = "UpdatedBy", nullable = false)
    private Integer updatedBy;

}