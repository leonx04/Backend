package com.example.backend.Library.model.dto.response;

import lombok.Value;

import java.time.LocalDate;

@Value
public class CustomerResponse extends BaseResponse{
    Integer id;
    String code;
    String userName;
    String password;
    String fullName;
    Integer gender;
    String email;
    String phone;
    LocalDate birthDate;
    String imageURL;
    Integer status;
}