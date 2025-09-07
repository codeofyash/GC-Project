package com.gobblecube.catalog.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands", indexes = {@Index(columnList = "name")})
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    private String normalizedName;


    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Brand parent;

    // children (one-level). We do not allow grandchildren logically (enforced in service)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Brand> children = new ArrayList<>();


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNormalizedName() { return normalizedName; }
    public void setNormalizedName(String normalizedName) { this.normalizedName = normalizedName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Brand getParent() {
        return parent;
    }

    public void setParent(Brand parent) {
        this.parent = parent;
    }

    public List<Brand> getChildren() {
        return children;
    }

    public void setChildren(List<Brand> children) {
        this.children = children;
    }
}
