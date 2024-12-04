/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Admin.controller.employee;

import com.example.backend.Library.model.dto.request.employee.EmployeeRequest;
import com.example.backend.Library.service.impl.employee.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.example.backend.Library.validation.pts_validator.Validator.errorField;

@RestController
@RequestMapping("${api.prefix}/admin/personal")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:8080"})
public class PersonalController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Lấy thông tin cá nhân
     * */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonal (@PathVariable("id") Integer id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cập nhật thông tin cá nhân
     * */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updatePersonal (
            @PathVariable("id") Integer id,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            @Valid @ModelAttribute EmployeeRequest request,
            BindingResult result) {
        Map response = new HashMap<>();
        try {
            errorField(response, result);
            if (response.get("status").equals(400)) {
                return ResponseEntity.ok(response);
            }
            employeeService.updateEmployee(id, request, avatar);
            response.put("message", "Cập nhật thông tin cá nhân thành công");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }
}
