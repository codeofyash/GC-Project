package com.gobblecube.catalog.controller;

import com.gobblecube.catalog.dto.VariantCreateRequest;
import com.gobblecube.catalog.dto.VariantDto;
import com.gobblecube.catalog.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variants")
public class VariantController {
    private final VariantService service;

    @Autowired
    public VariantController(VariantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VariantDto> create(@RequestBody VariantCreateRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariantDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariantDto> update(@PathVariable Long id,@RequestBody VariantCreateRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<VariantDto>> byProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(service.findByProductId(productId));
    }
}
