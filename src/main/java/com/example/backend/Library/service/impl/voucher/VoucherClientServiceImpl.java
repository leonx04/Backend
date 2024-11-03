package com.example.backend.Library.service.impl.voucher;


import com.example.backend.Library.model.dto.request.voucher.Voucher_Client_DTO;
import com.example.backend.Library.model.entity.voucher.Voucher;
import com.example.backend.Library.model.mapper.voucher.VoucherClientMapper;
import com.example.backend.Library.repository.voucher.Voucher_Repository;
import com.example.backend.Library.service.interfaces.voucher.VoucherClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoucherClientServiceImpl implements VoucherClientService {

    @Autowired
    private Voucher_Repository voucherRepository;

    private final VoucherClientMapper mapper = VoucherClientMapper.INSTANCE;

    private static final List<Integer> AVAILABLE_STATUSES = Arrays.asList(1, 3);

    @Override
    public Page<Voucher_Client_DTO> getAllAvailableVouchers(Pageable pageable) {
        Page<Voucher> voucherPage = voucherRepository.findByStatusIn(AVAILABLE_STATUSES, pageable);
        return voucherPage.map(mapper::toDto);
    }

    @Override
    public Page<Voucher_Client_DTO> getVouchersByStatus(Integer status, Pageable pageable) {
        if (!AVAILABLE_STATUSES.contains(status)) {
            throw new RuntimeException("Invalid status. Only statuses 1 and 3 are allowed.");
        }
        Page<Voucher> voucherPage = voucherRepository.findByStatus(status, pageable);
        return voucherPage.map(mapper::toDto);
    }

    @Override
    public Voucher_Client_DTO getVoucherByCode(String code) {
        Voucher voucher = voucherRepository.findByCodeAndStatusIn(code, AVAILABLE_STATUSES)
                .orElseThrow(() -> new RuntimeException("Voucher not found or unavailable with code: " + code));
        return mapper.toDto(voucher);
    }

    @Override
    public Page<Voucher_Client_DTO> searchVouchers(String code, Integer status, Pageable pageable) {
        if (status != null && !AVAILABLE_STATUSES.contains(status)) {
            throw new RuntimeException("Invalid status. Only statuses 1 and 3 are allowed.");
        }

        Page<Voucher> voucherPage;

        if (StringUtils.hasText(code)) {
            // Nếu có code
            if (status != null) {
                // Tìm theo cả code và status
                voucherPage = voucherRepository.searchByCodeAndStatus(code, status, pageable);
            } else {
                // Tìm theo code và các status cho phép
                voucherPage = voucherRepository.searchByCodeAndStatusIn(code, AVAILABLE_STATUSES, pageable);
            }
        } else {
            // Nếu không có code
            if (status != null) {
                // Chỉ tìm theo status
                voucherPage = voucherRepository.findByStatus(status, pageable);
            } else {
                // Lấy tất cả voucher có status cho phép
                voucherPage = voucherRepository.findByStatusIn(AVAILABLE_STATUSES, pageable);
            }
        }

        return voucherPage.map(mapper::toDto);
    }
}