package com.gobblecube.catalog.service.impl;

import com.gobblecube.catalog.dto.ProductDto;
import com.gobblecube.catalog.entity.Product;
import com.gobblecube.catalog.service.SearchService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    private final EntityManager em;
    @Autowired
    public SearchServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ProductDto> searchProducts(String q, Long brandId, Long categoryId) {
        StringBuilder sb = new StringBuilder("SELECT p FROM Product p WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (q != null && !q.trim().isEmpty()) {
            sb.append(" AND LOWER(p.name) LIKE :q");
            params.put("q", "%" + q.trim().toLowerCase() + "%");
        }
        if (brandId != null) {
            sb.append(" AND p.brand.id = :brandId");
            params.put("brandId", brandId);
        }
        if (categoryId != null) {
            sb.append(" AND p.category.id = :categoryId");
            params.put("categoryId", categoryId);
        }

        // Optional: add ordering (e.g., relevance or name)
        sb.append(" ORDER BY p.name ASC");

        TypedQuery<Product> query = em.createQuery(sb.toString(), Product.class);
        params.forEach(query::setParameter);

        List<Product> products = query.getResultList();

        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

    private ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.id = p.getId();
        dto.name = p.getName();
        dto.brandId = p.getBrand() == null ? null : p.getBrand().getId();
        dto.categoryId = p.getCategory() == null ? null : p.getCategory().getId();
        dto.primaryUnit = p.getPrimaryUnit();
        dto.primarySize = p.getPrimarySize();

        // For search results we return shallow variant info (ids only) to keep payload small.
        if (p.getVariants() != null && !p.getVariants().isEmpty()) {
            dto.variants = p.getVariants().stream().map(v -> {
                com.gobblecube.catalog.dto.VariantDto vd = new com.gobblecube.catalog.dto.VariantDto();
                vd.id = v.getId();
                vd.sku = v.getSku();
                vd.barcode = v.getBarcode();
                vd.unit = v.getUnit();
                vd.size = v.getSize();
                vd.mrp = v.getMrp();
                vd.attributesJson = v.getAttributesJson();
                return vd;
            }).collect(Collectors.toList());
        }

        return dto;
    }
}
