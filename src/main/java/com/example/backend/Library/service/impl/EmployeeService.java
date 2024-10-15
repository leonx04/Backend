package com.example.backend.Library.service.impl;

import com.example.backend.Library.model.entity.employee.Employee;
import com.example.backend.Library.repository.employee.EmployeeRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepository;

    // Lấy tất cả nhân viên
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Tạo mới nhân viên
    public Employee createEmployee(Employee employee) {
        employee.setCreatedat(new Date()); // Thiết lập ngày tạo
        return employeeRepository.save(employee);
    }

    // Lấy thông tin một nhân viên theo ID
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    // Cập nhật thông tin nhân viên
    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        Employee employee = getEmployeeById(id);
        employee.setUsername(updatedEmployee.getUsername());
        employee.setFullname(updatedEmployee.getFullname());
        employee.setPassword(updatedEmployee.getPassword());
        employee.setGender(updatedEmployee.getGender());
        employee.setBirthdate(updatedEmployee.getBirthdate());
        employee.setPhone(updatedEmployee.getPhone());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setAddress(updatedEmployee.getAddress());
        employee.setUrl(updatedEmployee.getUrl());
        employee.setRoleid(updatedEmployee.getRoleid());
        employee.setStatus(updatedEmployee.getStatus());
        employee.setNote(updatedEmployee.getNote());
        employee.setUpdatedat(new Date()); // Cập nhật ngày sửa
        return employeeRepository.save(employee);
    }

    // Xóa một nhân viên
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    //search
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByFullnameContainingIgnoreCase(name);
    }

    public Optional<Employee> searchEmployeeByCode(String code) {
        return employeeRepository.findByCode(code);
    }
}