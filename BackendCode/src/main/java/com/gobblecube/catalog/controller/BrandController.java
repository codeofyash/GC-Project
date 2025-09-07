package com.gobblecube.catalog.controller;

import com.gobblecube.catalog.dto.BrandDto;
import com.gobblecube.catalog.dto.BrandSummaryDto;
import com.gobblecube.catalog.dto.ProductDto;
import com.gobblecube.catalog.service.BrandService;
import com.gobblecube.catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService service;
    private final ProductService productService;

    @Autowired
    public BrandController(BrandService service, ProductService productService) {
        this.service = service;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<BrandDto> create(@RequestBody BrandDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<BrandDto>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDto> update(@PathVariable Long id, @RequestBody BrandDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getProductsByBrand(@PathVariable Long id) {
        List<ProductDto> products = productService.findByBrandId(id);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}/subbrands")
    public ResponseEntity<List<BrandDto>> getSubBrands(@PathVariable Long id) {
        return ResponseEntity.ok(service.listChildren(id));
    }

    @GetMapping("/{id}/products/all")
    public ResponseEntity<List<ProductDto>> getProductsAndChildren(@PathVariable Long id) {
        return ResponseEntity.ok(service.productsOfBrandAndChildren(id));
    }

}
