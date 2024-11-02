package com.example.backend.Library.repository;

import com.example.backend.Library.model.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserId(int customerId);
}