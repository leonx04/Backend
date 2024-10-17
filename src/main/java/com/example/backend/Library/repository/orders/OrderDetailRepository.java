package com.example.backend.Library.repository.orders;

import com.example.backend.Library.model.entity.orders.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
}
