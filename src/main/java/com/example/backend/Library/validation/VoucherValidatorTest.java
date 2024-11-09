package com.example.backend.Library.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.backend.Library.model.dto.request.voucher.Voucher_Admin_DTO;
import com.example.backend.Library.validation.VoucherValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class VoucherValidatorTest {

    @Test
    public void shouldValidateVoucherWithAllValidFields() {
        // Arrange
        Voucher_Admin_DTO voucherDto = new Voucher_Admin_DTO();
        voucherDto.setCode("VALID_CODE");
        voucherDto.setDescription("VALID_DESCRIPTION");
        voucherDto.setStartDate(LocalDateTime.now());
        voucherDto.setEndDate(LocalDateTime.now().plusMonths(6));
        voucherDto.setDiscountValue(BigDecimal.valueOf(10));
        voucherDto.setMinimumOrderValue(BigDecimal.valueOf(20));
        voucherDto.setVoucherType("PERCENTAGE");
        voucherDto.setQuantity(100);

        // Act
        List<String> errors = VoucherValidator.validateVoucher(voucherDto);

        // Assert
        assertTrue(errors.isEmpty());
    }
}