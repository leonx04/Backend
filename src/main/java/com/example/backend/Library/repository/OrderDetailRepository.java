package com.example.backend.Library.repository;

import com.example.backend.Library.model.entity.orders.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(Integer orderId);
}