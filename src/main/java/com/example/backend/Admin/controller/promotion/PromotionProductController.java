package com.example.backend.Admin.controller.promotion;

import com.example.backend.Library.model.dto.response.ResponseData;
import com.example.backend.Library.model.entity.products.ProductDetail;
import com.example.backend.Library.service.impl.promotion.PromotionProductServiceImpl;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/promotion-products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class PromotionProductController {

    private final PromotionProductServiceImpl promotionProductService;

    /**
     * API để áp dụng promotion cho danh sách các product detail.
     *
     * @param promotionId ID của promotion cần áp dụng
     * @param productDetailIds Danh sách ID của các product detail cần áp dụng promotion
     * @return ResponseEntity chứa danh sách các product detail đã được cập nhật
     */
    @PostMapping("/apply")
    public ResponseEntity<List<ProductDetail>> applyPromotionToProducts(
            @RequestParam Integer promotionId, // Lấy promotion ID từ query parameter
            @RequestBody List<Integer> productDetailIds) { // Nhận danh sách ID product detail từ request body
        // Gọi service để áp dụng promotion và trả về danh sách sản phẩm đã cập nhật
        List<ProductDetail> updatedProducts = promotionProductService.applyPromotionToProducts(promotionId, productDetailIds);
        return ResponseEntity.ok(updatedProducts); // Trả về kết quả dưới dạng HTTP 200 OK
    }

    /**
     * API để gỡ bỏ promotion khỏi danh sách các product detail.
     *
     * @param productDetailIds Danh sách ID của các product detail cần gỡ promotion
     * @return ResponseEntity chứa danh sách các product detail đã được cập nhật
     */
    @PostMapping("/remove")
    public ResponseEntity<List<ProductDetail>> removePromotionFromProducts(
            @RequestBody List<Integer> productDetailIds) { // Nhận danh sách ID product detail từ request body
        // Gọi service để gỡ promotion và trả về danh sách sản phẩm đã cập nhật
        List<ProductDetail> updatedProducts = promotionProductService.removePromotionFromProducts(productDetailIds);
        return ResponseEntity.ok(updatedProducts); // Trả về kết quả dưới dạng HTTP 200 OK
    }


}
