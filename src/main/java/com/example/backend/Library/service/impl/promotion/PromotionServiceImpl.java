package com.example.backend.Library.service.impl.promotion;

import com.example.backend.Library.exception.ExceptionHandles;
import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.model.entity.promotion.Promotion;
import com.example.backend.Library.model.mapper.promotion.PromotionMapper;
import com.example.backend.Library.repository.promotion.Promotion_Repository;
import com.example.backend.Library.service.interfaces.promotion.Promotion_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

@Service
public class PromotionServiceImpl implements Promotion_Service {

    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");
    private static final Duration EDIT_TIME_LIMIT = Duration.ofHours(24);
    private static final Duration LONG_TERM_THRESHOLD = Duration.ofDays(30); // Ngưỡng để xác định khuyến mãi dài hạn

    private final Promotion_Repository promotionRepository;

    @Autowired
    public PromotionServiceImpl(Promotion_Repository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Page<Promotion_Admin_DTO> searchPromotions(LocalDateTime startDate, LocalDateTime endDate,
                                                      BigDecimal minDiscount, BigDecimal maxDiscount,
                                                      String code, String name, Pageable pageable) {
        Page<Promotion> promotions = promotionRepository.searchPromotions(
                startDate, endDate, minDiscount, maxDiscount, code, name, pageable);
        return promotions.map(PromotionMapper.INSTANCE::toDto);
    }

    @Override
    public Page<Promotion_Admin_DTO> getAllPromotions(Pageable pageable) {
        return promotionRepository.findAll(pageable).map(PromotionMapper.INSTANCE::toDto);
    }

    @Override
    public Promotion_Admin_DTO getPromotionById(Integer id) {
        Promotion promotion = findPromotionById(id);
        return PromotionMapper.INSTANCE.toDto(promotion);
    }

    @Override
    public Promotion_Admin_DTO createPromotion(Promotion_Admin_DTO promotionDto) {
        validatePromotionDates(promotionDto.getStartDate(), promotionDto.getEndDate());

        Promotion promotion = preparePromotionForCreation(promotionDto);
        Promotion savedPromotion = promotionRepository.save(promotion);
        return PromotionMapper.INSTANCE.toDto(savedPromotion);
    }

    @Override
    public Promotion_Admin_DTO updatePromotion(Integer id, Promotion_Admin_DTO promotionDto) {
        Promotion existingPromotion = findPromotionById(id);

        validatePromotionUpdate(existingPromotion);
        validatePromotionDates(promotionDto.getStartDate(), promotionDto.getEndDate());

        Promotion updatedPromotion = preparePromotionForUpdate(existingPromotion, promotionDto);
        Promotion savedPromotion = promotionRepository.save(updatedPromotion);
        return PromotionMapper.INSTANCE.toDto(savedPromotion);
    }

    @Override
    public void deletePromotion(Integer id) {
        Promotion promotion = findPromotionById(id);
        validatePromotionDeletion(promotion);
        promotionRepository.deleteById(id);
    }

    private void validatePromotionUpdate(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now(VIETNAM_ZONE);

        // Kiểm tra nếu là khuyến mãi dài hạn
        if (isLongTermPromotion(promotion)) {
            // Chỉ cho phép chỉnh sửa trong vòng 24h sau khi tạo
            if (Duration.between(promotion.getCreatedAt(), now).compareTo(EDIT_TIME_LIMIT) > 0) {
                throw new ExceptionHandles.ValidationException(
                        Collections.singletonList("Không thể chỉnh sửa khuyến mãi dài hạn sau 24 giờ kể từ khi tạo"));
            }
        }

        // Kiểm tra thời gian từ lần cập nhật cuối
        if (promotion.getUpdatedAt() != null &&
                Duration.between(promotion.getUpdatedAt(), now).compareTo(EDIT_TIME_LIMIT) < 0) {
            throw new ExceptionHandles.ValidationException(
                    Collections.singletonList("Phải đợi 24 giờ sau lần cập nhật cuối cùng để chỉnh sửa tiếp"));
        }
    }

    private void validatePromotionDeletion(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now(VIETNAM_ZONE);

        // Không cho phép xóa khuyến mãi đang diễn ra
        if (promotion.getStatus() == 1) {
            throw new ExceptionHandles.ValidationException(
                    Collections.singletonList("Không thể xóa khuyến mãi đang diễn ra"));
        }

        // Kiểm tra thời gian tạo với khuyến mãi dài hạn
        if (isLongTermPromotion(promotion) &&
                Duration.between(promotion.getCreatedAt(), now).compareTo(EDIT_TIME_LIMIT) > 0) {
            throw new ExceptionHandles.ValidationException(
                    Collections.singletonList("Không thể xóa khuyến mãi dài hạn sau 24 giờ kể từ khi tạo"));
        }
    }

    private boolean isLongTermPromotion(Promotion promotion) {
        if (promotion.getStartDate() == null || promotion.getEndDate() == null) {
            return false;
        }
        Duration promotionDuration = Duration.between(
                promotion.getStartDate(), promotion.getEndDate());
        return promotionDuration.compareTo(LONG_TERM_THRESHOLD) >= 0;
    }

    private void validatePromotionDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new ExceptionHandles.ValidationException(
                    Collections.singletonList("Ngày bắt đầu và ngày kết thúc không được để trống"));
        }

        LocalDateTime now = LocalDateTime.now(VIETNAM_ZONE);
        if (startDate.isBefore(now)) {
            throw new ExceptionHandles.ValidationException(
                    Collections.singletonList("Ngày bắt đầu phải sau thời điểm hiện tại"));
        }

        if (endDate.isBefore(startDate)) {
            throw new ExceptionHandles.ValidationException(
                    Collections.singletonList("Ngày kết thúc phải sau ngày bắt đầu"));
        }
    }

    private Promotion findPromotionById(Integer id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new ExceptionHandles.ResourceNotFoundException(
                        "Không tìm thấy khuyến mãi với id: " + id));
    }

    private Promotion preparePromotionForCreation(Promotion_Admin_DTO promotionDto) {
        Promotion promotion = PromotionMapper.INSTANCE.toEntity(promotionDto);

        LocalDateTime now = LocalDateTime.now(VIETNAM_ZONE);
        promotion.setStartDate(convertToVietnamTime(promotionDto.getStartDate()));
        promotion.setEndDate(convertToVietnamTime(promotionDto.getEndDate()));
        promotion.setCreatedAt(now);
        promotion.setUpdatedAt(now);

        updatePromotionStatus(promotion, now);

        return promotion;
    }

    private Promotion preparePromotionForUpdate(Promotion existingPromotion, Promotion_Admin_DTO promotionDto) {
        Promotion updatedPromotion = PromotionMapper.INSTANCE.toEntity(promotionDto);
        updatedPromotion.setId(existingPromotion.getId());
        updatedPromotion.setCreatedAt(existingPromotion.getCreatedAt());

        LocalDateTime now = LocalDateTime.now(VIETNAM_ZONE);
        updatedPromotion.setStartDate(convertToVietnamTime(promotionDto.getStartDate()));
        updatedPromotion.setEndDate(convertToVietnamTime(promotionDto.getEndDate()));
        updatedPromotion.setUpdatedAt(now);

        if (promotionDto.getStatus() != null && promotionDto.getStatus() == 2) {
            updatedPromotion.setStatus(2);
        } else {
            updatePromotionStatus(updatedPromotion, now);
        }

        return updatedPromotion;
    }

    private LocalDateTime convertToVietnamTime(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(VIETNAM_ZONE)
                .toLocalDateTime();
    }

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