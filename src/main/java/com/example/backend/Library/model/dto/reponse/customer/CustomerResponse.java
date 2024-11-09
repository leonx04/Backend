package com.example.backend.Library.model.dto.reponse.customer;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    LocalDate birthDate;
    String imageURL;
    Integer status;
}