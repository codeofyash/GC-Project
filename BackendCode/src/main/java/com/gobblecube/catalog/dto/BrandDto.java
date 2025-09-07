package com.gobblecube.catalog.dto;

import java.util.List;

public class BrandDto {
    public Long id;
    public String name;
    public String description;
    private Long parentId;
    private List<BrandSummaryDto> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<BrandSummaryDto> getChildren() {
        return children;
    }

    public void setChildren(List<BrandSummaryDto> children) {
        this.children = children;
    }
}