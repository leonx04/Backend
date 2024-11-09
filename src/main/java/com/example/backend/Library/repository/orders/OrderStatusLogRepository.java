package com.example.backend.Library.repository.orders;

import com.example.backend.Library.model.entity.orders.OrderStatusLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog,Integer> {
    Page<OrderStatusLog> findAll(Pageable pageable);

}
