package com.gobblecube.catalog.service.impl;

import com.gobblecube.catalog.dto.CategoryDto;
import com.gobblecube.catalog.entity.Category;
import com.gobblecube.catalog.repository.CategoryRepository;
import com.gobblecube.catalog.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repo) { this.repo = repo; }


    @Override
    public CategoryDto create(CategoryDto dto) {
        Category c = new Category();
        c.setName(dto.getName());
        c.setDescription(dto.getDescription());
        if (dto.parentId != null) {
            repo.findById(dto.parentId).ifPresent(c::setParent);
        }
        repo.save(c);
        dto.id = c.getId(); return dto;
    }


    @Override
    public CategoryDto update(Long id, CategoryDto dto) {
        Category c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        c.setName(dto.getName());
        c.setDescription(dto.getDescription());
        if (dto.parentId != null) repo.findById(dto.parentId).ifPresent(c::setParent);
        repo.save(c);
        dto.id = c.getId(); return dto;
    }


    @Override
    public CategoryDto get(Long id) {
        Category c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        CategoryDto dto = new CategoryDto();
        dto.id = c.getId();
        dto.name = c.getName();
        dto.description = c.getDescription();
        dto.parentId = c.getParent() == null ? null : c.getParent().getId(); return dto;
    }


    @Override
    public List<CategoryDto> listAll() {
        return repo.findAll().stream().map(c -> { CategoryDto d = new CategoryDto(); d.id=c.getId(); d.name=c.getName(); d.description=c.getDescription(); d.parentId=c.getParent()==null?null:c.getParent().getId(); return d; }).collect(Collectors.toList());
    }
}
