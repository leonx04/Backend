package com.example.backend.Library.service.interfaces.cart;

import com.example.backend.Library.model.dto.request.cart.CartDetailDTO;

import java.util.List;

public interface cartDetail_Interface {
    List<CartDetailDTO> findByIdUserd(Integer id);
}
