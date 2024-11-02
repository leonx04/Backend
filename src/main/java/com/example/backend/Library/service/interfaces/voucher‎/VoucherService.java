package com.example.backend.Library.service.interfaces.voucher;

import com.example.backend.Library.model.dto.request.voucher.Voucher_Admin_DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface để định nghĩa các phương thức liên quan đến dịch vụ quản lý voucher.
 */
public interface VoucherService {

    /**
     * Lấy tất cả các voucher.
     *
     * @return danh sách Voucher_Admin_DTO chứa thông tin của tất cả các voucher
     */
    List<Voucher_Admin_DTO> getAllVouchers();

    /**
     * Lấy các voucher theo trạng thái.
     *
     * @param status trạng thái của voucher (ví dụ: đã sử dụng, chưa sử dụng)
     * @return danh sách Voucher_Admin_DTO chứa thông tin của các voucher theo trạng thái
     */
    List<Voucher_Admin_DTO> getVouchersByStatus(Integer status);

    /**
     * Lấy các voucher trong khoảng thời gian nhất định.
     *
     * @param startDate thời gian bắt đầu của khoảng thời gian
     * @param endDate thời gian kết thúc của khoảng thời gian
     * @return danh sách Voucher_Admin_DTO chứa thông tin của các voucher trong khoảng thời gian
     */
    List<Voucher_Admin_DTO> getVouchersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Lấy các voucher theo khoảng giá trị giảm giá.
     *
     * @param minValue giá trị giảm giá tối thiểu
     * @param maxValue giá trị giảm giá tối đa
     * @return danh sách Voucher_Admin_DTO chứa thông tin của các voucher trong khoảng giá trị
     */
    List<Voucher_Admin_DTO> getVouchersByValueRange(BigDecimal minValue, BigDecimal maxValue);

    /**
     * Lấy các voucher theo loại.
     *
     * @param voucherType loại voucher (ví dụ: giảm giá theo phần trăm, giảm giá theo số tiền)
     * @return danh sách Voucher_Admin_DTO chứa thông tin của các voucher theo loại
     */
    List<Voucher_Admin_DTO> getVouchersByType(String voucherType);

    /**
     * Lấy tất cả các voucher với phân trang.
     *
     * @param pageable thông tin phân trang
     * @return Page<Voucher_Admin_DTO> chứa thông tin của tất cả các voucher với phân trang
     */
    Page<Voucher_Admin_DTO> getAllVouchersPageable(Pageable pageable);

    /**
     * Tìm kiếm voucher dựa trên nhiều tiêu chí với phân trang.
     *
     * @param code mã voucher
     * @param description mô tả voucher
     * @param minValue giá trị giảm giá tối thiểu
     * @param maxValue giá trị giảm giá tối đa
     * @param status trạng thái voucher
     * @param voucherType loại voucher
     * @param startDate thời gian bắt đầu
     * @param endDate thời gian kết thúc
     * @param pageable thông tin phân trang
     * @return Page<Voucher_Admin_DTO> chứa thông tin của các voucher tìm kiếm được với phân trang
     */
    Page<Voucher_Admin_DTO> searchVouchersPageable(String code,
                                                   String description,
                                                   BigDecimal minValue,
                                                   BigDecimal maxValue,
                                                   Integer status,
                                                   String voucherType,
                                                   LocalDateTime startDate,
                                                   LocalDateTime endDate,
                                                   Pageable pageable);

    /**
     * Tạo mới một voucher.
     *
     * @param voucherDto thông tin voucher cần tạo
     * @return Voucher_Admin_DTO chứa thông tin của voucher đã được tạo
     */
    Voucher_Admin_DTO createVoucher(Voucher_Admin_DTO voucherDto);

    /**
     * Cập nhật thông tin của một voucher.
     *
     * @param id id của voucher cần cập nhật
     * @param voucherDto thông tin mới của voucher
     * @return Voucher_Admin_DTO chứa thông tin của voucher đã cập nhật
     */
    Voucher_Admin_DTO updateVoucher(Integer id, Voucher_Admin_DTO voucherDto);

    /**
     * Xóa một voucher theo id.
     *
     * @param id id của voucher cần xóa
     */
    void deleteVoucher(Integer id);

    /**
     * Lấy thông tin chi tiết của một voucher theo id.
     *
     * @param id id của voucher cần lấy thông tin
     * @return Voucher_Admin_DTO chứa thông tin của voucher
     */
    Voucher_Admin_DTO getVoucherById(Integer id);

    Voucher_Admin_DTO getVoucherByCode(String code);
}