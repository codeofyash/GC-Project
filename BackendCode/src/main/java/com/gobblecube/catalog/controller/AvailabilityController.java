package com.gobblecube.catalog.controller;

import com.gobblecube.catalog.dto.AvailabilityDto;
import com.gobblecube.catalog.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    private final AvailabilityService service;

    @Autowired
    public AvailabilityController(AvailabilityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AvailabilityDto> create(@RequestBody AvailabilityDto dto) {
        AvailabilityDto created = service.create(dto);
        // return 201 with Location header if id available
        if (created != null && created.id != null) {
            return ResponseEntity.created(URI.create("/availability/" + created.id)).body(created);
        }
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityDto> get(@PathVariable Long id) {
        AvailabilityDto dto = service.get(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Latest availability entries for a platform (most recent captured).
     * GET /api/availability/platform/{platform}/latest?limit=50
     */
    @GetMapping("/platform/{platform}/latest")
    public ResponseEntity<List<AvailabilityDto>> latestByPlatform(
            @PathVariable String platform,
            @RequestParam(name = "limit", required = false, defaultValue = "50") int limit) {
        List<AvailabilityDto> rows = service.findLatestByPlatform(platform, limit);
        return ResponseEntity.ok(rows);
    }

    /**
     * History for a variant
     * GET /api/availability/variant/{variantId}/history
     */
    @GetMapping("/variant/{variantId}/history")
    public ResponseEntity<List<AvailabilityDto>> historyByVariant(@PathVariable Long variantId) {
        List<AvailabilityDto> rows = service.findHistoryByVariant(variantId);
        return ResponseEntity.ok(rows);
    }

    /**
     * Update availability (simple replace)
     * PUT /api/availability/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityDto> update(@PathVariable Long id,@RequestBody AvailabilityDto dto) {
        AvailabilityDto updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete availability (if you want)
     * DELETE /api/availability/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
