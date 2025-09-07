package com.gobblecube.catalog.service.impl;

import com.gobblecube.catalog.dto.CategoryCoverageDto;
import com.gobblecube.catalog.dto.PriceCompareDto;
import com.gobblecube.catalog.repository.AvailabilityRepository;
import com.gobblecube.catalog.repository.ProductVariantRepository;
import com.gobblecube.catalog.service.AnalyticsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnalyticsServiceImpl implements AnalyticsService {
    private final EntityManager em;
    private final AvailabilityRepository availabilityRepo;
    private final ProductVariantRepository variantRepo;


    @Autowired
    public AnalyticsServiceImpl(EntityManager em, AvailabilityRepository availabilityRepo, ProductVariantRepository variantRepo) {
        this.em = em; this.availabilityRepo = availabilityRepo; this.variantRepo = variantRepo;
    }


    @Override
    public List<CategoryCoverageDto> categoryCoverage(String platform) {
// simple native SQL: count distinct variants grouped by category
        String sql = "SELECT p.category_id, c.name, COUNT(DISTINCT a.variant_id) as variant_count, COUNT(DISTINCT v.product_id) as product_count " +
                "FROM availability a " +
                "JOIN product_variants v ON v.id = a.variant_id " +
                "JOIN products p ON p.id = v.product_id " +
                "JOIN categories c ON c.id = p.category_id " +
                "WHERE a.platform = :platform " +
                "GROUP BY p.category_id, c.name";
        Query q = em.createNativeQuery(sql);
        q.setParameter("platform", platform);
        @SuppressWarnings("unchecked") List<Object[]> rows = q.getResultList();
        List<CategoryCoverageDto> out = new ArrayList<>();
        for (Object[] r : rows) {
            CategoryCoverageDto d = new CategoryCoverageDto();
            d.categoryId = ((Number) r[0]).longValue(); d.categoryName = (String) r[1]; d.variantCount = ((Number) r[2]).longValue(); d.productCount = ((Number) r[3]).longValue(); out.add(d);
        }
        return out;
    }


    @Override
    public List<PriceCompareDto> comparePrices(Long variantId) {
// aggregate min/avg/max/latest by platform for given variant
        String sql = "SELECT a.platform, MIN(a.price), AVG(a.price), MAX(a.price), MAX(a.captured_at) as latest_ts " +
                "FROM availability a WHERE a.variant_id = :vid GROUP BY a.platform";

        Query q = em.createNativeQuery(sql);
        q.setParameter("vid", variantId);
        @SuppressWarnings("unchecked") List<Object[]> rows = q.getResultList();
        List<PriceCompareDto> out = new ArrayList<>();
        for (Object[] r : rows) {
            PriceCompareDto p = new PriceCompareDto();
            p.platform = (String) r[0]; p.minPrice = (BigDecimal) r[1]; p.avgPrice = (BigDecimal) r[2]; p.maxPrice = (BigDecimal) r[3]; p.latestPrice = (BigDecimal) r[3]; // latest will be fetched separately if needed
            out.add(p);
        }
        return out;
    }


    @Override
    public List<Long> gapAnalysis(String platformA, String platformB, Long categoryId) {
        String sql = ""
                + "SELECT DISTINCT a.variant_id "
                + "FROM availability a "
                + "JOIN ( "
                + "  SELECT variant_id, MAX(captured_at) AS maxt "
                + "  FROM availability "
                + "  WHERE platform = :pa "
                + "  GROUP BY variant_id "
                + ") la ON la.variant_id = a.variant_id AND la.maxt = a.captured_at "
                + "JOIN product_variants v ON v.id = a.variant_id "
                + "JOIN products p ON p.id = v.product_id "
                + "LEFT JOIN ( "
                + "  SELECT variant_id, MAX(captured_at) AS maxt "
                + "  FROM availability "
                + "  WHERE platform = :pb "
                + "  GROUP BY variant_id "
                + ") lb ON lb.variant_id = a.variant_id "
                + "WHERE a.platform = :pa "
                + "  AND lb.maxt IS NULL ";

        // Add category filter only if categoryId is not null
        if (categoryId != null) {
            sql += " AND p.category_id = :cid";
        }

        Query q = em.createNativeQuery(sql);
        q.setParameter("pa", platformA);
        q.setParameter("pb", platformB);
        if (categoryId != null) {
            q.setParameter("cid", categoryId);
        }

        @SuppressWarnings("unchecked")
        List<Number> rows = q.getResultList();
        List<Long> out = new ArrayList<>(rows.size());
        for (Number n : rows) {
            out.add(n.longValue());
        }
        return out;
    }

}
