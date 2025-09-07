package com.gobblecube.catalog.service.impl;

import com.gobblecube.catalog.dto.AvailabilityDto;
import com.gobblecube.catalog.entity.Availability;
import com.gobblecube.catalog.entity.ProductVariant;
import com.gobblecube.catalog.repository.AvailabilityRepository;
import com.gobblecube.catalog.repository.ProductVariantRepository;
import com.gobblecube.catalog.service.AvailabilityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.gobblecube.catalog.mapper.AvailabilityMapper.toDto;

@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository repo;
    private final ProductVariantRepository variantRepo;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository repo, ProductVariantRepository variantRepo) { this.repo = repo; this.variantRepo = variantRepo; }


    @Override
    public AvailabilityDto create(AvailabilityDto dto) {
        Availability a = new Availability();
        a.setPlatform(dto.platform);
        if (dto.variantId != null)
            variantRepo.findById(dto.variantId).ifPresent(a::setVariant);
        a.setPlatformSku(dto.platformSku);
        a.setPrice(dto.price);
        a.setMrp(dto.mrp);
        a.setInStock(dto.inStock);
        a.setDeliveryLeadTimeMinutes(dto.deliveryLeadTimeMinutes);
        a.setCapturedAt(dto.capturedAt);
        Availability saved = repo.save(a);
        dto.id = saved.getId(); return dto;
    }


    @Override
    public List<AvailabilityDto> findLatestByPlatform(String platform, int limit) {
        return repo.findTop100ByPlatformOrderByCapturedAtDesc(platform).stream().limit(limit).map(a -> { AvailabilityDto d = new AvailabilityDto(); d.id=a.getId(); d.platform=a.getPlatform(); d.variantId=a.getVariant()==null?null:a.getVariant().getId(); d.platformSku=a.getPlatformSku(); d.price=a.getPrice(); d.mrp=a.getMrp(); d.inStock=a.getInStock(); d.deliveryLeadTimeMinutes=a.getDeliveryLeadTimeMinutes(); d.capturedAt=a.getCapturedAt(); return d; }).collect(Collectors.toList());
    }



    @Override
    public List<AvailabilityDto> findHistoryByVariant(Long variantId) {
        ProductVariant v = variantRepo.findById(variantId).orElseThrow(() -> new IllegalArgumentException("Variant not found"));
        return repo.findByVariantAndCapturedAtBetween(v, java.time.Instant.EPOCH, java.time.Instant.now()).stream().map(a -> { AvailabilityDto d = new AvailabilityDto(); d.id=a.getId(); d.platform=a.getPlatform(); d.variantId=a.getVariant()==null?null:a.getVariant().getId(); d.platformSku=a.getPlatformSku(); d.price=a.getPrice(); d.mrp=a.getMrp(); d.inStock=a.getInStock(); d.deliveryLeadTimeMinutes=a.getDeliveryLeadTimeMinutes(); d.capturedAt=a.getCapturedAt(); return d; }).collect(Collectors.toList());
    }

    @Override
    public AvailabilityDto get(Long id) {
        Availability a = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Availability not found"));
        AvailabilityDto d = toDto(a);
        return d;
    }

    @Override
    public AvailabilityDto update(Long id, AvailabilityDto dto) {
        Availability existing = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Availability not found"));
        // set fields similar to create
        existing.setPlatform(dto.platform);
        if (dto.variantId != null) variantRepo.findById(dto.variantId).ifPresent(existing::setVariant);
        existing.setPlatformSku(dto.platformSku);
        existing.setPrice(dto.price);
        existing.setMrp(dto.mrp);
        existing.setInStock(dto.inStock);
        existing.setDeliveryLeadTimeMinutes(dto.deliveryLeadTimeMinutes);
        if (dto.capturedAt != null) existing.setCapturedAt(dto.capturedAt);
        Availability saved = repo.save(existing);
        return toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Availability not found");
        repo.deleteById(id);
    }
}
