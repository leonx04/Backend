package com.example.backend.Library.model.dto.request.products.product;

import com.example.backend.Library.enums.product.ProductStatus;
import com.example.backend.Library.model.dto.request.products.variant.ProductVariantRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
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

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class ProductRequest {
    @NotBlank(message = "Please enter the product name")
    String name;

    String description;

    @NotNull(message = "Please enter the product category")
    @Min(value = 1, message = "category is not valid")
    String category;

    @NotNull(message = "Please enter the product brand")
    @Min(value = 1, message = "brand is not valid")
    String brand;

    @NotNull(message = "Please enter the product material")
    @Min(value = 1, message = "mater is not valid")
    String material;

    @NotNull(message = "Please enter the shoe sole material")
    @Min(value = 1, message = "sole is not valid")
    String sole;


    ProductStatus status = ProductStatus.PENDING;

    @NotNull(message = "User  is null!")
    @Min(value = 1, message = "User  is null!")
    Integer userId;

    @CreationTimestamp
    LocalDate createdAt;

    @UpdateTimestamp
    LocalDate updatedAt;

//    @NotNull(message = "No product attributes provided!")
//    @JsonProperty("productVariants")
    Collection<ProductVariantRequest> productVariants;

    // Override toString to customize output
    @Override
    public String toString() {
        return "ProductRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + category +
                ", brandId=" + brand +
                ", materialId=" + material +
                ", soleId=" + sole +
                ", status=" + status +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", productVariants=" + productVariantsToString() + // Show product variants
                '}';
    }

    // Helper method to convert product variants list to string
    private String productVariantsToString() {
        if (productVariants == null || productVariants.isEmpty()) {
            return "No variants";
        }
        return productVariants.stream()
                .map(ProductVariantRequest::getSKU) // Show SKU of each product variant
                .collect(Collectors.joining(", "));
    }
}
