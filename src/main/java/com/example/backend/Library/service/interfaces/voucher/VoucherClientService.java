package com.example.backend.Library.service.interfaces.voucher;

import com.example.backend.Library.model.dto.request.voucher.Voucher_Client_DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherClientService {
    Page<Voucher_Client_DTO> getAllAvailableVouchers(Pageable pageable);

    Page<Voucher_Client_DTO> getVouchersByStatus(Integer status, Pageable pageable);

    Voucher_Client_DTO getVoucherByCode(String code);

    Page<Voucher_Client_DTO> searchVouchers(String code, Integer status, Pageable pageable);
}