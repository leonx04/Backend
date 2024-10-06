package com.example.backend.Library.model.dto.response;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Value
public class EmployeeResponse implements Serializable {
    Integer id;
    String code;
    String userName;
    String password;
    String fullName;
    Integer gender;
    LocalDate birthDate;
    String phone;
    String email;
    String address;
    String url;
    Integer roleId;
    Integer status;
    String note;
    Instant createdAt;
    Instant updatedAt;
}