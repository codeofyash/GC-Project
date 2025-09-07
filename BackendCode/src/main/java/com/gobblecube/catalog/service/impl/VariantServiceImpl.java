package com.gobblecube.catalog.service.impl;

import com.gobblecube.catalog.dto.VariantCreateRequest;
import com.gobblecube.catalog.dto.VariantDto;
import com.gobblecube.catalog.entity.Product;
import com.gobblecube.catalog.entity.ProductVariant;
import com.gobblecube.catalog.repository.ProductRepository;
import com.gobblecube.catalog.repository.ProductVariantRepository;
import com.gobblecube.catalog.service.VariantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VariantServiceImpl implements VariantService {
    private final ProductVariantRepository repo;
    private final ProductRepository productRepo;

    @Autowired
    public VariantServiceImpl(ProductVariantRepository repo, ProductRepository productRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
    }

    @Override
    public VariantDto create(VariantCreateRequest req) {
        // Validate and fetch product
        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + req.getProductId()));

        ProductVariant v = new ProductVariant();
        v.setProduct(product);
        v.setSku(req.getSku());
        v.setBarcode(req.getBarcode());
        v.setUnit(req.getUnit());
        v.setSize(req.getSize());
        v.setMrp(req.getMrp());
        v.setAttributesJson(req.getAttributesJson());

        ProductVariant saved = repo.save(v);

        VariantDto dto = new VariantDto();
        dto.id = saved.getId();
        dto.sku = saved.getSku();
        dto.barcode = saved.getBarcode();
        dto.unit = saved.getUnit();
        dto.size = saved.getSize();
        dto.mrp = saved.getMrp();
        dto.attributesJson = saved.getAttributesJson();
        return dto;
    }

    @Override
    public VariantDto update(Long id, VariantCreateRequest req) {
        ProductVariant v = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Variant not found"));
        // allow updating product association too if needed
        if (req.getProductId() != null && !req.getProductId().equals(v.getProduct().getId())) {
            Product product = productRepo.findById(req.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + req.getProductId()));
            v.setProduct(product);
        }
        v.setSku(req.getSku());
        v.setBarcode(req.getBarcode());
        v.setUnit(req.getUnit());
        v.setSize(req.getSize());
        v.setMrp(req.getMrp());
        v.setAttributesJson(req.getAttributesJson());
        repo.save(v);

        VariantDto dto = new VariantDto();
        dto.id = v.getId();
        dto.sku = v.getSku();
        dto.barcode = v.getBarcode();
        dto.unit = v.getUnit();
        dto.size = v.getSize();
        dto.mrp = v.getMrp();
        dto.attributesJson = v.getAttributesJson();
        return dto;
    }

    @Override
    public VariantDto get(Long id) {
        ProductVariant v = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Variant not found"));
        VariantDto dto = new VariantDto();
        dto.id = v.getId();
        dto.sku = v.getSku();
        dto.barcode = v.getBarcode();
        dto.unit = v.getUnit();
        dto.size = v.getSize();
        dto.mrp = v.getMrp();
        dto.attributesJson = v.getAttributesJson();
        return dto;
    }

    @Override
    public List<VariantDto> findByProductId(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return product.getVariants().stream().map(v -> {
            VariantDto d = new VariantDto();
            d.id = v.getId();
            d.sku = v.getSku();
            d.barcode = v.getBarcode();
            d.unit = v.getUnit();
            d.size = v.getSize();
            d.mrp = v.getMrp();
            d.attributesJson = v.getAttributesJson();
            return d;
        }).collect(Collectors.toList());
    }

    @Override
    public VariantDto findByBarcode(String barcode) {
        ProductVariant v = repo.findByBarcode(barcode).orElseThrow(() -> new IllegalArgumentException("Variant not found by barcode"));
        VariantDto dto = new VariantDto();
        dto.id = v.getId();
        dto.sku = v.getSku();
        dto.barcode = v.getBarcode();
        dto.unit = v.getUnit();
        dto.size = v.getSize();
        dto.mrp = v.getMrp();
        dto.attributesJson = v.getAttributesJson();
        return dto;
    }
}
