package com.example.backend.Library.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "code", nullable = false)
    private String code;

    @Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "useruame", nullable = false, length = 100)
    private String userName;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 255)
    @Nationalized
    @Column(name = "fullname")
    private String fullName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Size(max = 20)
    @Nationalized
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 255)
    @Nationalized
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Nationalized
    @Column(name = "address")
    private String address;

    @Size(max = 255)
    @Nationalized
    @Column(name = "url")
    private String url;

    @ColumnDefault("2")
    @Column(name = "roleid")
    private Integer roleId;

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @Nationalized
    @Lob
    @Column(name = "note")
    private String note;

    @ColumnDefault("getdate()")
    @Column(name = "createdat")
    private Instant createdAt;

    @ColumnDefault("getdate()")
    @Column(name = "updatedat")
    private Instant updatedAt;

}