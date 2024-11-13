package com.example.backend.Library.repository.products.productVariant;

import com.example.backend.Library.model.entity.products.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    @Transactional
    @Query("SELECT count(pv) FROM ProductVariant pv WHERE pv.product.brand.id = :idBrand")
    Long countByBrand(@Param("idBrand") Integer idBrand);

    @Query("SELECT count(pv) FROM ProductVariant pv WHERE pv.product.category.id = :idCategory")
    Long countByCategory(@Param("idCategory") Integer idCategory);

    @Query("SELECT count(pv) FROM ProductVariant pv WHERE pv.product.material.id = :idMaterial")
    Long countByMaterial(@Param("idMaterial") Integer idMaterial);

    @Transactional
    @Query("SELECT COUNT(pv) FROM ProductVariant pv WHERE pv.product.id = :productId")
    Long countByProductId(@Param("productId") Integer productId);
}
