package com.example.backend.Library.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String code;
    String username;
    String password;
    String fullname;
    Integer gender;
    Date birthdate;
    String phone;
    String email;
    String address;
    Integer roleid;
    Integer status;
    String note;
    Date createdat;
    Date updatedat;
}
