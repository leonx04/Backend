/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Library.model.dto.request.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientCustomerRequest implements Serializable {
    @Size(message = "Tên người dùng phải chứa tối thiểu 8 ký tự và tối đa 30 ký tự.", min = 8, max = 30)
    @NotBlank(message = "Không để trống tên người dùng.")
    String userName;
    @Size(message = "Mật khẩu phải từ 8 đến 32 ký tự", min = 8, max = 32)
    @NotBlank(message = "Không để trống password.")
    String password;
    String fullName;
    Integer gender;
    @Email(message = "Email không đúng định dạng.", regexp = "adasdas")
    String email;
    @Size(message = "Số điện thoại là chuỗi 10 chữ số.", min = 10, max = 10)
    @Pattern(message = "Sai định dạng số điện thoại.", regexp = "dfdasads")
    @NotBlank(message = "Không để trống số điện thoại")
    String phone;
    LocalDate birthDate;
    String imageURL;
    Integer status;
}