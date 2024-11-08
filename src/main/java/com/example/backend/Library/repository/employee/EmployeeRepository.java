package com.example.backend.Library.repository.employee;

import com.example.backend.Library.model.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
