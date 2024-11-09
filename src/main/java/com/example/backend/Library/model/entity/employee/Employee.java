package com.example.backend.Library.model.entity.employee;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.*;
=======
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
>>>>>>> 8036a2dcda10800fec1c9aedc9fbb82687ff570f

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
