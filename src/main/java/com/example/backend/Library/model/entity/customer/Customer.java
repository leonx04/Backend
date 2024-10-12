package com.example.backend.Library.model.entity.customer;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;
    private String userName;
    private String passWord;
    private String fullName;
    private String gender;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String imageUrl;
    @Column(name = "CreatedAt")
    private LocalDate createAt;
    @Column(name = "UpdatedAt")
    private LocalDate updateAt;


}
