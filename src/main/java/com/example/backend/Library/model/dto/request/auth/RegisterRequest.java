package com.example.backend.Library.model.dto.request.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Không được để trống tên người dùng")
    private String userName;

    @NotBlank(message = "Không được để trống email")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Không được để trống mật khẩu")
    private String password;

    @NotBlank(message = "Nhập lại mật khẩu")
    private String retypePassword;
}
