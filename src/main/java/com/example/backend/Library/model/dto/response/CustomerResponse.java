package com.example.backend.Library.model.dto.response;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Value
public class CustomerResponse implements Serializable {
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