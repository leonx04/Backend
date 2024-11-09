package com.example.backend.Library.service.impl.promotion;

import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.model.entity.products.ProductDetail;
import com.example.backend.Library.model.entity.promotion.Promotion;
import com.example.backend.Library.repository.promotion.Promotion_Repository;
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

// Đánh dấu lớp này là một service của Spring
@Service
// Tự động khởi tạo các thành phần cần thiết thông qua constructor
@RequiredArgsConstructor
// Thiết lập các trường mặc định có quyền truy cập là private
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionProductServiceImpl {

    // Khai báo các dependency cần thiết
    final ProductDetailService productDetailService;
    final Promotion_Service promotionService;
    final Promotion_Repository promotionRepository;

    /**
     * Áp dụng promotion cho danh sách các product detail.
     *
     * @param promotionId ID của promotion cần áp dụng
     * @param productDetailIds Danh sách ID của các product detail cần áp dụng
     * @return Danh sách các product detail đã được cập nhật
     */
    @Transactional
    public List<ProductDetail> applyPromotionToProducts(Integer promotionId, List<Integer> productDetailIds) {
        // Lấy thông tin promotion và kiểm tra tính hợp lệ
        Promotion_Admin_DTO promotionDTO = promotionService.getPromotionById(promotionId);
//        validatePromotion(promotionDTO);

        // Tìm promotion trong repository hoặc ném ngoại lệ nếu không tìm thấy
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy promotion với id: " + promotionId));

        List<ProductDetail> updatedProducts = new ArrayList<>();

        // Áp dụng promotion cho từng product detail
        for (Integer productDetailId : productDetailIds) {
            try {
                // Tìm product detail hoặc ném ngoại lệ nếu không tìm thấy
                ProductDetail productDetail = productDetailService.findById(productDetailId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy product detail với id: " + productDetailId));

                // Kiểm tra tính hợp lệ của product detail và cập nhật status nếu cần
//                validateProductDetailForPromotion(productDetail);
                ensureValidStatus(productDetail);

                // Gán promotion cho product detail và lưu vào danh sách kết quả
                productDetail.setPromotion(promotion);
                updatedProducts.add(productDetailService.update(productDetail));
            } catch (Exception e) {
                // Ném ngoại lệ nếu có lỗi trong quá trình áp dụng promotion
                throw new RuntimeException(
                        "Lỗi khi áp dụng promotion cho product detail " + productDetailId + ": " + e.getMessage());
            }
        }
        return updatedProducts;
    }

    /**
     * Gỡ bỏ promotion khỏi danh sách các product detail.
     *
     * @param productDetailIds Danh sách ID của các product detail cần gỡ bỏ promotion
     * @return Danh sách các product detail đã được cập nhật
     */
    @Transactional
    public List<ProductDetail> removePromotionFromProducts(List<Integer> productDetailIds) {
        List<ProductDetail> updatedProducts = new ArrayList<>();

        // Gỡ bỏ promotion cho từng product detail
        for (Integer productDetailId : productDetailIds) {
            try {
                // Tìm product detail hoặc ném ngoại lệ nếu không tìm thấy
                ProductDetail productDetail = productDetailService.findById(productDetailId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy product detail với id: " + productDetailId));

                // Đảm bảo status hợp lệ trước khi gỡ bỏ promotion
                ensureValidStatus(productDetail);
                productDetail.setPromotion(null); // Bỏ gán promotion
                updatedProducts.add(productDetailService.update(productDetail));
            } catch (Exception e) {
                // Ném ngoại lệ nếu có lỗi trong quá trình gỡ bỏ promotion
                throw new RuntimeException(
                        "Lỗi khi xóa promotion khỏi product detail " + productDetailId + ": " + e.getMessage());
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
     * @param productDetail Product detail cần kiểm tra
     */
    private void validateProductDetailForPromotion(ProductDetail productDetail) {
        if (productDetail.getStatus() != 3) {
            throw new IllegalStateException("Product detail không trong trạng thái chờ hoạt động");
        }
        if (productDetail.getPromotion() != null) {
            throw new IllegalStateException("Product detail đã có promotion khác");
        }
    }

    /**
     * Đảm bảo product detail có status và các thông tin khác hợp lệ.
     *
     * @param productDetail Product detail cần kiểm tra và cập nhật
     */
    private void ensureValidStatus(ProductDetail productDetail) {
        // Kiểm tra và cập nhật status nếu cần
        if (productDetail.getStatus() < 1) {
            productDetail.setStatus(1);
        }
        // Kiểm tra các trường createdBy và updatedBy từ BaseEntity
        if (productDetail.getCreatedBy() < 1) {
            productDetail.setCreatedBy(1);
        }
        if (productDetail.getUpdatedBy() < 1) {
            productDetail.setUpdatedBy(1);
        }
    }
}
