package com.example.backend.Admin.controller.promotion;

import com.example.backend.Library.model.entity.products.ProductDetail;
import com.example.backend.Library.service.impl.promotion.PromotionProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotion-products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class PromotionProductController {

    private final PromotionProductServiceImpl promotionProductService;

    @PostMapping("/apply")
    public ResponseEntity<List<ProductDetail>> applyPromotionToProducts(
            @RequestParam Integer promotionId,
            @RequestBody List<Integer> productDetailIds) {
        List<ProductDetail> updatedProducts = promotionProductService.applyPromotionToProducts(promotionId, productDetailIds);
        return ResponseEntity.ok(updatedProducts);
    }

    @PostMapping("/remove")
    public ResponseEntity<List<ProductDetail>> removePromotionFromProducts(
            @RequestBody List<Integer> productDetailIds) {
        List<ProductDetail> updatedProducts = promotionProductService.removePromotionFromProducts(productDetailIds);
        return ResponseEntity.ok(updatedProducts);
    }
}