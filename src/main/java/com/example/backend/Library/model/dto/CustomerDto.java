package com.example.backend.Library.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Data
public class CustomerDto implements Serializable {
    Integer id;
    String code;
    String userName;
    String password;
    String fullName;
    String gender;
    String email;
    String phone;
    LocalDate birthDate;
    String url;
    Integer status;
    Instant createdAt;
    Instant updatedAt;
}