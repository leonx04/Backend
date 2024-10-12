package com.example.backend.Library.service.impl;

import com.example.backend.Library.exception.ExceptionHandles;
import com.example.backend.Library.model.dto.request.voucher.Voucher_Admin_DTO;
import com.example.backend.Library.model.entity.voucher.Voucher;
import com.example.backend.Library.model.mapper.VoucherMapper;
import com.example.backend.Library.repository.Voucher_Repository;
import com.example.backend.Library.service.interfaces.VoucherService;
import com.example.backend.Library.validation.VoucherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
                .collect(Collectors.toList()); // Trả về danh sách DTO
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
                .map(VoucherMapper.INSTANCE::toDto) // Chuyển đổi từ Voucher sang Voucher_Admin_DTO
                .collect(Collectors.toList()); // Trả về danh sách DTO
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
                .map(VoucherMapper.INSTANCE::toDto) // Chuyển đổi từ Voucher sang Voucher_Admin_DTO
                .collect(Collectors.toList()); // Trả về danh sách DTO
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
                .map(VoucherMapper.INSTANCE::toDto) // Chuyển đổi từ Voucher sang Voucher_Admin_DTO
                .collect(Collectors.toList()); // Trả về danh sách DTO
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
                .map(VoucherMapper.INSTANCE::toDto) // Chuyển đổi từ Voucher sang Voucher_Admin_DTO
                .collect(Collectors.toList()); // Trả về danh sách DTO
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
                status, voucherType, startDate, endDate); // Tìm kiếm voucher theo các tiêu chí
        return vouchers.stream()
                .map(VoucherMapper.INSTANCE::toDto) // Chuyển đổi từ Voucher sang Voucher_Admin_DTO
                .collect(Collectors.toList()); // Trả về danh sách DTO
    }

    /**
     * Tạo mới một voucher.
     *
     * @param voucherDto thông tin voucher cần tạo
     * @return Voucher_Admin_DTO của voucher đã tạo
     */
    @Override
    public Voucher_Admin_DTO createVoucher(Voucher_Admin_DTO voucherDto) {
        System.out.println("Debug - VoucherServiceImpl - createVoucher - startDate: " + voucherDto.getStartDate());
        System.out.println("Debug - VoucherServiceImpl - createVoucher - endDate: " + voucherDto.getEndDate());

        List<String> validationErrors = VoucherValidator.validateVoucher(voucherDto);
        if (!validationErrors.isEmpty()) {
            throw new ExceptionHandles.ValidationException(validationErrors);
        }

        // Adjust time zone
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime startDate = voucherDto.getStartDate().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId).toLocalDateTime();
        LocalDateTime endDate = voucherDto.getEndDate().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId).toLocalDateTime();

        Voucher voucher = VoucherMapper.INSTANCE.toEntity(voucherDto);
        voucher.setStartDate(startDate);
        voucher.setEndDate(endDate);

        LocalDateTime now = LocalDateTime.now(zoneId);

        if (voucher.getStartDate().isAfter(now)) {
            voucher.setStatus(3);
        } else if (voucher.getStartDate().isBefore(now) || voucher.getStartDate().isEqual(now)) {
            if (voucher.getEndDate().isAfter(now) || voucher.getEndDate().isEqual(now)) {
                voucher.setStatus(1);
            } else {
                voucher.setStatus(4);
            }
        }

        voucher.setCreatedAt(now);
        voucher.setUpdatedAt(now);

        voucher = voucherRepository.save(voucher);
        return VoucherMapper.INSTANCE.toDto(voucher);
    }

    @Override
    public Voucher_Admin_DTO updateVoucher(Integer id, Voucher_Admin_DTO voucherDto) {
        System.out.println("Debug - VoucherServiceImpl - updateVoucher - startDate: " + voucherDto.getStartDate());
        System.out.println("Debug - VoucherServiceImpl - updateVoucher - endDate: " + voucherDto.getEndDate());

        List<String> validationErrors = VoucherValidator.validateVoucher(voucherDto);
        if (!validationErrors.isEmpty()) {
            throw new ExceptionHandles.ValidationException(validationErrors);
        }

        Voucher existingVoucher = voucherRepository.findById(id).orElseThrow(() -> new ExceptionHandles.ResourceNotFoundException("Voucher not found with id: " + id));

        // Adjust time zone
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime startDate = voucherDto.getStartDate().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId).toLocalDateTime();
        LocalDateTime endDate = voucherDto.getEndDate().atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId).toLocalDateTime();

        Voucher updatedVoucher = VoucherMapper.INSTANCE.toEntity(voucherDto);
        updatedVoucher.setId(existingVoucher.getId());
        updatedVoucher.setCreatedAt(existingVoucher.getCreatedAt());
        updatedVoucher.setStartDate(startDate);
        updatedVoucher.setEndDate(endDate);

        LocalDateTime now = LocalDateTime.now(zoneId);
        updatedVoucher.setUpdatedAt(now);

        // Update status based on current time and voucher dates
        if (updatedVoucher.getEndDate().isBefore(now)) {
            updatedVoucher.setStatus(4); // Expired
        } else if (updatedVoucher.getStartDate().isAfter(now)) {
            updatedVoucher.setStatus(3); // Future
        } else {
            updatedVoucher.setStatus(1); // Active
        }

        updatedVoucher = voucherRepository.save(updatedVoucher);
        return VoucherMapper.INSTANCE.toDto(updatedVoucher);
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
        return VoucherMapper.INSTANCE.toDto(voucher); // Chuyển đổi từ entity sang DTO và trả về
    }
    @Override
    public Voucher_Admin_DTO getVoucherByCode(String code) {
        Voucher voucher = voucherRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Voucher not found")); // Kiểm tra xem voucher có tồn tại không
        return VoucherMapper.INSTANCE.toDto(voucher); // Chuyển đổi từ entity sang DTO và trả về
    }
}
