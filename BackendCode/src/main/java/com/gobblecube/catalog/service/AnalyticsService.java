package com.gobblecube.catalog.service;

import com.gobblecube.catalog.dto.CategoryCoverageDto;
import com.gobblecube.catalog.dto.PriceCompareDto;

import java.util.List;

public interface AnalyticsService {
    List<CategoryCoverageDto> categoryCoverage(String platform);
    List<PriceCompareDto> comparePrices(Long variantId);
    List<Long> gapAnalysis(String platformA, String platformB, Long categoryId);
}
