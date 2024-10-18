package com.example.backend.Library.service.impl.promotion;

import com.example.backend.Library.exception.ExceptionHandles;
import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.model.entity.products.ProductDetail;
import com.example.backend.Library.model.entity.promotion.Promotion;
import com.example.backend.Library.repository.Promotion_Repository;
import com.example.backend.Library.service.interfaces.products.ProductDetailService;
import com.example.backend.Library.service.interfaces.promotion.Promotion_Service;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionProductServiceImpl {

    final ProductDetailService productDetailService;
    final Promotion_Service promotionService;
    final Promotion_Repository promotionRepository;

    @Transactional
    public List<ProductDetail> applyPromotionToProducts(Integer promotionId, List<Integer> productDetailIds) {
        Promotion_Admin_DTO promotionDTO = promotionService.getPromotionById(promotionId);
        validatePromotion(promotionDTO);

        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy promotion với id: " + promotionId));

        List<ProductDetail> updatedProducts = new ArrayList<>();

        for (Integer productDetailId : productDetailIds) {
            try {
                ProductDetail productDetail = productDetailService.findById(productDetailId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy product detail với id: " + productDetailId));

                validateProductDetailForPromotion(productDetail);
                ensureValidStatus(productDetail); // Đảm bảo status hợp lệ

                productDetail.setPromotion(promotion);
                updatedProducts.add(productDetailService.update(productDetail));
            } catch (Exception e) {
                throw new RuntimeException(
                        "Lỗi khi áp dụng promotion cho product detail " + productDetailId + ": " + e.getMessage());
            }
        }

        return updatedProducts;
    }

    @Transactional
    public List<ProductDetail> removePromotionFromProducts(List<Integer> productDetailIds) {
        List<ProductDetail> updatedProducts = new ArrayList<>();

        for (Integer productDetailId : productDetailIds) {
            try {
                ProductDetail productDetail = productDetailService.findById(productDetailId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy product detail với id: " + productDetailId));

                ensureValidStatus(productDetail); // Đảm bảo status hợp lệ
                productDetail.setPromotion(null);
                updatedProducts.add(productDetailService.update(productDetail));
            } catch (Exception e) {
                throw new RuntimeException(
                        "Lỗi khi xóa promotion khỏi product detail " + productDetailId + ": " + e.getMessage());
            }
        }

        return updatedProducts;
    }

    private void validatePromotion(Promotion_Admin_DTO promotion) {
        LocalDateTime now = LocalDateTime.now();
        if (promotion.getStatus() != 1) {
            throw new IllegalStateException("Promotion không trong trạng thái hoạt động");
        }
        if (promotion.getStartDate().isAfter(now) || promotion.getEndDate().isBefore(now)) {
            throw new IllegalStateException("Promotion không trong thời gian hiệu lực");
        }
    }

    private void validateProductDetailForPromotion(ProductDetail productDetail) {
        if (productDetail.getStatus() != 1) {
            throw new IllegalStateException("Product detail không trong trạng thái hoạt động");
        }
        if (productDetail.getPromotion() != null) {
            throw new IllegalStateException("Product detail đã có promotion khác");
        }
    }

    private void ensureValidStatus(ProductDetail productDetail) {
        if (productDetail.getStatus() < 1) {
            productDetail.setStatus(1);
        }
        // Thêm kiểm tra để đảm bảo các trường khác từ BaseEntity cũng hợp lệ
        if (productDetail.getCreatedBy() < 1) {
            productDetail.setCreatedBy(1);
        }
        if (productDetail.getUpdatedBy() < 1) {
            productDetail.setUpdatedBy(1);
        }
    }
}
