package com.gobblecube.catalog.mapper;

import com.gobblecube.catalog.dto.AvailabilityDto;
import com.gobblecube.catalog.entity.Availability;

public class AvailabilityMapper {
    private AvailabilityMapper() {}

    public static AvailabilityDto toDto(Availability a) {
        if (a == null) return null;
        AvailabilityDto dto = new AvailabilityDto();
        dto.setId(a.getId());
        dto.setPlatform(a.getPlatform());
        dto.setVariantId(a.getVariant() != null ? a.getVariant().getId() : null);
        dto.setPlatformSku(a.getPlatformSku());
        dto.setPrice(a.getPrice());
        dto.setMrp(a.getMrp());
        dto.setInStock(a.getInStock());
        dto.setDeliveryLeadTimeMinutes(a.getDeliveryLeadTimeMinutes());
        dto.setCapturedAt(a.getCapturedAt());
        return dto;
    }
}
