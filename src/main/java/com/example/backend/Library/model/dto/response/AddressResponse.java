package com.example.backend.Library.model.dto.response;

import lombok.Value;

@Value
public class AddressResponse extends BaseResponse{
    Integer id;
    String detailAddress;
    String recipientName;
    String recipientPhone;
    String city;
    String district;
    String commune;
    Integer status;
    Integer customerId;
}