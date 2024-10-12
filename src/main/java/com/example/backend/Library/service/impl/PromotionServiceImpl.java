package com.example.backend.Library.service.impl;

import com.example.backend.Library.exception.ExceptionHandles;
import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.model.entity.promotion.Promotion;
import com.example.backend.Library.model.mapper.promotion.PromotionMapper;
import com.example.backend.Library.repository.Promotion_Repository;
import com.example.backend.Library.service.interfaces.Promotion_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements Promotion_Service {


    private final Promotion_Repository promotionRepository;



    @Autowired
    public PromotionServiceImpl(Promotion_Repository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<Promotion_Admin_DTO> searchPromotions(LocalDateTime startDate, LocalDateTime endDate,
                                                      BigDecimal minDiscount, BigDecimal maxDiscount,
                                                      String code, String name) {
        List<Promotion> promotions = promotionRepository.searchPromotions(startDate, endDate, minDiscount, maxDiscount, code, name);
        return promotions.stream()
                .map(PromotionMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Promotion_Admin_DTO getPromotionById(Integer id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandles.ResourceNotFoundException("Promotion not found with id: " + id));
        return PromotionMapper.INSTANCE.toDto(promotion);
    }

    @Override
    public Promotion_Admin_DTO createPromotion(Promotion_Admin_DTO promotionDto) {
        Promotion promotion = PromotionMapper.INSTANCE.toEntity(promotionDto);

        LocalDateTime now = LocalDateTime.now();
        promotion.setCreatedAt(now);
        promotion.setUpdatedAt(now);

        // Xác định trạng thái dựa trên ngày bắt đầu và ngày kết thúc
        if (promotion.getEndDate().isBefore(now)) {
            promotion.setStatus(4); // Hết hạn
        } else if (promotion.getStartDate().isAfter(now)) {
            promotion.setStatus(3); // Tương lai
        } else {
            promotion.setStatus(1); // Hoạt động
        }

        Promotion savedPromotion = promotionRepository.save(promotion);
        return PromotionMapper.INSTANCE.toDto(savedPromotion);
    }

    @Override
    public Promotion_Admin_DTO updatePromotion(Integer id, Promotion_Admin_DTO promotionDto) {
        Promotion existingPromotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandles.ResourceNotFoundException("Promotion not found with id: " + id));

        Promotion updatedPromotion = PromotionMapper.INSTANCE.toEntity(promotionDto);
        updatedPromotion.setId(existingPromotion.getId());
        updatedPromotion.setCreatedAt(existingPromotion.getCreatedAt());

        LocalDateTime now = LocalDateTime.now();
        updatedPromotion.setUpdatedAt(now);

        Promotion savedPromotion = promotionRepository.save(updatedPromotion);
        return PromotionMapper.INSTANCE.toDto(savedPromotion);
    }

    @Override
    public void deletePromotion(Integer id) {
        if (!promotionRepository.existsById(id)) {
            throw new ExceptionHandles.ResourceNotFoundException("Promotion not found with id: " + id);
        }
        promotionRepository.deleteById(id);
    }

    @Override
    public List<Promotion_Admin_DTO> getAllPromotions() {
        return promotionRepository.findAll().stream()
                .map(PromotionMapper.INSTANCE::toDto)
                .collect(Collectors.toList()) ;
    }
}