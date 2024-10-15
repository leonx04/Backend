package com.example.backend.Library.service.interfaces;

import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface Promotion_Service {

//    List<Promotion_Admin_DTO> searchPromotions(LocalDateTime startDate, LocalDateTime endDate,
//                                               BigDecimal minDiscount, BigDecimal maxDiscount,
//                                               String code, String name);

    Promotion_Admin_DTO getPromotionById(Integer id);

    Promotion_Admin_DTO createPromotion(Promotion_Admin_DTO promotionDto);

    Promotion_Admin_DTO updatePromotion(Integer id, Promotion_Admin_DTO promotionDto);

    void deletePromotion(Integer id);

    Page<Promotion_Admin_DTO> getAllPromotions(Pageable pageable);
    Page<Promotion_Admin_DTO> searchPromotions(LocalDateTime startDate, LocalDateTime endDate,
                                               BigDecimal minDiscount, BigDecimal maxDiscount, String code, String name, Pageable pageable);
}
