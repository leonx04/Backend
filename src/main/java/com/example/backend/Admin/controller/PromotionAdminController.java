package com.example.backend.Admin.controller;

import com.example.backend.Library.model.dto.request.promotion.Promotion_Admin_DTO;
import com.example.backend.Library.service.interfaces.Promotion_Service;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/promotions")
@CrossOrigin(origins =  "http://127.0.0.1:5500/")
public class PromotionAdminController {
    @Autowired
    private  Promotion_Service promotionService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPromotions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Promotion_Admin_DTO> pagePromotions = promotionService.getAllPromotions(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("promotions", pagePromotions.getContent());
        response.put("currentPage", pagePromotions.getNumber());
        response.put("totalItems", pagePromotions.getTotalElements());
        response.put("totalPages", pagePromotions.getTotalPages());

        return ResponseEntity.ok(response);
    }

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
        Pageable paging = PageRequest.of(page, size);
        Page<Promotion_Admin_DTO> pagePromotions = promotionService.searchPromotions(startDate, endDate, minDiscount, maxDiscount, code, name, paging);

        Map<String, Object> response = new HashMap<>();
        response.put("promotions", pagePromotions.getContent());
        response.put("currentPage", pagePromotions.getNumber());
        response.put("totalItems", pagePromotions.getTotalElements());
        response.put("totalPages", pagePromotions.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion_Admin_DTO> getPromotionById(@PathVariable Integer id) {
        Promotion_Admin_DTO promotion = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping
    public ResponseEntity<Promotion_Admin_DTO> createPromotion(@RequestBody Promotion_Admin_DTO promotionDto) {
        System.out.println("Debug - createPromotion - startDate: " + promotionDto.getStartDate());
        System.out.println("Debug - createPromotion - endDate: " + promotionDto.getEndDate());
        Promotion_Admin_DTO createdPromotion = promotionService.createPromotion(promotionDto);
        return new ResponseEntity<>(createdPromotion, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Promotion_Admin_DTO> updatePromotion(@PathVariable Integer id, @RequestBody Promotion_Admin_DTO promotionDto) {
        System.out.println("Debug - updatePromotion - startDate: " + promotionDto.getStartDate());
        System.out.println("Debug - updatePromotion - endDate: " + promotionDto.getEndDate());
        Promotion_Admin_DTO updatedPromotion = promotionService.updatePromotion(id, promotionDto);
        return ResponseEntity.ok(updatedPromotion);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Integer id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
    
}
