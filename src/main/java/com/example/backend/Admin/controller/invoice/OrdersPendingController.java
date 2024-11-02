package com.example.backend.Admin.controller.invoice;

import com.example.backend.Library.model.dto.Response.orders.OrderDTO;
import com.example.backend.Library.model.dto.Response.orders.PageDTO;
import com.example.backend.Library.service.impl.orders.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders/pending")
// Get list of pending orders
public class OrdersPendingController {
    @Autowired
    private OrderImpl orderImpl;


    @GetMapping()
    public ResponseEntity<?> getOrderList(@RequestParam(defaultValue = "0") int pageNo,
                                          @RequestParam(defaultValue = "10") int pageSize) {

        PageDTO<OrderDTO> pageDTO = orderImpl.getOrderfindStatus(pageNo, pageSize);

        System.out.println("Page: " + pageNo);
        System.out.println("Size: " + pageSize);
        System.out.println("Total Elements: " + pageDTO.getTotalElements());
        System.out.println("Total Pages: " + pageDTO.getTotalPages());

        return ResponseEntity.ok(pageDTO);
    }
}
