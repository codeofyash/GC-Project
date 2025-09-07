package com.gobblecube.catalog.service;

import com.gobblecube.catalog.dto.ProductCreateRequest;
import com.gobblecube.catalog.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductCreateRequest req);
    ProductDto update(Long id, ProductCreateRequest req);
    ProductDto get(Long id);
    List<ProductDto> listAll();
    List<ProductDto> findByBrandId(Long brandId);
    List<ProductDto> findByBrandIds(List<Long> brandIds);
    ProductDto getFull(Long id);

}
