package com.example.backend.Library.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Brand")
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Mã không được để trống")
    @Size(max = 255, message = "Mã có tối đa 255 ký tự")
    private String code;

    @Column(nullable = false)
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 255, message = "Tên có tối đa 255 ký tự")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Trạng thái là bắt buộc")
    private Integer status = 1;


}
