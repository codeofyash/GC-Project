package com.gobblecube.catalog.service;

import com.gobblecube.catalog.dto.ProductDto;

import java.util.List;

public interface SearchService {
    List<ProductDto> searchProducts(String q, Long brandId, Long categoryId);
}
