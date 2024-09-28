package com.example.backend.Library.repository;

import com.example.backend.Library.model.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface Voucher_Repository extends JpaRepository<Voucher, Integer> {
    List<Voucher> findByStatus(Integer status);

    @Query("SELECT v FROM Voucher v WHERE v.startDate >= :startDate AND v.endDate <= :endDate")
    List<Voucher> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Voucher> findByDiscountValueBetween(BigDecimal minValue, BigDecimal maxValue);

    List<Voucher> findByVoucherType(String voucherType);

    @Query("SELECT v FROM Voucher v WHERE " +
            "(:code IS NULL OR v.code LIKE %:code%) AND " +
            "(:description IS NULL OR v.description LIKE %:description%) AND " +
            "(:minValue IS NULL OR v.discountValue >= :minValue) AND " +
            "(:maxValue IS NULL OR v.discountValue <= :maxValue) AND " +
            "(:status IS NULL OR v.status = :status) AND " +
            "(:voucherType IS NULL OR v.voucherType = :voucherType) AND " +
            "(:startDate IS NULL OR v.startDate >= :startDate) AND " +
            "(:endDate IS NULL OR v.endDate <= :endDate)")
    List<Voucher> searchVouchers(@Param("code") String code,
                                 @Param("description") String description,
                                 @Param("minValue") BigDecimal minValue,
                                 @Param("maxValue") BigDecimal maxValue,
                                 @Param("status") Integer status,
                                 @Param("voucherType") String voucherType,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);
}
