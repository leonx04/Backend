package com.example.backend.Library.repository.orders;

import com.example.backend.Library.model.entity.orders.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCodeContaining(String code);
    Optional<Order> findByCode(String code);

    Page<Order> findAll(Pageable pageable);
    Page<Order> findByOrderStatusNotIn(List<Integer> statuses, Pageable pageable);

    Page<Order> findByOrderStatus(Integer orderStatus, Pageable pageable);

    List<Order> findByCustomerId(int customerId);

    @Query("SELECT o FROM Order o " +
            "JOIN o.customer c " +
            "JOIN o.voucher v " +
            "WHERE (:keyword IS NULL OR o.code LIKE %:keyword% " +
            "OR c.fullName LIKE %:keyword% )")
    Page<Order> searchOrders(@Param("keyword") String keyword,Pageable pageable);

}
