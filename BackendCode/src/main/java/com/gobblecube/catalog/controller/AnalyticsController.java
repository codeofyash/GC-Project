package com.gobblecube.catalog.controller;

import com.gobblecube.catalog.dto.CategoryCoverageDto;
import com.gobblecube.catalog.dto.PriceCompareDto;
import com.gobblecube.catalog.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsService service;

    @Autowired
    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/platform/{platform}/category-coverage")
    public ResponseEntity<List<CategoryCoverageDto>> categoryCoverage(@PathVariable String platform) {
        return ResponseEntity.ok(service.categoryCoverage(platform));
    }

    @GetMapping("/variant/{variantId}/price-compare")
    public ResponseEntity<List<PriceCompareDto>> priceCompare(@PathVariable Long variantId) {
        return ResponseEntity.ok(service.comparePrices(variantId));
    }

    @GetMapping("/gap")
    public ResponseEntity<List<Long>> gap(
            @RequestParam String platformA,
            @RequestParam String platformB,
            @RequestParam Long categoryId) {
        return ResponseEntity.ok(service.gapAnalysis(platformA, platformB, categoryId));
    }
}

