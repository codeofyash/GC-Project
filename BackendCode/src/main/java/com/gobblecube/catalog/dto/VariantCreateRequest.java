package com.gobblecube.catalog.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public class VariantCreateRequest {

    private Long productId;

    private String sku;

    private String barcode;

    private String unit;

    private Double size;

    private BigDecimal mrp;

    private String attributesJson;

    // getters / setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Double getSize() { return size; }
    public void setSize(Double size) { this.size = size; }

    public BigDecimal getMrp() { return mrp; }
    public void setMrp(BigDecimal mrp) { this.mrp = mrp; }

    public String getAttributesJson() { return attributesJson; }
    public void setAttributesJson(String attributesJson) { this.attributesJson = attributesJson; }
}
