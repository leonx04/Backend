package com.example.backend.Library.model.dto.request.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @Size(min = 8, max = 50, message = "Độ dài tên người dùng phải từ 8 đến 50 ký tự")
    @NotBlank(message = "Không được để trống tên người dùng")
    private String userName;

    @NotBlank(message = "Không được để trống email")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email không đúng định dạng")
    private String email;

    @Size(message = "Độ dài mật khẩu phải từ 8 đến 50 ký tự", min = 8, max = 50)
    @NotBlank(message = "Không được để trống mật khẩu")
    private String password;

    @NotBlank(message = "Nhập lại mật khẩu")
    private String retypePassword;
}
