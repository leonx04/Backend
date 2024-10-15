package com.example.backend.Library.service.interfaces;

import com.example.backend.Library.model.dto.request.customer.AddressRequest;
import com.example.backend.Library.model.dto.response.AddressResponse;
import com.example.backend.Library.model.entity.customer.Address;

import java.util.List;

public interface IAddressService {
    Address createAddress(Integer customerId, AddressRequest request) throws Exception;

    List<AddressResponse> getAllAddressOfCustomer(Integer customerId) throws Exception;

    AddressResponse getAddressById(Integer id);

    Address updateAddress(Integer id, AddressRequest request) throws Exception;

    void deleteAddress(Integer id);

    void updateDefaultAddress(Integer id);

}
