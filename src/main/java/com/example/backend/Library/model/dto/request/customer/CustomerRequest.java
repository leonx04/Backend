package com.example.backend.Library.model.dto.request.customer;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class CustomerRequest {
    private String userName;

    private String password;

    private String retypePassword;

    private String fullName;

    private Integer gender;

//    @NotBlank(message = "Email is required")
//    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private String imageURL;

    @ColumnDefault("1")
    private Integer status;
}