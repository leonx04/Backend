package com.example.backend.Library.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(unique = true) // Đảm bảo mã nhân viên là duy nhất
    private String code;
    private String username;
    private String password; // Mật khẩu của nhân viên
    private String fullname; // Họ và tên nhân viên
    private Integer gender; // Giới tính (0: Nam, 1: Nữ, 2: Khác)
    private Date birthdate; // Ngày sinh
    private String phone; // Số điện thoại
    private String email; // Địa chỉ email
    private String address; // Địa chỉ
    private String imageURL; // Đường dẫn đến hình ảnh
    private Integer roleid = 2; // Vai trò mặc định (2: Nhân viên)
    private Integer status = 1; // 1: Hoạt động, 0: Không hoạt động
    private String note; // Ghi chú
    private Date createdat = new Date(); // Ngày tạo
    private Date updatedat = new Date(); // Ngày cập nhật
}
