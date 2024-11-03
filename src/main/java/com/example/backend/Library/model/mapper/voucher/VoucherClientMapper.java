package com.example.backend.Library.model.mapper.voucher;

import com.example.backend.Library.model.dto.request.voucher.Voucher_Client_DTO;
import com.example.backend.Library.model.entity.voucher.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VoucherClientMapper {
    VoucherClientMapper INSTANCE = Mappers.getMapper(VoucherClientMapper.class);

    Voucher_Client_DTO toDto(Voucher voucher);

    Voucher toEntity(Voucher_Client_DTO voucherClientDto);
}