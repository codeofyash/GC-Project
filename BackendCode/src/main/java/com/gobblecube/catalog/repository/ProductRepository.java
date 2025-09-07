package com.gobblecube.catalog.repository;

import com.gobblecube.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrandId(Long brandId);
    List<Product> findByBrandIdIn(List<Long> brandIds);
    Optional<Product> findWithVariantsById(Long id);


}
