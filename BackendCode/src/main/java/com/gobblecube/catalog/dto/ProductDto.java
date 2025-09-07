package com.gobblecube.catalog.dto;

import java.util.List;
import java.util.Map;

public class ProductDto {
    public Long id;
    public String name;
    public Long brandId;
    public Long categoryId;
    public String primaryUnit;
    public Double primarySize;
    public List<VariantDto> variants;
    private Map<Long, List<AvailabilityDto>> variantAvailability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDto> variants) {
        this.variants = variants;
    }

    public Double getPrimarySize() {
        return primarySize;
    }

    public void setPrimarySize(Double primarySize) {
        this.primarySize = primarySize;
    }

    public String getPrimaryUnit() {
        return primaryUnit;
    }

    public void setPrimaryUnit(String primaryUnit) {
        this.primaryUnit = primaryUnit;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, List<AvailabilityDto>> getVariantAvailability() {
        return variantAvailability;
    }

    public void setVariantAvailability(Map<Long, List<AvailabilityDto>> variantAvailability) {
        this.variantAvailability = variantAvailability;
    }
}
