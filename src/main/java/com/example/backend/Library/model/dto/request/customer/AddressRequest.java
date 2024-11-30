package com.example.backend.Library.model.dto.request.customer;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddressRequest {
    @NotBlank(message = "Nhập tên người nhận")
    String recipientName;
    @NotBlank(message = "Nhập số điện thoại người nhận")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    String recipientPhone;
    @NotBlank(message = "Nhập địa chỉ chi tiết")
    String detailAddress;
    @NotBlank(message = "Chọn Thành phố")
    String city;
    @NotBlank(message = "Chọn Quận/huyện")
    String district;
    @NotBlank(message = "Chọn Phường/xã")
    String commune;
    Integer status;
    Integer customerId;
}