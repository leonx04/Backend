package com.example.backend.Library.model.entity.employee;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@Data
@Entity
@Table(name = "Employee")
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    String url;
    Integer roleid;
    Integer status;
    String note;
    Date createdat;
    Date updatedat;
}
