package com.example.backend.Library.model.dto.request.products.variant;

import com.example.backend.Library.enums.product.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
public class ProductVariantRequest {
     @NotBlank(message = "Missing SKU code")
     String SKU;
     @Min(message = "Quantity must be greater than 0", value = 1)
     Integer quantity;
     @Min(message = "Price must be greater than 0", value = 1)
     BigDecimal price;

     @Min(message = "Weight must be greater than 0", value = 1)
     Integer weight;

     @Min(value = 1, message = "Missing product id")
     @NotNull(message = "Missing product id")
     Integer productId;

     @NotNull(message = "Please select a size")
     @Min(value = 1, message = "Please select a size")
     Integer sizeId;
     @NotNull(message = "Please select a color")
     @Min(value = 1, message = "Please select a color")
     Integer colorId;

     //xem vid bổ sung valid cho nó :giá trị hợp lệ.
     ProductStatus status;

     @CreationTimestamp
     LocalDate createdAt;

     @NotNull(message = "User  is null!")
     Integer userId;
     // Override toString to customize output
     @Override
     public String toString() {
          return "ProductVariantRequest{" +
                  "SKU='" + SKU + '\'' +
                  ", quantity=" + quantity +
                  ", price=" + price +
                  ", productId=" + productId +
                  ", sizeId=" + sizeId +
                  ", colorId=" + colorId +
                  ", status=" + status +
                  ", createdAt=" + createdAt +
                  ", userId=" + userId +
                  '}';
     }
}
