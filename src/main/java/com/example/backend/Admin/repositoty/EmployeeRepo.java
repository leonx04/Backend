package com.example.backend.Admin.repositoty;

import com.example.backend.Admin.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Employee findByUsername(String username);
    List<Employee> findByFullnameContainingIgnoreCase(String fullname);
    Optional<Employee> findByCode(String code);
}
