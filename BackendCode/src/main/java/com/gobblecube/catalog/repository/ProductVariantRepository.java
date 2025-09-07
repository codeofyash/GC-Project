package com.gobblecube.catalog.repository;

import com.gobblecube.catalog.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findByBarcode(String barcode);
    Optional<ProductVariant> findBySku(String sku);
}
