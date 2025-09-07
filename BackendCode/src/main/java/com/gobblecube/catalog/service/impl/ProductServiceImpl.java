package com.gobblecube.catalog.service.impl;

import com.gobblecube.catalog.dto.AvailabilityDto;
import com.gobblecube.catalog.dto.ProductCreateRequest;
import com.gobblecube.catalog.dto.ProductDto;
import com.gobblecube.catalog.dto.VariantDto;
import com.gobblecube.catalog.entity.Product;
import com.gobblecube.catalog.entity.ProductVariant;
import com.gobblecube.catalog.mapper.AvailabilityMapper;
import com.gobblecube.catalog.mapper.ProductMapper;
import com.gobblecube.catalog.repository.*;
import com.gobblecube.catalog.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final BrandRepository brandRepo;
    private final CategoryRepository categoryRepo;
    private final ProductVariantRepository variantRepo;
    private final AvailabilityRepository availabilityRepo;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, BrandRepository brandRepo, CategoryRepository categoryRepo, ProductVariantRepository variantRepo, AvailabilityRepository availabilityRepo) {
        this.productRepo = productRepo; this.brandRepo = brandRepo; this.categoryRepo = categoryRepo; this.variantRepo = variantRepo; this.availabilityRepo = availabilityRepo;
    }


    @Override
    public ProductDto create(ProductCreateRequest req) {
        Product p = new Product(); p.setName(req.name);
        if (req.brandId != null) brandRepo.findById(req.brandId).ifPresent(p::setBrand);
        if (req.categoryId != null) categoryRepo.findById(req.categoryId).ifPresent(p::setCategory);
        p.setPrimaryUnit(req.primaryUnit); p.setPrimarySize(req.primarySize);
        if (req.variants != null) {
            for (VariantDto vdto : req.variants) {
                ProductVariant v = new ProductVariant();
                v.setProduct(p);
                v.setSku(vdto.sku);
                v.setBarcode(vdto.barcode);
                v.setUnit(vdto.unit);
                v.setSize(vdto.size);
                v.setMrp(vdto.mrp);
                v.setAttributesJson(vdto.attributesJson);
                p.getVariants().add(v);
            }
        }
        Product saved = productRepo.save(p);
        ProductDto dto = toDto(saved);
        return dto;
    }


    @Override
    public ProductDto update(Long id, ProductCreateRequest req) {
        Product p = productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        p.setName(req.name);
        if (req.brandId != null) brandRepo.findById(req.brandId).ifPresent(p::setBrand);
        if (req.categoryId != null) categoryRepo.findById(req.categoryId).ifPresent(p::setCategory);
        p.setPrimaryUnit(req.primaryUnit); p.setPrimarySize(req.primarySize);
        Product saved = productRepo.save(p);
        return toDto(saved);
    }


    @Override
    public ProductDto get(Long id) {
        Product p = productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return toDto(p);
    }


    @Override
    public List<ProductDto> listAll() {
        return productRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }
    @Override
    public List<ProductDto> findByBrandId(Long brandId) {
        List<Product> products = productRepo.findByBrandId(brandId);
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByBrandIds(List<Long> brandIds) {
        List<Product> products = productRepo.findByBrandIdIn(brandIds);
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }
    @Override
    public ProductDto getFull(Long id) {
        Product product = productRepo.findWithVariantsById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        List<ProductVariant> variants = product.getVariants() == null ? Collections.emptyList() : product.getVariants();

        // map variants
        List<VariantDto> variantDtos = variants.stream().map(ProductMapper::toVariantDto).collect(Collectors.toList());

        // collect variant ids
        List<Long> variantIds = variants.stream()
                .map(ProductVariant::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<Long, List<AvailabilityDto>> availabilityMap = new HashMap<>();
        if (!variantIds.isEmpty()) {
            List<com.gobblecube.catalog.entity.Availability> avails = availabilityRepo.findByVariantIdIn(variantIds);
            for (com.gobblecube.catalog.entity.Availability a : avails) {
                AvailabilityDto ad = AvailabilityMapper.toDto(a);
                availabilityMap.computeIfAbsent(ad.getVariantId(), k -> new ArrayList<>()).add(ad);
            }
        }

        return ProductMapper.toProductFullDto(product, variantDtos, availabilityMap);
    }

    private ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.id = p.getId();
        dto.name = p.getName();
        dto.brandId = p.getBrand()==null?null:p.getBrand().getId();
        dto.categoryId = p.getCategory()==null?null:p.getCategory().getId(); dto.primaryUnit = p.getPrimaryUnit();
        dto.primarySize = p.getPrimarySize();
        dto.variants = p.getVariants().stream().map(v -> {
            VariantDto vd = new VariantDto(); vd.id = v.getId(); vd.sku = v.getSku(); vd.barcode = v.getBarcode(); vd.unit = v.getUnit(); vd.size = v.getSize(); vd.mrp = v.getMrp(); vd.attributesJson = v.getAttributesJson(); return vd;
        }).collect(Collectors.toList());
        return dto;
    }
}
