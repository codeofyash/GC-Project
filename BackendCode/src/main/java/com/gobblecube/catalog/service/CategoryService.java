package com.gobblecube.catalog.service;

import com.gobblecube.catalog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto dto);
    CategoryDto update(Long id, CategoryDto dto);
    CategoryDto get(Long id);
    List<CategoryDto> listAll();
}
