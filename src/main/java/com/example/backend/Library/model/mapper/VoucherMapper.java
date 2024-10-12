package com.example.backend.Library.model.mapper;

import com.example.backend.Library.model.dto.request.voucher.Voucher_Admin_DTO;
import com.example.backend.Library.model.entity.voucher.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Interface VoucherMapper dùng để ánh xạ giữa Voucher và Voucher_Admin_DTO.
 */
@Mapper
public interface VoucherMapper {
    // Tạo một instance của VoucherMapper
    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);

    /**
     * Chuyển đổi từ Voucher entity sang Voucher_Admin_DTO.
     *
     * @param voucher đối tượng Voucher cần chuyển đổi
     * @return đối tượng Voucher_Admin_DTO
     */
    @Mapping(source = "createdAt", target = "createdDate") // Ánh xạ createdDate thành createdAt
    @Mapping(source = "updatedAt", target = "updatedDate") // Ánh xạ updatedDate thành updatedAt
    Voucher_Admin_DTO toDto(Voucher voucher);

    /**
     * Chuyển đổi từ Voucher_Admin_DTO sang Voucher entity.
     *
     * @param voucherDto đối tượng Voucher_Admin_DTO cần chuyển đổi
     * @return đối tượng Voucher
     */
    @Mapping(target = "id", ignore = true) // Bỏ qua ánh xạ cho trường id
    @Mapping(source = "createdDate", target = "createdAt") // Ánh xạ createdAt thành createdDate
    @Mapping(source = "updatedDate", target = "updatedAt") // Ánh xạ updatedAt thành updatedDate
    Voucher toEntity(Voucher_Admin_DTO voucherDto);

    /**
     * Chuyển đổi từ chuỗi định dạng ngày tháng thành LocalDateTime.
     *
     * @param date chuỗi ngày tháng cần chuyển đổi
     * @return đối tượng LocalDateTime
     */
    default LocalDateTime map(String date) {
        if (date == null) {
            return null; // Nếu chuỗi là null, trả về null
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.parse(date, formatter); // Chuyển đổi chuỗi thành LocalDateTime
    }

    /**
     * Chuyển đổi từ LocalDateTime thành chuỗi định dạng ngày tháng.
     *
     * @param date đối tượng LocalDateTime cần chuyển đổi
     * @return chuỗi định dạng ngày tháng
     */
    default String map(LocalDateTime date) {
        if (date == null) {
            return null; // Nếu đối tượng là null, trả về null
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return date.format(formatter); // Chuyển đổi LocalDateTime thành chuỗi
    }
}
