package com.example.backend.Library.model.mapper;

import com.example.backend.Library.model.dto.Voucher_Admin_DTO;
import com.example.backend.Library.model.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface VoucherMapper {
    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);

    @Mapping(source = "createdDate", target = "createdAt")
    @Mapping(source = "updatedDate", target = "updatedAt")
    Voucher_Admin_DTO toDto(Voucher voucher);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "createdAt", target = "createdDate")
    @Mapping(source = "updatedAt", target = "updatedDate")
    Voucher toEntity(Voucher_Admin_DTO voucherDto);

    default LocalDateTime map(String date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy--:--:--");
        return LocalDateTime.parse(date, formatter);
    }

    default String map(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy--:--:--");
        return date.format(formatter);
    }
}