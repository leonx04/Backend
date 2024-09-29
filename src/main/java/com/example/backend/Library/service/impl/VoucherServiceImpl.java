package com.example.backend.Library.service.impl;

import com.example.backend.Library.model.dto.Voucher_Admin_DTO;
import com.example.backend.Library.model.entity.Voucher;
import com.example.backend.Library.model.mapper.VoucherMapper;
import com.example.backend.Library.repository.Voucher_Repository;
import com.example.backend.Library.service.interfaces.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementaion của VoucherService để cung cấp các dịch vụ liên quan đến voucher.
 */
@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private Voucher_Repository voucherRepository; // Inject repository để tương tác với cơ sở dữ liệu

    /**
     * Lấy tất cả các voucher và chuyển đổi chúng thành Voucher_Admin_DTO.
     *
     * @return danh sách Voucher_Admin_DTO
     */
    @Override
    public List<Voucher_Admin_DTO> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(VoucherMapper.INSTANCE::toDto) // Chuyển đổi từ Voucher sang Voucher_Admin_DTO
                .collect(Collectors.toList());
    }

    /**
     * Lấy các voucher theo trạng thái.
     *
     * @param status trạng thái của voucher
     * @return danh sách Voucher_Admin_DTO theo trạng thái
     */
    @Override
    public List<Voucher_Admin_DTO> getVouchersByStatus(Integer status) {
        return voucherRepository.findByStatus(status).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Lấy các voucher theo khoảng thời gian.
     *
     * @param startDate thời gian bắt đầu
     * @param endDate thời gian kết thúc
     * @return danh sách Voucher_Admin_DTO trong khoảng thời gian
     */
    @Override
    public List<Voucher_Admin_DTO> getVouchersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return voucherRepository.findByDateRange(startDate, endDate).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Lấy các voucher theo khoảng giá trị giảm giá.
     *
     * @param minValue giá trị giảm giá tối thiểu
     * @param maxValue giá trị giảm giá tối đa
     * @return danh sách Voucher_Admin_DTO trong khoảng giá trị
     */
    @Override
    public List<Voucher_Admin_DTO> getVouchersByValueRange(BigDecimal minValue, BigDecimal maxValue) {
        return voucherRepository.findByDiscountValueBetween(minValue, maxValue).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Lấy các voucher theo loại.
     *
     * @param voucherType loại voucher
     * @return danh sách Voucher_Admin_DTO theo loại
     */
    @Override
    public List<Voucher_Admin_DTO> getVouchersByType(String voucherType) {
        return voucherRepository.findByVoucherType(voucherType).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Tìm kiếm các voucher dựa trên nhiều tiêu chí.
     *
     * @param code mã voucher
     * @param description mô tả voucher
     * @param minValue giá trị giảm giá tối thiểu
     * @param maxValue giá trị giảm giá tối đa
     * @param status trạng thái voucher
     * @param voucherType loại voucher
     * @param startDate thời gian bắt đầu
     * @param endDate thời gian kết thúc
     * @return danh sách Voucher_Admin_DTO đáp ứng tiêu chí tìm kiếm
     */
    @Override
    public List<Voucher_Admin_DTO> searchVouchers(String code,
                                                  String description,
                                                  BigDecimal minValue,
                                                  BigDecimal maxValue,
                                                  Integer status,
                                                  String voucherType,
                                                  LocalDateTime startDate,
                                                  LocalDateTime endDate) {
        List<Voucher> vouchers = voucherRepository.searchVouchers(code, description, minValue, maxValue,
                status, voucherType, startDate, endDate);
        return vouchers.stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Tạo mới một voucher.
     *
     * @param voucherDto thông tin voucher cần tạo
     * @return Voucher_Admin_DTO của voucher đã tạo
     */
    @Override
    public Voucher_Admin_DTO createVoucher(Voucher_Admin_DTO voucherDto) {
        Voucher voucher = VoucherMapper.INSTANCE.toEntity(voucherDto); // Chuyển đổi từ DTO sang entity
        voucher.setCreatedDate(LocalDateTime.now()); // Thiết lập ngày tạo
        voucher.setUpdatedDate(LocalDateTime.now()); // Thiết lập ngày cập nhật
        voucher = voucherRepository.save(voucher); // Lưu voucher vào cơ sở dữ liệu
        return VoucherMapper.INSTANCE.toDto(voucher); // Chuyển đổi trở lại thành DTO
    }

    /**
     * Cập nhật thông tin của một voucher.
     *
     * @param id        id của voucher cần cập nhật
     * @param voucherDto thông tin voucher mới
     * @return Voucher_Admin_DTO của voucher đã cập nhật
     */
    @Override
    public Voucher_Admin_DTO updateVoucher(Integer id, Voucher_Admin_DTO voucherDto) {
        Voucher existingVoucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found")); // Kiểm tra xem voucher có tồn tại không

        Voucher updatedVoucher = VoucherMapper.INSTANCE.toEntity(voucherDto); // Chuyển đổi từ DTO sang entity
        updatedVoucher.setId(existingVoucher.getId()); // Giữ lại id cũ
        updatedVoucher.setCreatedDate(existingVoucher.getCreatedDate()); // Giữ lại ngày tạo cũ
        updatedVoucher.setUpdatedDate(LocalDateTime.now()); // Cập nhật ngày hiện tại

        updatedVoucher = voucherRepository.save(updatedVoucher); // Lưu voucher đã cập nhật vào cơ sở dữ liệu
        return VoucherMapper.INSTANCE.toDto(updatedVoucher); // Chuyển đổi trở lại thành DTO
    }

    /**
     * Xóa một voucher theo id.
     *
     * @param id id của voucher cần xóa
     */
    @Override
    public void deleteVoucher(Integer id) {
        voucherRepository.deleteById(id); // Xóa voucher theo id
    }

    /**
     * Lấy thông tin chi tiết của một voucher theo id.
     *
     * @param id id của voucher cần lấy
     * @return Voucher_Admin_DTO của voucher
     */
    @Override
    public Voucher_Admin_DTO getVoucherById(Integer id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found")); // Kiểm tra xem voucher có tồn tại không
        return VoucherMapper.INSTANCE.toDto(voucher); // Chuyển đổi từ entity sang DTO
    }
}
