package com.example.backend.Admin.controller;
import com.example.backend.Library.model.entity.Employee;
import com.example.backend.Library.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/admin/employees")

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    // Lấy danh sách tất cả nhân viên
    @GetMapping
    public List<Employee> listEmployees() {
        return employeeService.getAllEmployees();
    }

    // Lấy thông tin nhân viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Tạo mới nhân viên
    @PostMapping
    public Employee createEmployee(@RequestParam("employee") Employee employee,
                                   @RequestParam("image") MultipartFile imageFile) throws IOException {
        return employeeService.createEmployee(employee, imageFile);
    }

    // Cập nhật thông tin nhân viên
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id,
                                                   @RequestParam("employee") Employee employeeDetails,
                                                   @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDetails, imageFile));
    }


    //search
    @GetMapping("/search")
    public String searchEmployees(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "code", required = false) String code,
                                  Model model) {
        System.out.println("Search parameters - Name: " + name + ", Code: " + code);  // Log giá trị tham số

        if (name != null && !name.isEmpty()) {
            List<Employee> employees = employeeService.searchEmployeesByName(name);
            System.out.println("Employees found by name: " + employees);  // Log kết quả tìm kiếm theo tên
            model.addAttribute("employees", employees);
        } else if (code != null && !code.isEmpty()) {
            Optional<Employee> employee = employeeService.searchEmployeesByCode(code);
            if (employee.isPresent()) {
                System.out.println("Employee found by code: " + employee);  // Log kết quả tìm kiếm theo mã
                model.addAttribute("employees", List.of(employee.get()));
            } else {
                model.addAttribute("employees", List.of()); // Không tìm thấy nhân viên nào
            }
        } else {
            model.addAttribute("employees", List.of()); // Không có điều kiện tìm kiếm
        }

        return "employeeList";
    }
}
