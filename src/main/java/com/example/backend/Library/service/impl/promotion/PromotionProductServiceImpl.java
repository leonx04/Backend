package com.example.backend.Library.service.impl.promotion;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.Library.repository.products.productVariant.ProductVariantRepository;
import org.springframework.stereotype.Service;

import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.model.entity.products.ProductVariant;
import com.example.backend.Library.model.entity.promotion.Promotion;
import com.example.backend.Library.repository.promotion.Promotion_Repository;
import com.example.backend.Library.service.interfaces.products.ProductVariantService;
import com.example.backend.Library.service.interfaces.promotion.Promotion_Service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

// Đánh dấu lớp này là một service của Spring
@Service
// Tự động khởi tạo các thành phần cần thiết thông qua constructor
@RequiredArgsConstructor
// Thiết lập các trường mặc định có quyền truy cập là private
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionProductServiceImpl {

    // Khai báo các dependency cần thiết
    final ProductVariantService ProductVariantService;
    final Promotion_Service promotionService;
    final Promotion_Repository promotionRepository;

    /**
     * Áp dụng promotion cho danh sách các product detail.
     *
     * @param promotionId ID của promotion cần áp dụng
     * @param ProductVariantIds Danh sách ID của các product detail cần áp dụng
     * @return Danh sách các product detail đã được cập nhật
     */
    @Transactional
    public List<ProductVariant> applyPromotionToProducts(Integer promotionId, List<Integer> ProductVariantIds) {
        // Lấy thông tin promotion và kiểm tra tính hợp lệ
        Promotion_Admin_DTO promotionDTO = promotionService.getPromotionById(promotionId);
//        validatePromotion(promotionDTO);

        // Tìm promotion trong repository hoặc ném ngoại lệ nếu không tìm thấy
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy promotion với id: " + promotionId));

        List<ProductVariant> updatedProducts = new ArrayList<>();

        // Áp dụng promotion cho từng product detail
        for (Integer ProductVariantId : ProductVariantIds) {
            try {
                // Tìm product detail hoặc ném ngoại lệ nếu không tìm thấy
                ProductVariant ProductVariant = ProductVariantService.findById(ProductVariantId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy product detail với id: " + ProductVariantId));

                // Kiểm tra tính hợp lệ của product detail và cập nhật status nếu cần
//                validateProductVariantForPromotion(ProductVariant);
                ensureValidStatus(ProductVariant);

                // Gán promotion cho product detail và lưu vào danh sách kết quả
                ProductVariant.setPromotion(promotion);
                updatedProducts.add(ProductVariantService.update(ProductVariant));
            } catch (Exception e) {
                // Ném ngoại lệ nếu có lỗi trong quá trình áp dụng promotion
                throw new RuntimeException(
                        "Lỗi khi áp dụng promotion cho product detail " + ProductVariantId + ": " + e.getMessage());
            }
        }
        return updatedProducts;
    }

    /**
     * Gỡ bỏ promotion khỏi danh sách các product detail.
     *
     * @param ProductVariantIds Danh sách ID của các product detail cần gỡ bỏ promotion
     * @return Danh sách các product detail đã được cập nhật
     */
    @Transactional
    public List<ProductVariant> removePromotionFromProducts(List<Integer> ProductVariantIds) {
        List<ProductVariant> updatedProducts = new ArrayList<>();

        // Gỡ bỏ promotion cho từng product detail
        for (Integer ProductVariantId : ProductVariantIds) {
            try {
                // Tìm product detail hoặc ném ngoại lệ nếu không tìm thấy
                ProductVariant ProductVariant = ProductVariantService.findById(ProductVariantId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy product detail với id: " + ProductVariantId));

                // Đảm bảo status hợp lệ trước khi gỡ bỏ promotion
                ensureValidStatus(ProductVariant);
                ProductVariant.setPromotion(null); // Bỏ gán promotion
                updatedProducts.add(ProductVariantService.update(ProductVariant));
            } catch (Exception e) {
                // Ném ngoại lệ nếu có lỗi trong quá trình gỡ bỏ promotion
                throw new RuntimeException(
                        "Lỗi khi xóa promotion khỏi product detail " + ProductVariantId + ": " + e.getMessage());
            }
        }
        return updatedProducts;
    }

    /**
     * Kiểm tra tính hợp lệ của promotion.
     *
     * @param promotion DTO chứa thông tin của promotion cần kiểm tra
     */
//    private void validatePromotion(Promotion_Admin_DTO promotion) {
//        LocalDateTime now = LocalDateTime.now();
//        if (promotion.getStatus() != 1) {
//            throw new IllegalStateException("Promotion không trong trạng thái hoạt động");
//        }
//        if (promotion.getStartDate().isAfter(now) || promotion.getEndDate().isBefore(now)) {
//            throw new IllegalStateException("Promotion không trong thời gian hiệu lực");
//        }
//    }

    /**
     * Kiểm tra tính hợp lệ của product detail để áp dụng promotion.
     *
     * @param ProductVariant Product detail cần kiểm tra
     */
    private void validateProductVariantForPromotion(ProductVariant ProductVariant) {
        if (ProductVariant.getStatus() != 3) {
            throw new IllegalStateException("Product detail không trong trạng thái chờ hoạt động");
        }
        if (ProductVariant.getPromotion() != null) {
            throw new IllegalStateException("Product detail đã có promotion khác");
        }
    }

    /**
     * Đảm bảo product detail có status và các thông tin khác hợp lệ.
     *
     * @param ProductVariant Product detail cần kiểm tra và cập nhật
     */
    private void ensureValidStatus(ProductVariant ProductVariant) {
        // Kiểm tra và cập nhật status nếu cần
        if (ProductVariant.getStatus() < 1) {
            ProductVariant.setStatus(1);
        }
    }
}
