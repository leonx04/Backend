package com.example.backend.Library.model.dto.request.customer;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Data
@Builder
public class CustomerRequest {
    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;

    private String retypePassword;

    private String fullName;

    private Integer gender;

    @NotBlank(message = "Email is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private LocalDate birthDate;

    private String imageURL;

    @ColumnDefault("1")
    private Integer status;
}