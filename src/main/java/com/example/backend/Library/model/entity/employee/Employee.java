package com.example.backend.Library.model.entity.employee;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Employee")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;
    private String userName;
    private String passWord;

    private String fullName;
    private int gender;
    private Date birthDate;
    private String phone;
    private String email;
    private String address;
    private String imageUrl;
    private int roleId;
    private int status;
    private String note;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}