package com.example.backend.Admin.controller.promotion;

import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.service.interfaces.promotion.Promotion_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/${api.version}/admin/promotions")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class PromotionAdminController {

    @Autowired
    private Promotion_Service promotionService;

    /**
     * Hàm hỗ trợ tạo Pageable cho các API có phân trang.
     */
    private Pageable createPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    /**
     * Hàm hỗ trợ chuyển đổi đối tượng Page thành Map để trả về kết quả.
     */
    private Map<String, Object> createPagedResponse(Page<Promotion_Admin_DTO> pageData) {
        Map<String, Object> response = new HashMap<>();
        response.put("promotions", pageData.getContent());
        response.put("currentPage", pageData.getNumber());
        response.put("totalItems", pageData.getTotalElements());
        response.put("totalPages", pageData.getTotalPages());
        return response;
    }

    /**
     * API lấy tất cả chương trình khuyến mãi (có phân trang).
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPromotions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = createPageable(page, size);
        Page<Promotion_Admin_DTO> pagePromotions = promotionService.getAllPromotions(paging);
        return ResponseEntity.ok(createPagedResponse(pagePromotions));
    }

    /**
     * API tìm kiếm chương trình khuyến mãi dựa theo các tiêu chí lọc.
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchPromotions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) BigDecimal minDiscount,
            @RequestParam(required = false) BigDecimal maxDiscount,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = createPageable(page, size);
        Page<Promotion_Admin_DTO> pagePromotions = promotionService.searchPromotions(
                startDate, endDate, minDiscount, maxDiscount, code, name, paging);
        return ResponseEntity.ok(createPagedResponse(pagePromotions));
    }

    /**
     * API lấy thông tin chi tiết của một chương trình khuyến mãi theo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Promotion_Admin_DTO> getPromotionById(@PathVariable Integer id) {
        Promotion_Admin_DTO promotion = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    /**
     * API tạo mới chương trình khuyến mãi.
     */
    @PostMapping
    public ResponseEntity<Promotion_Admin_DTO> createPromotion(@RequestBody Promotion_Admin_DTO promotionDto) {
        logDebugInfo("createPromotion", promotionDto);
        Promotion_Admin_DTO createdPromotion = promotionService.createPromotion(promotionDto);
        return new ResponseEntity<>(createdPromotion, HttpStatus.CREATED);
    }

    /**
     * API cập nhật thông tin chương trình khuyến mãi theo ID.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Promotion_Admin_DTO> updatePromotion(
            @PathVariable Integer id, @RequestBody Promotion_Admin_DTO promotionDto) {
        logDebugInfo("updatePromotion", promotionDto);
        Promotion_Admin_DTO updatedPromotion = promotionService.updatePromotion(id, promotionDto);
        return ResponseEntity.ok(updatedPromotion);
    }

    /**
     * API xóa chương trình khuyến mãi theo ID.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Integer id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Hàm hỗ trợ ghi log thông tin debug cho các API.
     */
    private void logDebugInfo(String action, Promotion_Admin_DTO dto) {
        System.out.printf("Debug - %s - startDate: %s, endDate: %s%n",
                action, dto.getStartDate(), dto.getEndDate());
    }
}
