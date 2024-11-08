package com.example.backend.Library.repository.customer;

import com.example.backend.Library.model.entity.customer.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findAllByCustomer(Customer customer);
}