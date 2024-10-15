package com.example.backend.Library.service.impl;

import com.example.backend.Library.exception.ExceptionHandles;
import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.model.entity.promotion.Promotion;
import com.example.backend.Library.model.mapper.promotion.PromotionMapper;
import com.example.backend.Library.repository.Promotion_Repository;
import com.example.backend.Library.service.interfaces.Promotion_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class PromotionServiceImpl implements Promotion_Service {

    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");
    private final Promotion_Repository promotionRepository;

    @Autowired
    public PromotionServiceImpl(Promotion_Repository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    // Tìm kiếm khuyến mãi theo các tiêu chí
    @Override
    public Page<Promotion_Admin_DTO> searchPromotions(LocalDateTime startDate, LocalDateTime endDate,
                                                      BigDecimal minDiscount, BigDecimal maxDiscount,
                                                      String code, String name, Pageable pageable) {
        Page<Promotion> promotions = promotionRepository.searchPromotions(startDate, endDate, minDiscount, maxDiscount, code, name, pageable);
        return promotions.map(PromotionMapper.INSTANCE::toDto);
    }

    // Lấy tất cả khuyến mãi với phân trang
    @Override
    public Page<Promotion_Admin_DTO> getAllPromotions(Pageable pageable) {
        Page<Promotion> promotions = promotionRepository.findAll(pageable);
        return promotions.map(PromotionMapper.INSTANCE::toDto);
    }

    // Lấy khuyến mãi theo ID
    @Override
    public Promotion_Admin_DTO getPromotionById(Integer id) {
        Promotion promotion = findPromotionById(id);
        return PromotionMapper.INSTANCE.toDto(promotion);
    }

    // Tạo khuyến mãi mới
    @Override
    public Promotion_Admin_DTO createPromotion(Promotion_Admin_DTO promotionDto) {
        Promotion promotion = preparePromotionForCreation(promotionDto);
        Promotion savedPromotion = promotionRepository.save(promotion);
        return PromotionMapper.INSTANCE.toDto(savedPromotion);
    }

    // Cập nhật khuyến mãi
    @Override
    public Promotion_Admin_DTO updatePromotion(Integer id, Promotion_Admin_DTO promotionDto) {
        Promotion existingPromotion = findPromotionById(id);
        Promotion updatedPromotion = preparePromotionForUpdate(existingPromotion, promotionDto);
        Promotion savedPromotion = promotionRepository.save(updatedPromotion);
        return PromotionMapper.INSTANCE.toDto(savedPromotion);
    }

    // Xóa khuyến mãi
    @Override
    public void deletePromotion(Integer id) {
        if (!promotionRepository.existsById(id)) {
            throw new ExceptionHandles.ResourceNotFoundException("Không tìm thấy khuyến mãi với id: " + id);
        }
        promotionRepository.deleteById(id);
    }

    // Phương thức hỗ trợ: Tìm khuyến mãi theo ID
    private Promotion findPromotionById(Integer id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandles.ResourceNotFoundException("Không tìm thấy khuyến mãi với id: " + id));
    }

    // Phương thức hỗ trợ: Chuẩn bị khuyến mãi để tạo mới
    private Promotion preparePromotionForCreation(Promotion_Admin_DTO promotionDto) {
        Promotion promotion = PromotionMapper.INSTANCE.toEntity(promotionDto);
        setPromotionDates(promotion, promotionDto.getStartDate(), promotionDto.getEndDate());

        LocalDateTime now = LocalDateTime.now();
        promotion.setCreatedAt(now);
        promotion.setUpdatedAt(now);

        updatePromotionStatus(promotion, now);

        return promotion;
    }

    // Phương thức hỗ trợ: Chuẩn bị khuyến mãi để cập nhật
    private Promotion preparePromotionForUpdate(Promotion existingPromotion, Promotion_Admin_DTO promotionDto) {
        Promotion updatedPromotion = PromotionMapper.INSTANCE.toEntity(promotionDto);
        updatedPromotion.setId(existingPromotion.getId());
        updatedPromotion.setCreatedAt(existingPromotion.getCreatedAt());
        setPromotionDates(updatedPromotion, promotionDto.getStartDate(), promotionDto.getEndDate());

        LocalDateTime now = LocalDateTime.now();
        updatedPromotion.setUpdatedAt(now);

        updatePromotionStatus(updatedPromotion, now);

        return updatedPromotion;
    }

    // Phương thức hỗ trợ: Đặt ngày bắt đầu và kết thúc cho khuyến mãi
    private void setPromotionDates(Promotion promotion, LocalDateTime startDate, LocalDateTime endDate) {
        promotion.setStartDate(convertToVietnamTime(startDate));
        promotion.setEndDate(convertToVietnamTime(endDate));
    }

    // Phương thức hỗ trợ: Chuyển đổi thời gian sang múi giờ Việt Nam
    private LocalDateTime convertToVietnamTime(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(VIETNAM_ZONE).toLocalDateTime();
    }

    // Phương thức hỗ trợ: Cập nhật trạng thái khuyến mãi
    private void updatePromotionStatus(Promotion promotion, LocalDateTime now) {
        if (promotion.getEndDate().isBefore(now)) {
            promotion.setStatus(4); // Đã kết thúc
        } else if (promotion.getStartDate().isAfter(now)) {
            promotion.setStatus(3); // Chưa bắt đầu
        } else {
            promotion.setStatus(1); // Đang diễn ra
        }
    }
}