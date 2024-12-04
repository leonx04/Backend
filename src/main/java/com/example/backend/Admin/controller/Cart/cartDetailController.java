package com.example.backend.Admin.controller.Cart;

import com.example.backend.Library.model.dto.request.cart.CartDetailDTO;
import com.example.backend.Library.service.impl.cart.cartDetail_Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart/detail")
public class cartDetailController {
    @Autowired
    private cartDetail_Impl cartService;
    @GetMapping()
    public ResponseEntity<?> getCartDetail(@RequestParam("id") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("ID is required");
        }
        List<CartDetailDTO> cartDetailDTOs = cartService.findByIdUserd(id);
        return ResponseEntity.ok(cartDetailDTOs);
    }
}
