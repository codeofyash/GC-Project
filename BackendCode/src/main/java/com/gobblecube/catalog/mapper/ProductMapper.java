package com.gobblecube.catalog.mapper;

import com.gobblecube.catalog.dto.ProductDto;
import com.gobblecube.catalog.dto.VariantDto;
import com.gobblecube.catalog.entity.Product;
import com.gobblecube.catalog.entity.ProductVariant;

import java.util.List;

public final class ProductMapper {
    private ProductMapper() {}

    public static VariantDto toVariantDto(ProductVariant v) {
        if (v == null) return null;
        VariantDto dto = new VariantDto();
        dto.setId(v.getId());
        dto.setProductId(v.getProduct() != null ? v.getProduct().getId() : null);
        dto.setSku(v.getSku());
        dto.setBarcode(v.getBarcode());
        dto.setUnit(v.getUnit());
        dto.setSize(v.getSize());
        dto.setMrp(v.getMrp());
        dto.setAttributesJson(v.getAttributesJson());
        return dto;
    }

    public static ProductDto toProductFullDto(Product p, List<VariantDto> variants, java.util.Map<Long, java.util.List<com.gobblecube.catalog.dto.AvailabilityDto>> availabilityMap) {
        if (p == null) return null;
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setBrandId(p.getBrand() != null ? p.getBrand().getId() : null);
        dto.setCategoryId(p.getCategory() != null ? p.getCategory().getId() : null);
        dto.setPrimaryUnit(p.getPrimaryUnit());
        dto.setPrimarySize(p.getPrimarySize());
        dto.setVariants(variants);
        dto.setVariantAvailability(availabilityMap);
        return dto;
    }
}
