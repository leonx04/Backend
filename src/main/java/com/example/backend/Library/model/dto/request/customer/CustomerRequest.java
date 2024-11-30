/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

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

    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private String imageURL;

    @ColumnDefault("1")
    private Integer status;
}