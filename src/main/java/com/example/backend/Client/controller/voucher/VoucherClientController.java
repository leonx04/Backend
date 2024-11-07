package com.example.backend.Client.controller.voucher;

import com.example.backend.Library.model.dto.request.voucher.Voucher_Client_DTO;
import com.example.backend.Library.service.interfaces.voucher.VoucherClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/vouchers")
@CrossOrigin(origins = "*")
public class VoucherClientController {
    @Autowired
    private VoucherClientService voucherClientService;

    @GetMapping
    public ResponseEntity<Page<Voucher_Client_DTO>> getAllAvailableVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status) {

        Pageable pageable = PageRequest.of(page, size);

        if (status != null) {
            return ResponseEntity.ok(voucherClientService.getVouchersByStatus(status, pageable));
        }

        return ResponseEntity.ok(voucherClientService.getAllAvailableVouchers(pageable));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Voucher_Client_DTO> getVoucherByCode(@PathVariable String code) {
        Voucher_Client_DTO voucher = voucherClientService.getVoucherByCode(code);
        return ResponseEntity.ok(voucher);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Voucher_Client_DTO>> searchVouchers(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher_Client_DTO> vouchers = voucherClientService.searchVouchers(code, status, pageable);
        return ResponseEntity.ok(vouchers);
    }
}