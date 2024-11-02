package com.example.backend.Library.repository.orders;



import com.example.backend.Library.model.dto.Request.FindByOrderStatusAndOrderType;
import com.example.backend.Library.model.entity.orders.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCodeContaining(String code);
    Optional<Order> findByCode(String code);

    Page<Order> findAll(Pageable pageable);
    Page<Order> findByOrderStatusNotIn(List<Integer> statuses, Pageable pageable);

    Page<Order> findByOrderStatusAndOrderType(Integer orderStatus, String orderType, Pageable pageable);
}
