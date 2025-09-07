package com.gobblecube.catalog.service;

import com.gobblecube.catalog.dto.BrandDto;
import com.gobblecube.catalog.dto.ProductDto;

import java.util.List;

public interface BrandService {
    BrandDto create(BrandDto dto);
    BrandDto update(Long id, BrandDto dto);
    BrandDto get(Long id);
    List<BrandDto> listAll();
    void delete(Long id);
    List<BrandDto> listChildren(Long parentId);
    List<ProductDto> productsOfBrandAndChildren(Long brandId);
}
