package com.gobblecube.catalog.service;

import com.gobblecube.catalog.dto.CategoryCoverageDto;
import com.gobblecube.catalog.dto.PriceCompareDto;
import com.gobblecube.catalog.dto.VariantCreateRequest;
import com.gobblecube.catalog.dto.VariantDto;

import java.util.List;

public interface VariantService {
    VariantDto create(VariantCreateRequest req);
    VariantDto update(Long id, VariantCreateRequest req);
    VariantDto get(Long id);
    List<VariantDto> findByProductId(Long productId);
    VariantDto findByBarcode(String barcode);
}
