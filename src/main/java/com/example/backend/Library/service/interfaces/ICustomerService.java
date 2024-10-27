package com.example.backend.Library.service.interfaces;

import com.example.backend.Library.model.dto.request.LoginRequest;
import com.example.backend.Library.model.dto.request.RegisterRequest;
import com.example.backend.Library.model.dto.request.customer.CustomerRequest;
import com.example.backend.Library.model.dto.response.CustomerResponse;
import com.example.backend.Library.model.entity.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ICustomerService {
    Customer registerCustomer(RegisterRequest request);
    Customer createCustomer(CustomerRequest request);

    Customer updateAdminCustomer(int id, CustomerRequest request) throws Exception;

    Page<CustomerResponse> getCustomers(Pageable pageable);
    Customer getCustomerById(int id) throws Exception;
    Optional<Customer> getCustomerByEmail(String email);
    Customer updateCustomer(int id, CustomerRequest request) throws Exception;
    void deleteCustomer(int id);

    void softDeleteCustomer(int id) throws Exception;

    Customer updateCustomerStatus(int id, int status) throws Exception;

    boolean existsByEmail(String email);
    void checkCreateImage(CustomerRequest request, MultipartFile avatar);
    void updateImage(int id, CustomerRequest request, MultipartFile avatar) throws IOException;

    boolean authenticateCustomer(LoginRequest loginRequest);

    // Method tạo dữ liệu giả cho khách hàng
    void createFakeCustomers(int count);

    // Method tìm kiếm khách hàng theo các thuộc tính của khách hàng
    Page<CustomerResponse> searchCustomers(
            String fullName, String email, String phone, Integer gender, Integer status, Pageable pageable);

}
