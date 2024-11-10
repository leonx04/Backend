package com.example.backend.Library.controller;

import com.example.backend.Library.service.payment.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/create-payment/{orderId}")
    public ResponseEntity<?> createPayment(@PathVariable int orderId, HttpServletRequest request) {
        try {
            String paymentUrl = vnPayService.createPaymentUrl(orderId, request.getRemoteAddr());
            return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/payment-callback")
    public ResponseEntity<?> paymentCallback(@RequestParam Map<String, String> params) {
        boolean isValid = vnPayService.verifyPaymentResponse(params);
        if (isValid) {
            return ResponseEntity.ok(Map.of("status", "success"));
        }
        return ResponseEntity.badRequest().body(Map.of("status", "failed"));
    }
}