package com.example.backend.Library.enums.product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public enum ProductStatus {
    ALL(0),
    ACTIVE(1),
    INACTIVE(2);

    private final int value;

    // Constructor

    // Getter
    public int getValue() {
        return value;
    }

    // Optional: Method to get ProductStatus from value
    public static ProductStatus fromValue(int value) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
