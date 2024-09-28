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

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private Voucher_Repository voucherRepository;

    @Override
    public List<Voucher_Admin_DTO> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher_Admin_DTO> getVouchersByStatus(Integer status) {
        return voucherRepository.findByStatus(status).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher_Admin_DTO> getVouchersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return voucherRepository.findByDateRange(startDate, endDate).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher_Admin_DTO> getVouchersByValueRange(BigDecimal minValue, BigDecimal maxValue) {
        return voucherRepository.findByDiscountValueBetween(minValue, maxValue).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher_Admin_DTO> getVouchersByType(String voucherType) {
        return voucherRepository.findByVoucherType(voucherType).stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

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

    @Override
    public Voucher_Admin_DTO createVoucher(Voucher_Admin_DTO voucherDto) {
        Voucher voucher = VoucherMapper.INSTANCE.toEntity(voucherDto);
        voucher.setCreatedDate(LocalDateTime.now());
        voucher.setUpdatedDate(LocalDateTime.now());
        voucher = voucherRepository.save(voucher);
        return VoucherMapper.INSTANCE.toDto(voucher);
    }

    @Override
    public Voucher_Admin_DTO updateVoucher(Integer id, Voucher_Admin_DTO voucherDto) {
        Voucher existingVoucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        Voucher updatedVoucher = VoucherMapper.INSTANCE.toEntity(voucherDto);
        updatedVoucher.setId(existingVoucher.getId());
        updatedVoucher.setCreatedDate(existingVoucher.getCreatedDate());
        updatedVoucher.setUpdatedDate(LocalDateTime.now());

        updatedVoucher = voucherRepository.save(updatedVoucher);
        return VoucherMapper.INSTANCE.toDto(updatedVoucher);
    }

    @Override
    public void deleteVoucher(Integer id) {
        voucherRepository.deleteById(id);
    }

    @Override
    public Voucher_Admin_DTO getVoucherById(Integer id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));
        return VoucherMapper.INSTANCE.toDto(voucher);
    }

}