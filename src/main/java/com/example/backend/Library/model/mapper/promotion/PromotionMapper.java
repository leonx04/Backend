package com.example.backend.Library.model.mapper.promotion;

import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.model.entity.promotion.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Interface PromotionMapper dùng để ánh xạ giữa Promotion và Promotion_Admin_DTO.
 * Sử dụng MapStruct để tự động tạo implementation cho interface này.
 */
@Mapper
public interface PromotionMapper {
    // Tạo một instance của PromotionMapper sử dụng MapStruct
    PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    /**
     * Chuyển đổi từ Promotion entity sang Promotion_Admin_DTO.
     *
     * @param promotion đối tượng Promotion cần chuyển đổi
     * @return đối tượng Promotion_Admin_DTO tương ứng
     */
    @Mapping(source = "createdAt", target = "createdDate")
    @Mapping(source = "updatedAt", target = "updatedDate")
    @Mapping(source = "discountPercentage", target = "discountPercentage")
    Promotion_Admin_DTO toDto(Promotion promotion);

    /**
     * Chuyển đổi từ Promotion_Admin_DTO sang Promotion entity.
     *
     * @param promotionDto đối tượng Promotion_Admin_DTO cần chuyển đổi
     * @return đối tượng Promotion tương ứng
     */
    @Mapping(target = "id", ignore = true) // Bỏ qua việc ánh xạ trường id, thường dùng khi tạo mới entity
    @Mapping(source = "discountPercentage"
            , target = "discountPercentage")
    Promotion toEntity(Promotion_Admin_DTO promotionDto);

    /**
     * Chuyển đổi từ chuỗi định dạng ngày tháng thành LocalDateTime.
     * Phương thức này được sử dụng tự động bởi MapStruct khi cần chuyển đổi
     * các trường kiểu String sang LocalDateTime.
     *
     * @param date chuỗi ngày tháng cần chuyển đổi, định dạng "yyyy-MM-dd'T'HH:mm:ss"
     * @return đối tượng LocalDateTime tương ứng, hoặc null nếu đầu vào là null
     */
    default LocalDateTime mapStringToLocalDateTime(String date) {
        if (date == null) {
            return null; // Trả về null nếu chuỗi đầu vào là null
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(date, formatter); // Chuyển đổi chuỗi thành LocalDateTime
    }

    /**
     * Chuyển đổi từ LocalDateTime thành chuỗi định dạng ngày tháng.
     * Phương thức này được sử dụng tự động bởi MapStruct khi cần chuyển đổi
     * các trường kiểu LocalDateTime sang String.
     *
     * @param date đối tượng LocalDateTime cần chuyển đổi
     * @return chuỗi định dạng ngày tháng "yyyy-MM-dd'T'HH:mm:ss", hoặc null nếu đầu vào là null
     */
    default String mapLocalDateTimeToString(LocalDateTime date) {
        if (date == null) {
            return null; // Trả về null nếu đối tượng đầu vào là null
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return date.format(formatter); // Chuyển đổi LocalDateTime thành chuỗi
    }

    /**
     * Chuyển đổi từ BigDecimal sang String.
     * Phương thức này được sử dụng để chuyển đổi trường discountPercent.
     *
     * @param value đối tượng BigDecimal cần chuyển đổi
     * @return chuỗi đại diện cho giá trị BigDecimal, hoặc null nếu đầu vào là null
     */
    default String mapBigDecimalToString(BigDecimal value) {
        return value != null ? value.toString() : null;
    }

    /**
     * Chuyển đổi từ String sang BigDecimal.
     * Phương thức này được sử dụng để chuyển đổi trường discountPercent.
     *
     * @param value chuỗi cần chuyển đổi
     * @return đối tượng BigDecimal tương ứng, hoặc null nếu đầu vào là null
     */
    default BigDecimal mapStringToBigDecimal(String value) {
        return value != null ? new BigDecimal(value) : null;
    }
}