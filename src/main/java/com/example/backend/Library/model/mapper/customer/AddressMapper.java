package com.example.backend.Library.model.mapper.customer;

import com.example.backend.Library.model.dto.request.customer.AddressRequest;
import com.example.backend.Library.model.dto.response.customer.AddressResponse;
import com.example.backend.Library.model.entity.customer.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "customerId", target = "customer.id")
    Address toAddress(AddressRequest request);

    @Mapping(source = "customer.id", target = "customerId")
    AddressResponse toAddressResponse(Address address);

//    @Mapping(source = "customer.id", target = "customerId")
    List<AddressResponse> toAddressResponses(List<Address> addresses);
}
