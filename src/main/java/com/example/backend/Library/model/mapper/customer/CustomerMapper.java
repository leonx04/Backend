package com.example.backend.Library.model.mapper.customer;

import com.example.backend.Library.model.dto.request.RegisterRequest;
import com.example.backend.Library.model.dto.request.customer.CustomerRequest;
import com.example.backend.Library.model.dto.response.CustomerResponse;
import com.example.backend.Library.model.entity.customer.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "code", ignore = true) // Bỏ qua trường code khi ánh xạ
    Customer toCustomerRequest(CustomerRequest request);
    CustomerResponse toCustomer(Customer customer);

    @Mapping(target = "email", ignore = true) // Bỏ qua trường email khi ánh xạ
    Customer updateCustomer(CustomerRequest request);

    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "imageURL", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "code", ignore = true)
    Customer registerCustomer(RegisterRequest request);

}
