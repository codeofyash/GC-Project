package com.gobblecube.catalog.service;

import com.gobblecube.catalog.dto.AvailabilityDto;

import java.util.List;

public interface AvailabilityService {
    AvailabilityDto create(AvailabilityDto dto);
    List<AvailabilityDto> findLatestByPlatform(String platform, int limit);
    List<AvailabilityDto> findHistoryByVariant(Long variantId);
    AvailabilityDto get(Long id);
    AvailabilityDto update(Long id, AvailabilityDto dto);
    void delete(Long id);
}
