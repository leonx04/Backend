package com.example.backend.Library.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Builder
@Data
public class EmployeeRequest implements Serializable {
    String code;
    @NotBlank(message = "Tên đăng nhập không được để trống!")
    String userName;
    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 8, max = 32, message = "Password độ dài từ 8 đến 32 ký tự!")
    String password;
    @NotBlank(message = "Họ tên không được để trống!")
    String fullName;
    @NotNull(message = "Giới tính không được để trống!")
    Integer gender;
    @NotNull(message = "Ngày sinh không được để trống!")
    LocalDate birthDate;
    @NotBlank(message = "Số điện thoại không được để trống!")
    @Size(min = 10, max = 15, message = "Số điện thoại từ 10 đến 15 ký tự!")
    String phone;
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không đúng định dạng!")
    String email;
    @NotBlank(message = "Địa chỉ không được để trống!")
    String address;
    @NotBlank(message = "Ảnh không được để trống!")
    String url;
    @Builder.Default
    Integer roleId = 2;
    Integer status;
    String note;
    Instant createdAt;
    Instant updatedAt;
}