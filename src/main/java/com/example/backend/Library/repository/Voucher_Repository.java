package com.example.backend.Library.repository;

import com.example.backend.Library.model.entity.voucher.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository để quản lý các đối tượng Voucher.
 * Sử dụng JpaRepository để cung cấp các phương thức CRUD cơ bản.
 */
@Repository
public interface Voucher_Repository extends JpaRepository<Voucher, Integer> {

    /**
     * Tìm kiếm các voucher theo trạng thái.
     *
     * @param status trạng thái của voucher
     * @return danh sách voucher theo trạng thái
     */
    List<Voucher> findByStatus(Integer status);

    /**
     * Tìm kiếm các voucher trong khoảng thời gian cho trước.
     *
     * @param startDate thời gian bắt đầu
     * @param endDate thời gian kết thúc
     * @return danh sách voucher trong khoảng thời gian
     */
    @Query("SELECT v FROM Voucher v WHERE v.startDate >= :startDate AND v.endDate <= :endDate")
    List<Voucher> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Tìm kiếm các voucher có giá trị giảm giá nằm trong khoảng giá trị cho trước.
     *
     * @param minValue giá trị giảm giá tối thiểu
     * @param maxValue giá trị giảm giá tối đa
     * @return danh sách voucher trong khoảng giá trị giảm giá
     */
    List<Voucher> findByDiscountValueBetween(BigDecimal minValue, BigDecimal maxValue);

    /**
     * Tìm kiếm các voucher theo loại voucher.
     *
     * @param voucherType loại voucher
     * @return danh sách voucher theo loại
     */
    List<Voucher> findByVoucherType(String voucherType);

    /**
     * Tìm kiếm voucher dựa trên nhiều tiêu chí khác nhau.
     *
     * @param code mã voucher
     * @param description mô tả voucher
     * @param minValue giá trị giảm giá tối thiểu
     * @param maxValue giá trị giảm giá tối đa
     * @param status trạng thái voucher
     * @param voucherType loại voucher
     * @param startDate thời gian bắt đầu
     * @param endDate thời gian kết thúc
     * @return danh sách voucher tìm kiếm được
     */
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


    Optional<Voucher> findByCode(String code);
}
