package com.example.backend.Admin.controller.promotion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Library.model.entity.products.ProductVariant;
import com.example.backend.Library.service.impl.promotion.PromotionProductServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/${api.version}/admin/promotion-products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class PromotionProductController {

    private final PromotionProductServiceImpl promotionProductService;

    /**
     * API để áp dụng promotion cho danh sách các product detail.
     *
     * @param promotionId ID của promotion cần áp dụng
     * @param ProductVariantIds Danh sách ID của các product detail cần áp dụng promotion
     * @return ResponseEntity chứa danh sách các product detail đã được cập nhật
     */
    @PostMapping("/apply")
    public ResponseEntity<List<ProductVariant>> applyPromotionToProducts(
            @RequestParam Integer promotionId, // Lấy promotion ID từ query parameter
            @RequestBody List<Integer> ProductVariantIds) { // Nhận danh sách ID product detail từ request body
        // Gọi service để áp dụng promotion và trả về danh sách sản phẩm đã cập nhật
        List<ProductVariant> updatedProducts = promotionProductService.applyPromotionToProducts(promotionId, ProductVariantIds);
        return ResponseEntity.ok(updatedProducts); // Trả về kết quả dưới dạng HTTP 200 OK
    }

    /**
     * API để gỡ bỏ promotion khỏi danh sách các product detail.
     *
     * @param ProductVariantIds Danh sách ID của các product detail cần gỡ promotion
     * @return ResponseEntity chứa danh sách các product detail đã được cập nhật
     */
    @PostMapping("/remove")
    public ResponseEntity<List<ProductVariant>> removePromotionFromProducts(
            @RequestBody List<Integer> ProductVariantIds) { // Nhận danh sách ID product detail từ request body
        // Gọi service để gỡ promotion và trả về danh sách sản phẩm đã cập nhật
        List<ProductVariant> updatedProducts = promotionProductService.removePromotionFromProducts(ProductVariantIds);
        return ResponseEntity.ok(updatedProducts); // Trả về kết quả dưới dạng HTTP 200 OK
    }


}