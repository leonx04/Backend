package com.example.backend.Library.model.entity.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
public enum OrderStatus {
    PENDING(1, "Đang chờ xử lý"),
    CONFIRMED(2, "Đã xác nhận"),
    SHIPPING(3, "Đang giao hàng"),
    COMPLETED(4, "Hoàn thành"),
    CANCELED(5, "Đã hủy");
    private final int code_status;
    private final String description;

    public static String getDescriptionByCode(int code_status) {
        for (OrderStatus status : values()) {
            if (status.code_status == code_status) {
                return status.description;
            }
        }
        throw new IllegalArgumentException("Invalid order status code: " + code_status);
    }
}
