package com.gobblecube.catalog.repository;

import com.gobblecube.catalog.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
    List<Brand> findByParentId(Long parentId);
    boolean existsByParentId(Long parentId);
    List<Brand> findByParentIsNull();
}
