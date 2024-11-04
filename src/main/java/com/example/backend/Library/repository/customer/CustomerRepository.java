package com.example.backend.Library.repository.customer;

import com.example.backend.Library.model.entity.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    Page<Customer> findAll(Pageable pageable);

    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    // Lấy ra Nhân viên có mã lớn nhất(1 nhân viên)
    Customer findFirstByOrderByCodeDesc();

    // tìm kiếm khách hàng theo các thuộc tính của khách hàng
    @Query("SELECT c FROM Customer c WHERE "
            + "(:fullName IS NULL OR LOWER(c.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND "
            + "(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND "
            + "(:phone IS NULL OR c.phone LIKE %:phone%) AND "
            + "(:gender IS NULL OR c.gender = :gender) AND "
            + "(:status IS NULL OR c.status = :status)"
            + "ORDER BY c.code ASC")
    Page<Customer> searchCustomers(
            @Param("fullName") String fullName,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("gender") Integer gender,
            @Param("status") Integer status,
            Pageable pageable);

}