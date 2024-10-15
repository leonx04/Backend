package com.example.backend.Library.model.dto.request.customer;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddressRequest {
    String recipientName;
    @NotBlank(message = "Nhập số điện thoại người nhận")
    String recipientPhone;
    @NotBlank(message = "Nhập địa chỉ chi tiết")
    String detailAddress;
    @NotBlank(message = "Thành phố")
    String city;
    @NotBlank(message = "Quận/huyện")
    String district;
    @NotBlank(message = "Phường/xã")
    String commune;
//    @NotNull(message = "Trạng thái địa chỉ")
    Integer status;
    Integer customerId;
}