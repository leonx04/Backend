package com.example.backend.Library.repository.cart;

import com.example.backend.Library.model.entity.cart.CartDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
     List<CartDetail> findByCartId(int cartId);
     List<CartDetail> findByCart_User_Id(int userId);

}