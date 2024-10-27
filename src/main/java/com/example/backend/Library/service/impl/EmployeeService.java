package com.example.backend.Library.service.impl;

import com.example.backend.Library.model.entity.Employee;
import com.example.backend.Library.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepository; // Tiêm dependency của repository

    private final String IMAGE_PATH = "H:\\FPOLY\\DATN\\qlnhanvien\\Backend\\src\\main\\java\\com\\example\\backend\\Library\\service\\impl\\images";

    // Lấy tất cả nhân viên
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Lấy thông tin nhân viên theo ID
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    // Tạo nhân viên mới
    public Employee createEmployee(Employee employee, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            employee.setImageURL(imageUrl);
        }
        return employeeRepository.save(employee);
    }

    // Cập nhật thông tin nhân viên
    public Employee updateEmployee(Integer id, Employee employeeDetails, MultipartFile imageFile) throws IOException {
        Employee employee = employeeRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Nhân viên không tồn tại"));

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            employee.setImageURL(imageUrl);
        }
        // Cập nhật thông tin
        employee.setUsername(employeeDetails.getUsername());
        employee.setPassword(employeeDetails.getPassword()); // Mã hóa mật khẩu nếu cần
        employee.setFullname(employeeDetails.getFullname());
        employee.setGender(employeeDetails.getGender());
        employee.setBirthdate(employeeDetails.getBirthdate());
        employee.setPhone(employeeDetails.getPhone());
        employee.setEmail(employeeDetails.getEmail());
        employee.setAddress(employeeDetails.getAddress());
        employee.setImageURL(employeeDetails.getImageURL());
        employee.setRoleid(employeeDetails.getRoleid());
        employee.setStatus(employeeDetails.getStatus());
        employee.setNote(employeeDetails.getNote());
        employee.setUpdatedat(new Date()); // Cập nhật ngày
        return employeeRepository.save(employee);
    }

    // cập nhật trạng thái
    public Employee setEmployeeStatus(Integer id, Integer status) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setStatus(status); // 1: Hoạt động, 0: Không hoạt động
        return employeeRepository.save(employee);
    }

    // Tìm kiếm nhân viên theo tên
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByFullnameContainingIgnoreCase(name);
    }

    // Tìm kiếm nhân viên theo code
    public Optional<Employee> searchEmployeesByCode(String code) {
        return employeeRepository.findByCode(code);
    }

    //save image
    private String saveImage(MultipartFile imageFile) throws IOException {
        // Tạo đường dẫn cho file
        String imagePath = IMAGE_PATH + imageFile.getOriginalFilename();
        // Lưu file vào thư mục
        File file = new File(imagePath);
        imageFile.transferTo(file); // Lưu file
        return "images/" + imageFile.getOriginalFilename(); // Trả về đường dẫn hình ảnh tương đối
    }
}