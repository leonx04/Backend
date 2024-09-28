package com.example.backend.Admin.controller;

import com.example.backend.Library.model.dto.Voucher_Admin_DTO;
import com.example.backend.Library.service.interfaces.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/vouchers")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class VoucherAdminController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public ResponseEntity<List<Voucher_Admin_DTO>> getAllVouchers() {
        return ResponseEntity.ok(voucherService.getAllVouchers());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Voucher_Admin_DTO>> getVouchersByStatus(@PathVariable Integer status) {
        return ResponseEntity.ok(voucherService.getVouchersByStatus(status));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Voucher_Admin_DTO>> getVouchersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(voucherService.getVouchersByDateRange(startDate, endDate));
    }

    @GetMapping("/value-range")
    public ResponseEntity<List<Voucher_Admin_DTO>> getVouchersByValueRange(
            @RequestParam BigDecimal minValue,
            @RequestParam BigDecimal maxValue) {
        return ResponseEntity.ok(voucherService.getVouchersByValueRange(minValue, maxValue));
    }

    @GetMapping("/type/{voucherType}")
    public ResponseEntity<List<Voucher_Admin_DTO>> getVouchersByType(@PathVariable String voucherType) {
        return ResponseEntity.ok(voucherService.getVouchersByType(voucherType));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Voucher_Admin_DTO>> searchVouchers(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal minValue,
            @RequestParam(required = false) BigDecimal maxValue,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String voucherType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Voucher_Admin_DTO> results = voucherService.searchVouchers(code, description, minValue, maxValue,
                status, voucherType, startDate, endDate);
        return ResponseEntity.ok(results);
    }
    @PostMapping
    public ResponseEntity<Voucher_Admin_DTO> createVoucher(@RequestBody Voucher_Admin_DTO voucherDto) {
        return ResponseEntity.ok(voucherService.createVoucher(voucherDto));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Voucher_Admin_DTO> updateVoucher(@PathVariable Integer id, @RequestBody Voucher_Admin_DTO voucherDto) {
        return ResponseEntity.ok(voucherService.updateVoucher(id, voucherDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Integer id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher_Admin_DTO> getVoucherById(@PathVariable Integer id) {
        return ResponseEntity.ok(voucherService.getVoucherById(id));
    }
}