package com.example.backend.Library.service.interfaces;

import com.example.backend.Library.model.dto.Voucher_Admin_DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VoucherService {
    List<Voucher_Admin_DTO> getAllVouchers();
    List<Voucher_Admin_DTO> getVouchersByStatus(Integer status);
    List<Voucher_Admin_DTO> getVouchersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Voucher_Admin_DTO> getVouchersByValueRange(BigDecimal minValue, BigDecimal maxValue);
    List<Voucher_Admin_DTO> getVouchersByType(String voucherType);
    List<Voucher_Admin_DTO> searchVouchers(String code,
                                           String description,
                                           BigDecimal minValue,
                                           BigDecimal maxValue,
                                           Integer status,
                                           String voucherType,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate);

    Voucher_Admin_DTO createVoucher(Voucher_Admin_DTO voucherDto);
    Voucher_Admin_DTO updateVoucher(Integer id, Voucher_Admin_DTO voucherDto);
    void deleteVoucher(Integer id);
    Voucher_Admin_DTO getVoucherById(Integer id);
}