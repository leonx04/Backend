package com.example.backend.Library.model.dto.request.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {
    @NotBlank(message = "Mã nhân viên không được để trống")
    String code;
    @NotBlank(message = "Tên đăng nhập không được để trống")
    String userName;
    String passWord;

    @NotBlank(message = "Họ tên không được để trống")
    String fullName;
    int gender;
    @NotNull(message = "Ngày sinh không được để trống")
    Date birthDate;
    @NotBlank(message = "Số điện thoại không được để trống")
    String phone;
    @NotBlank(message = "Email không được để trống")
    String email;
    @NotBlank(message = "Địa chỉ không được để trống")
    String address;
    String imageUrl;

    int roleId;
    int status;
    String note;

    LocalDate createdAt;
    LocalDate updatedAt;
}