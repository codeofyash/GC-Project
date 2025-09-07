package com.gobblecube.catalog.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class AvailabilityDto {
    public Long id;
    public String platform;
    public Long variantId;
    public String platformSku;
    public BigDecimal price;
    public BigDecimal mrp;
    public Boolean inStock;
    public Integer deliveryLeadTimeMinutes;
    public Instant capturedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public String getPlatformSku() {
        return platformSku;
    }

    public void setPlatformSku(String platformSku) {
        this.platformSku = platformSku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Instant getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(Instant capturedAt) {
        this.capturedAt = capturedAt;
    }

    public Integer getDeliveryLeadTimeMinutes() {
        return deliveryLeadTimeMinutes;
    }

    public void setDeliveryLeadTimeMinutes(Integer deliveryLeadTimeMinutes) {
        this.deliveryLeadTimeMinutes = deliveryLeadTimeMinutes;
    }
}
