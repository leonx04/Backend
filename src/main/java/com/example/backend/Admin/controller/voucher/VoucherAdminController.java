package com.example.backend.Admin.controller.voucher;

import com.example.backend.Library.model.dto.request.voucher.Voucher_Admin_DTO;
import com.example.backend.Library.service.interfaces.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller để quản lý các yêu cầu liên quan đến voucher từ phía admin.
 */
@RestController
@RequestMapping("/api/${api.version}/admin/vouchers")
@CrossOrigin(origins =  "http://127.0.0.1:5500/") // Cho phép truy cập từ địa chỉ này
public class VoucherAdminController {

    @Autowired
    private VoucherService voucherService; // Dịch vụ quản lý voucher

    /**
     * Lấy tất cả các voucher.
     *
     * @return danh sách voucher dưới dạng ResponseEntity
     */
    @GetMapping
    public ResponseEntity<Page<Voucher_Admin_DTO>> getAllVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(voucherService.getAllVouchersPageable(pageable));
    }

    /**
     * Lấy các voucher theo trạng thái.
     *
     * @param status trạng thái của voucher
     * @return danh sách voucher theo trạng thái dưới dạng ResponseEntity
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Voucher_Admin_DTO>> getVouchersByStatus(@PathVariable Integer status) {
        return ResponseEntity.ok(voucherService.getVouchersByStatus(status));
    }

    /**
     * Lấy các voucher trong khoảng thời gian nhất định.
     *
     * @param startDate thời gian bắt đầu
     * @param endDate thời gian kết thúc
     * @return danh sách voucher trong khoảng thời gian dưới dạng ResponseEntity
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<Voucher_Admin_DTO>> getVouchersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(voucherService.getVouchersByDateRange(startDate, endDate));
    }

    /**
     * Lấy các voucher theo khoảng giá trị giảm giá.
     *
     * @param minValue giá trị giảm giá tối thiểu
     * @param maxValue giá trị giảm giá tối đa
     * @return danh sách voucher trong khoảng giá trị dưới dạng ResponseEntity
     */
    @GetMapping("/value-range")
    public ResponseEntity<List<Voucher_Admin_DTO>> getVouchersByValueRange(
            @RequestParam BigDecimal minValue,
            @RequestParam BigDecimal maxValue) {
        return ResponseEntity.ok(voucherService.getVouchersByValueRange(minValue, maxValue));
    }

    /**
     * Lấy các voucher theo loại.
     *
     * @param voucherType loại voucher
     * @return danh sách voucher theo loại dưới dạng ResponseEntity
     */
    @GetMapping("/type/{voucherType}")
    public ResponseEntity<List<Voucher_Admin_DTO>> getVouchersByType(@PathVariable String voucherType) {
        return ResponseEntity.ok(voucherService.getVouchersByType(voucherType));
    }

    /**
     * Tìm kiếm voucher dựa trên nhiều tiêu chí.
     *
     * @param code mã voucher
     * @param description mô tả voucher
     * @param minValue giá trị giảm giá tối thiểu
     * @param maxValue giá trị giảm giá tối đa
     * @param status trạng thái voucher
     * @param voucherType loại voucher
     * @param startDate thời gian bắt đầu
     * @param endDate thời gian kết thúc
     * @return danh sách voucher tìm kiếm được dưới dạng ResponseEntity
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Voucher_Admin_DTO>> searchVouchers(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal minValue,
            @RequestParam(required = false) BigDecimal maxValue,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String voucherType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher_Admin_DTO> results = voucherService.searchVouchersPageable(code, description, minValue, maxValue,
                status, voucherType, startDate, endDate, pageable);
        return ResponseEntity.ok(results);
    }

    /**
     * Tạo mới một voucher.
     *
     * @param voucherDto thông tin voucher cần tạo
     * @return voucher đã được tạo dưới dạng ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Voucher_Admin_DTO> createVoucher(@RequestBody Voucher_Admin_DTO voucherDto) {
        System.out.println("Debug - createVoucher - startDate: " + voucherDto.getStartDate());
        System.out.println("Debug - createVoucher - endDate: " + voucherDto.getEndDate());
        Voucher_Admin_DTO createdVoucher = voucherService.createVoucher(voucherDto);
        return new ResponseEntity<>(createdVoucher, HttpStatus.CREATED);
    }

    /**
     * Cập nhật thông tin của một voucher.
     *
     * @param id id của voucher cần cập nhật
     * @param voucherDto thông tin mới của voucher
     * @return voucher đã cập nhật dưới dạng ResponseEntity
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Voucher_Admin_DTO> updateVoucher(@PathVariable Integer id, @RequestBody Voucher_Admin_DTO voucherDto) {
        System.out.println("Debug - updateVoucher - startDate: " + voucherDto.getStartDate());
        System.out.println("Debug - updateVoucher - endDate: " + voucherDto.getEndDate());
        Voucher_Admin_DTO updatedVoucher = voucherService.updateVoucher(id, voucherDto);
        return ResponseEntity.ok(updatedVoucher);
    }

    /**
     * Xóa một voucher theo id.
     *
     * @param id id của voucher cần xóa
     * @return ResponseEntity không có nội dung
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Integer id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build(); // Trả về phản hồi không có nội dung
    }

    /**
     * Lấy thông tin chi tiết của một voucher theo id.
     *
     * @param id id của voucher cần lấy thông tin
     * @return voucher đã tìm thấy dưới dạng ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Voucher_Admin_DTO> getVoucherById(@PathVariable Integer id) {
        return ResponseEntity.ok(voucherService.getVoucherById(id));
    }

//    @GetMapping("/{code}")
//    public ResponseEntity<Voucher_Admin_DTO> getVoucherByCode(@PathVariable String code) {
//        return ResponseEntity.ok(voucherService.getVoucherByCode(code));
//    }

}
