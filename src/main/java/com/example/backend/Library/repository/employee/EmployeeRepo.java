package com.example.backend.Library.repository.employee;

import com.example.backend.Library.model.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    // Phuong
    Employee findByUserName(String username);
    List<Employee> findByFullNameContainingIgnoreCase(String fullname);
    Optional<Employee> findByCode(String code);

    // Son
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByPhone(String phone);

}