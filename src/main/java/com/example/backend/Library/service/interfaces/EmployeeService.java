package com.example.backend.Library.service.interfaces;

import com.example.backend.Library.model.dto.request.EmployeeRequest;
import com.example.backend.Library.model.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getAll();
    EmployeeResponse getById(Integer id);
    EmployeeResponse createEmployee(EmployeeRequest request);
    EmployeeResponse updateEmployee(Integer id, EmployeeRequest request);
    void deleteEmployee(Integer id);
}
