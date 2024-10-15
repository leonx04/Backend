package com.example.backend.Library.repository.employee;

import com.example.backend.Library.model.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    // Phuong Bao
    Employee findByUsername(String username);
    List<Employee> findByFullnameContainingIgnoreCase(String fullname);
    Optional<Employee> findByCode(String code);

    // Son pc
    Optional<Employee> findByEmail(String email);
}