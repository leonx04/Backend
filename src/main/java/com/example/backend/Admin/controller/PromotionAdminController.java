package com.example.backend.Admin.controller;

import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.service.interfaces.Promotion_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/promotions")
@CrossOrigin(origins =  "http://127.0.0.1:5500/")
public class PromotionAdminController {
    @Autowired
    private  Promotion_Service promotionService;

    @GetMapping
    public ResponseEntity<List<Promotion_Admin_DTO>> getAllPromotions() {
        List<Promotion_Admin_DTO> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Promotion_Admin_DTO>> searchPromotions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) BigDecimal minDiscount,
            @RequestParam(required = false) BigDecimal maxDiscount,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name) {
        List<Promotion_Admin_DTO> promotions = promotionService.searchPromotions(startDate, endDate, minDiscount, maxDiscount, code, name);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion_Admin_DTO> getPromotionById(@PathVariable Integer id) {
        Promotion_Admin_DTO promotion = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping
    public ResponseEntity<Promotion_Admin_DTO> createPromotion(@RequestBody Promotion_Admin_DTO promotionDto) {
        Promotion_Admin_DTO createdPromotion = promotionService.createPromotion(promotionDto);
        return new ResponseEntity<>(createdPromotion, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Promotion_Admin_DTO> updatePromotion(@PathVariable Integer id, @RequestBody Promotion_Admin_DTO promotionDto) {
        Promotion_Admin_DTO updatedPromotion = promotionService.updatePromotion(id, promotionDto);
        return ResponseEntity.ok(updatedPromotion);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Integer id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
}
