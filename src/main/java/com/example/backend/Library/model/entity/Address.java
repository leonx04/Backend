package com.example.backend.Library.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "detailaddress", nullable = false)
    private String detailAddress;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "city", nullable = false)
    private String city;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "district", nullable = false)
    private String district;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "commune", nullable = false)
    private String commune;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @ColumnDefault("getdate()")
    @Column(name = "createdat")
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "updatedat")
    private Instant updatedAt;

}