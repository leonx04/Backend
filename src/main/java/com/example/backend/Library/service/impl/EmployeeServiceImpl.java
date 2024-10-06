package com.example.backend.Library.service.impl;

import com.example.backend.Library.exception.AppException;
import com.example.backend.Library.exception.ErrorCode;
import com.example.backend.Library.model.dto.request.EmployeeRequest;
import com.example.backend.Library.model.dto.response.EmployeeResponse;
import com.example.backend.Library.model.entity.Employee;
import com.example.backend.Library.model.mapper.EmployeeMapper;
import com.example.backend.Library.repository.EmployeeRepository;
import com.example.backend.Library.service.interfaces.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeMapper mapper;
//    private PasswordEncoder encoder;

    public EmployeeResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email =context.getAuthentication().getName();

        Employee employee = employeeRepository
                .findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return mapper.toEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAll() {
        log.info("In method get Users");
        return employeeRepository.findAll()
                .stream()
                .map(mapper :: toEmployeeResponse)
                .toList();
    }

    @Override
    public EmployeeResponse getById(Integer id) {
        return mapper.toEmployeeResponse(
                employeeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee epl = mapper.toEmployee(request);
        epl.setPassword(request.getPassword());

        try {
            epl = employeeRepository.save(epl);
        } catch (Exception e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return mapper.toEmployeeResponse(epl);
    }

    @Override
    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        mapper.updateEmployee(employee, request);
        employee.setPassword(request.getPassword());

        return mapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}
