package com.example.backend.Library.service.impl.cart;


import com.example.backend.Library.model.dto.request.cart.CartDetailDTO;

import com.example.backend.Library.model.entity.cart.CartDetail;
import com.example.backend.Library.repository.cart.CartDetailRepository;
import com.example.backend.Library.service.interfaces.cart.cartDetail_Interface;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class cartDetail_Impl implements cartDetail_Interface {
    @Autowired
    private CartDetailRepository cartDetail;
    @Override
    public List<CartDetailDTO> findByIdUserd(Integer id) {
        List<CartDetail> cartDetails = cartDetail.findByCart_User_Id(id);
        return cartDetails.stream()
                .map(cart -> {
                    CartDetailDTO cartDetailDTO = new CartDetailDTO();
                    cartDetailDTO.setFullNameproduct(cart.getProductVariantDetail().getFullName());
                    cartDetailDTO.setQuantity(cart.getQuantity());
                    cartDetailDTO.setPrice(cart.getProductVariantDetail().getPrice());
                    cartDetailDTO.setSubtotal(BigDecimal.valueOf(cart.getQuantity()).multiply(cart.getProductVariantDetail().getPrice()));
                    return cartDetailDTO;

                        }
                )
                .collect(Collectors.toList());
    }
}
