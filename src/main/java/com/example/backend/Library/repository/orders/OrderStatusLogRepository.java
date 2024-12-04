package com.example.backend.Library.repository.orders;

import com.example.backend.Library.model.entity.orders.OrderStatusLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog,Integer> {
    Page<OrderStatusLog> findAll(Pageable pageable);
    @Query("select o from OrderStatusLog o where CAST(o.updatedAt AS DATE) between :startDate and :endDate")
    Page<OrderStatusLog> findAllByTimeRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

}
