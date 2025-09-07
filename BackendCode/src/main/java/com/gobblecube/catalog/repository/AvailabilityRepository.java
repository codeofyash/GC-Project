package com.gobblecube.catalog.repository;

import com.gobblecube.catalog.entity.Availability;
import com.gobblecube.catalog.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findTop100ByPlatformOrderByCapturedAtDesc(String platform);
    List<Availability> findByVariantAndCapturedAtBetween(ProductVariant variant, Instant from, Instant to);


    @Query("SELECT a FROM Availability a WHERE a.variant.id = :variantId ORDER BY a.capturedAt DESC")
    List<Availability> findHistoryByVariant(@Param("variantId") Long variantId);
    List<Availability> findByVariantIdIn(List<Long> variantIds);


}
