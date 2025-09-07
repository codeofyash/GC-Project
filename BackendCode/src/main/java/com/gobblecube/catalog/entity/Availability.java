package com.gobblecube.catalog.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "availability", indexes = {@Index(columnList = "platform, variant_id, captured_at")})
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String platform; // keep platform as string for flexibility (or change to FK to Platform entity)


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;


    private String platformSku;


    private BigDecimal price;


    private BigDecimal mrp;


    private Boolean inStock;


    private Integer deliveryLeadTimeMinutes;


    private Instant capturedAt = Instant.now();


    private String metadataJson;


    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public ProductVariant getVariant() { return variant; }
    public void setVariant(ProductVariant variant) { this.variant = variant; }
    public String getPlatformSku() { return platformSku; }
    public void setPlatformSku(String platformSku) { this.platformSku = platformSku; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getMrp() { return mrp; }
    public void setMrp(BigDecimal mrp) { this.mrp = mrp; }
    public Boolean getInStock() { return inStock; }
    public void setInStock(Boolean inStock) { this.inStock = inStock; }
    public Integer getDeliveryLeadTimeMinutes() { return deliveryLeadTimeMinutes; }
    public void setDeliveryLeadTimeMinutes(Integer deliveryLeadTimeMinutes) { this.deliveryLeadTimeMinutes = deliveryLeadTimeMinutes; }
    public Instant getCapturedAt() { return capturedAt; }
    public void setCapturedAt(Instant capturedAt) { this.capturedAt = capturedAt; }
    public String getMetadataJson() { return metadataJson; }
    public void setMetadataJson(String metadataJson) { this.metadataJson = metadataJson; }
}
