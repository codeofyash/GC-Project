package com.gobblecube.catalog.dto;

import java.util.List;

public class ProductCreateRequest {
    public String name;
    public Long brandId;
    public Long categoryId;
    public String primaryUnit;
    public Double primarySize;
    public List<VariantDto> variants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
