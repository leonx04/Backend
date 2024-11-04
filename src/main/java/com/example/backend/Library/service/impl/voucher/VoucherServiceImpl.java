package com.example.backend.Library.service.impl.voucher;

import com.example.backend.Library.exception.ExceptionHandles;
import com.example.backend.Library.model.dto.request.voucher.Voucher_Admin_DTO;
import com.example.backend.Library.model.entity.voucher.Voucher;
import com.example.backend.Library.model.mapper.voucher.VoucherMapper;
import com.example.backend.Library.repository.voucher.Voucher_Repository;
import com.example.backend.Library.service.interfaces.voucher.VoucherService;
import com.example.backend.Library.validation.VoucherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cài đặt VoucherService để cung cấp các dịch vụ liên quan đến voucher.
 */
@Service
public class VoucherServiceImpl implements VoucherService {

    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    @Autowired
    private Voucher_Repository voucherRepository;

    /**
     * Lấy tất cả các voucher và chuyển đổi chúng thành Voucher_Admin_DTO.
     *
     * @return Danh sách Voucher_Admin_DTO
     */
    @Override
    public List<Voucher_Admin_DTO> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Lấy các voucher theo trạng thái.
     *
     * @param status Trạng thái của voucher
     * @return Danh sách Voucher_Admin_DTO theo trạng thái
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
     * @param startDate Thời gian bắt đầu
     * @param endDate Thời gian kết thúc
     * @return Danh sách Voucher_Admin_DTO trong khoảng thời gian
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
     * @param minValue Giá trị giảm giá tối thiểu
     * @param maxValue Giá trị giảm giá tối đa
     * @return Danh sách Voucher_Admin_DTO trong khoảng giá trị
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
     * @param voucherType Loại voucher
     * @return Danh sách Voucher_Admin_DTO theo loại
     */
    @Override
    public List<Voucher_Admin_DTO> getVouchersByType(String voucherType) {
        return voucherRepository.findByVoucherType(voucherType).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Lấy tất cả các voucher với phân trang.
     *
     * @param pageable Thông tin phân trang
     * @return Page chứa Voucher_Admin_DTO
     */
    @Override
    public Page<Voucher_Admin_DTO> getAllVouchersPageable(Pageable pageable) {
        return voucherRepository.findAll(pageable).map(VoucherMapper.INSTANCE::toDto);
    }

    /**
     * Tìm kiếm voucher với nhiều tiêu chí và phân trang.
     *
     * @param code Mã voucher
     * @param description Mô tả voucher
     * @param minValue Giá trị tối thiểu
     * @param maxValue Giá trị tối đa
     * @param status Trạng thái
     * @param voucherType Loại voucher
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @param pageable Thông tin phân trang
     * @return Page chứa Voucher_Admin_DTO thỏa mãn điều kiện tìm kiếm
     */
    @Override
    public Page<Voucher_Admin_DTO> searchVouchersPageable(String code,
                                                          String description,
                                                          BigDecimal minValue,
                                                          BigDecimal maxValue,
                                                          Integer status,
                                                          String voucherType,
                                                          LocalDateTime startDate,
                                                          LocalDateTime endDate,
                                                          Pageable pageable) {
        return voucherRepository.searchVouchersPageable(code, description, minValue, maxValue,
                        status, voucherType, startDate, endDate, pageable)
                .map(VoucherMapper.INSTANCE::toDto);
    }

    /**
     * Tạo mới một voucher.
     *
     * @param voucherDto Thông tin voucher cần tạo
     * @return Voucher_Admin_DTO của voucher đã tạo
     */
    @Override
    public Voucher_Admin_DTO createVoucher(Voucher_Admin_DTO voucherDto) {
        // Xác thực thông tin voucher
        validateVoucher(voucherDto);

        // Chuyển đổi từ DTO sang entity và thiết lập các thuộc tính
        Voucher voucher = prepareVoucherForCreation(voucherDto);

        // Lưu voucher vào cơ sở dữ liệu
        Voucher savedVoucher = voucherRepository.save(voucher);
        return VoucherMapper.INSTANCE.toDto(savedVoucher);
    }

    /**
     * Cập nhật thông tin của một voucher.
     *
     * @param id ID của voucher cần cập nhật
     * @param voucherDto Thông tin voucher cần cập nhật
     * @return Voucher_Admin_DTO của voucher đã cập nhật
     */
    @Override
    public Voucher_Admin_DTO updateVoucher(Integer id, Voucher_Admin_DTO voucherDto) {
//        // Xác thực thông tin voucher
//        validateVoucher(voucherDto);

        // Tìm voucher hiện có theo id
        Voucher existingVoucher = findVoucherById(id);

        // Cập nhật thông tin voucher
        Voucher updatedVoucher = updateVoucherProperties(existingVoucher, voucherDto);

        // Lưu voucher đã cập nhật vào cơ sở dữ liệu
        Voucher savedVoucher = voucherRepository.save(updatedVoucher);
        return VoucherMapper.INSTANCE.toDto(savedVoucher);
    }

    /**
     * Xóa một voucher theo id.
     *
     * @param id ID của voucher cần xóa
     */
    @Override
    public void deleteVoucher(Integer id) {
        voucherRepository.deleteById(id);
    }

    /**
     * Lấy thông tin chi tiết của một voucher theo id.
     *
     * @param id ID của voucher cần lấy
     * @return Voucher_Admin_DTO của voucher
     */
    @Override
    public Voucher_Admin_DTO getVoucherById(Integer id) {
        Voucher voucher = findVoucherById(id);
        return VoucherMapper.INSTANCE.toDto(voucher);
    }

    /**
     * Lấy thông tin voucher theo mã.
     *
     * @param code Mã voucher cần lấy
     * @return Voucher_Admin_DTO của voucher
     */
    @Override
    public Voucher_Admin_DTO getVoucherByCode(String code) {
        Voucher voucher = voucherRepository.findByCode(code)
                .orElseThrow(() -> new ExceptionHandles.ResourceNotFoundException("Không tìm thấy voucher với mã: " + code));
        return VoucherMapper.INSTANCE.toDto(voucher);
    }

    // Các phương thức hỗ trợ

    /**
     * Xác thực thông tin voucher.
     *
     * @param voucherDto Thông tin voucher cần xác thực
     */
    private void validateVoucher(Voucher_Admin_DTO voucherDto) {
        List<String> validationErrors = VoucherValidator.validateVoucher(voucherDto);
        if (!validationErrors.isEmpty()) {
            throw new ExceptionHandles.ValidationException(validationErrors);
        }
    }

    /**
     * Chuẩn bị voucher để tạo mới.
     *
     * @param voucherDto Thông tin voucher từ DTO
     * @return Voucher đã được chuẩn bị
     */
    private Voucher prepareVoucherForCreation(Voucher_Admin_DTO voucherDto) {
        Voucher voucher = VoucherMapper.INSTANCE.toEntity(voucherDto);
        LocalDateTime now = LocalDateTime.now(VIETNAM_ZONE);

        voucher.setStartDate(convertToVietnamTime(voucherDto.getStartDate()));
        voucher.setEndDate(convertToVietnamTime(voucherDto.getEndDate()));
        voucher.setCreatedAt(now);
        voucher.setUpdatedAt(now);
        voucher.setStatus(determineVoucherStatus(voucher.getStartDate(), voucher.getEndDate(), now));

        return voucher;
    }

    /**
     * Tìm voucher theo ID.
     *
     * @param id ID của voucher cần tìm
     * @return Voucher tìm thấy
     * @throws ExceptionHandles.ResourceNotFoundException nếu không tìm thấy voucher
     */
    private Voucher findVoucherById(Integer id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandles.ResourceNotFoundException("Không tìm thấy voucher với id: " + id));
    }

    /**
     * Cập nhật thông tin voucher.
     *
     * @param existingVoucher Voucher hiện có
     * @param voucherDto Thông tin voucher mới
     * @return Voucher đã được cập nhật
     */
    private Voucher updateVoucherProperties(Voucher existingVoucher, Voucher_Admin_DTO voucherDto) {
        Voucher updatedVoucher = VoucherMapper.INSTANCE.toEntity(voucherDto);
        LocalDateTime now = LocalDateTime.now(VIETNAM_ZONE);

        updatedVoucher.setId(existingVoucher.getId());
        updatedVoucher.setCreatedAt(existingVoucher.getCreatedAt());
        updatedVoucher.setStartDate(convertToVietnamTime(voucherDto.getStartDate()));
        updatedVoucher.setEndDate(convertToVietnamTime(voucherDto.getEndDate()));
        updatedVoucher.setUpdatedAt(now);
        updatedVoucher.setStatus(determineVoucherStatus(updatedVoucher.getStartDate(), updatedVoucher.getEndDate(), now));

        return updatedVoucher;
    }

    /**
     * Xác định trạng thái của voucher dựa trên thời gian.
     *
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @param now Thời gian hiện tại
     * @return Trạng thái của voucher
     */
    private int determineVoucherStatus(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime now) {
        if (endDate.isBefore(now)) {
            return 4; // Hết hạn
        } else if (startDate.isAfter(now)) {
            return 3; // Tương lai
        } else {
            return 1; // Hoạt động
        }
    }

    /**
     * Chuyển đổi thời gian từ UTC sang múi giờ Việt Nam.
     *
     * @param utcDateTime Thời gian UTC
     * @return Thời gian tương ứng ở múi giờ Việt Nam
     */
    private LocalDateTime convertToVietnamTime(LocalDateTime utcDateTime) {
        return utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(VIETNAM_ZONE).toLocalDateTime();
    }
}