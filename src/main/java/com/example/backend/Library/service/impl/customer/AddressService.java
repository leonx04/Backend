package com.example.backend.Library.service.impl.customer;

import com.example.backend.Library.exception.DataNotFoundException;
import com.example.backend.Library.model.dto.request.customer.AddressRequest;
import com.example.backend.Library.model.dto.response.customer.AddressResponse;
import com.example.backend.Library.model.entity.customer.Address;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.model.mapper.customer.AddressMapper;
import com.example.backend.Library.repository.customer.AddressRepository;
import com.example.backend.Library.repository.customer.CustomerRepository;
import com.example.backend.Library.service.interfaces.customer.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public Address createAddress(Integer customerId, AddressRequest request) throws Exception {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        Address address = addressMapper.toAddress(request);
        if (!addressRepository.findAllByCustomer(customer).isEmpty()) {
            // Đặt địa chỉ default -> non-default
            addressRepository.findAllByCustomer(customer)
                    .forEach(exists -> {
                        exists.setStatus(0);
                        addressRepository.save(exists);
                    });
        }
        address.setCustomer(customer);
        address.setStatus(1);
        return addressRepository.save(address);
    }

    @Override
    public List<AddressResponse> getAllAddressOfCustomer(Integer customerId) throws Exception{
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        return addressMapper
                .toAddressResponses(addressRepository.findAllByCustomerOrderByStatusDesc(customer));
    }

    @Override
    public AddressResponse getAddressById(Integer id) {
        return addressRepository.findById(id)
                .map(addressMapper::toAddressResponse)
                .orElse(null);
    }

    @Override
    public Address updateAddress(Integer id, AddressRequest request) throws Exception {
        Address oldAddress = addressRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Address not found"));
        Address newAddress = addressMapper.toAddress(request);
        newAddress.setId(oldAddress.getId());
        newAddress.setStatus(oldAddress.getStatus());
        newAddress.setCustomer(oldAddress.getCustomer());
        return addressRepository.save(newAddress);
    }

    @Override
    public void deleteAddress(Integer id) {
        try {
            Address address = addressRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Địa chỉ đã xóa hoặc không tồn tại"));
            addressRepository.delete(address);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateDefaultAddress(Integer id) {
        try {
            Address existsAddress = addressRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Address not found"));
            // Đặt địa chỉ default -> non-default
            addressRepository.findAllByCustomer(existsAddress.getCustomer())
                            .forEach(exists -> {
                                if (!exists.getId().equals(id)) {
                                    exists.setStatus(0);
                                    addressRepository.save(exists);
                                }
                            });

            existsAddress.setStatus(1);
            addressRepository.save(existsAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
