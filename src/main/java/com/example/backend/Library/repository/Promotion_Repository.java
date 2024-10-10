package com.example.backend.Library.repository;

import com.example.backend.Library.model.entity.promotion.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface Promotion_Repository  extends JpaRepository<Promotion, Integer> {

    @Query("SELECT p FROM Promotion p WHERE " +
            "(:startDate IS NULL OR p.startDate >= :startDate) AND " +
            "(:endDate IS NULL OR p.endDate <= :endDate) AND " +
            "(:minDiscount IS NULL OR p.discountPercentage >= :minDiscount) AND " +
            "(:maxDiscount IS NULL OR p.discountPercentage <= :maxDiscount) AND " +
            "(:code IS NULL OR p.code LIKE %:code%) AND " +
            "(:name IS NULL OR p.name LIKE %:name%)")
    List<Promotion> searchPromotions(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("minDiscount") BigDecimal minDiscount,
            @Param("maxDiscount") BigDecimal maxDiscount,
            @Param("code") String code,
            @Param("name") String name
    );


}
