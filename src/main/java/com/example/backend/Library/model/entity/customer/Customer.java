package com.example.backend.Library.model.entity.customer;

import com.example.backend.Library.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Code", nullable = false, length = 255)
    private String code = "CUS";

    @Column(name = "Username", nullable = false, length = 100)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Fullname")
    private String fullName;

    private Integer gender;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Birthdate")
    private LocalDate birthDate;

    @Column(name = "Imageurl")
    private String imageURL;

    @ColumnDefault("1")
    @Column(name = "Status")
    private Integer status;

//    @JsonIgnore
//    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
//    private List<Address> addresses;

//    @JsonIgnore
//    @OneToMany(mappedBy = "customer")
//    private List<Order> orders;

}