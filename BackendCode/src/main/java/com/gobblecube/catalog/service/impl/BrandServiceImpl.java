package com.gobblecube.catalog.service.impl;

import com.gobblecube.catalog.dto.BrandDto;
import com.gobblecube.catalog.dto.BrandSummaryDto;
import com.gobblecube.catalog.dto.ProductDto;
import com.gobblecube.catalog.entity.Brand;
import com.gobblecube.catalog.repository.BrandRepository;
import com.gobblecube.catalog.service.BrandService;
import com.gobblecube.catalog.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repo;
    private final ProductService productService;
    @Autowired
    public BrandServiceImpl(BrandRepository repo, ProductService productService) {
        this.repo = repo;
        this.productService = productService;
    }


    @Override
    public BrandDto create(BrandDto dto) {
        Brand b = toEntity(dto);
        if (dto.getParentId() != null) {
            Brand parent = repo.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent brand not found: " + dto.getParentId()));
            if (parent.getParent() != null) {
                throw new IllegalArgumentException("Parent brand cannot be a child itself (one-level only)");
            }
            b.setParent(parent);
        }

        Brand saved = repo.save(b);
        return toDto(saved);
    }


    @Override
    public BrandDto update(Long id, BrandDto dto) {
        Brand existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Brand not found: " + id));
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        // parent changes
        if (dto.getParentId() != null) {
            if (dto.getParentId().equals(id)) {
                throw new IllegalArgumentException("Brand cannot be parent of itself");
            }
            Brand parent = repo.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent brand not found: " + dto.getParentId()));
            if (parent.getParent() != null) {
                throw new IllegalArgumentException("Parent brand cannot be a child itself (one-level only)");
            }
            existing.setParent(parent);
        } else if (dto.getParentId() == null && existing.getParent() != null && dto.getParentId() != null) {
            // if desired to remove parent: handle accordingly (here ignore)
        }

        Brand saved = repo.save(existing);
        return toDto(saved);
    }


    @Override
    public BrandDto get(Long id) {
        Brand b = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        BrandDto dto = new BrandDto(); dto.id = b.getId(); dto.name = b.getName(); dto.description = b.getDescription();
        return dto;
    }


    @Override
    public List<BrandDto> listAll() {
        return repo.findByParentIsNull().stream()
                .map(b -> {
                    BrandDto d = new BrandDto();
                    d.id = b.getId();
                    d.name = b.getName();
                    d.description = b.getDescription();
                    return d;
                })
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<BrandDto> listChildren(Long parentId) {
        List<Brand> children = repo.findByParentId(parentId);
        return children.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> productsOfBrandAndChildren(Long brandId) {
        List<Long> ids = new ArrayList<>();
        ids.add(brandId);
        List<Brand> children = repo.findByParentId(brandId);
        children.forEach(c -> ids.add(c.getId()));
        return productService.findByBrandIds(ids);
    }

    private BrandDto toDto(Brand b) {
        if (b == null) return null;
        BrandDto dto = new BrandDto();
        dto.setId(b.getId());
        dto.setName(b.getName());
        dto.setDescription(b.getDescription());
        dto.setParentId(b.getParent() != null ? b.getParent().getId() : null);
        // map immediate children
        dto.setChildren(b.getChildren() != null ?
                b.getChildren().stream().map(this::toSummary).collect(Collectors.toList()) :
                List.of());
        return dto;
    }

    private BrandSummaryDto toSummary(Brand b) {
        if (b == null) return null;
        BrandSummaryDto s = new BrandSummaryDto();
        s.setId(b.getId());
        s.setName(b.getName());
        return s;
    }

    private Brand toEntity(BrandDto dto) {
        if (dto == null) return null;
        Brand b = new Brand();
        b.setId(dto.getId());
        b.setName(dto.getName());
        b.setDescription(dto.getDescription());
        // parent set in service (avoid loading lazily here)
        return b;
    }
}
