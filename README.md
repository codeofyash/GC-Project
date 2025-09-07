# GobbleCube — Catalog & Analytics Prototype

## Overview
This project is a prototype for **GobbleCube** to organize, manage, and analyze **crawled e-grocery product data** (Zepto, Blinkit, Flipkart Minutes, Swiggy Instamart, etc.).

It demonstrates:
- A **canonical catalog model** (Brands, Categories, Products, Variants, Availability).
- REST APIs for CRUD operations and analytics.
- A basic **React + Tailwind frontend** for browsing and analyzing the catalog.
- Seed data for **Unilever** with sub-brands like Dove, Lux, Lifebuoy, Surf Excel, Vaseline.

---

## Tech Stack

### Backend
- **Java 17**, **Spring Boot 3**
- **Gradle** (build tool)
- **MySQL** (persistent storage)
- **Hibernate / JPA** (ORM)
- **Flyway** (DB migrations)

### Frontend
- **React (Vite)** single-page app
- **Tailwind CSS**
- Minimal custom router & API integration

---

## Features

- **Catalog CRUD APIs**
  - `/brands`, `/categories`, `/products`, `/variants`
  - Support for **parent/child brands** (Unilever → Dove, Lux, etc.)
  - Support for hierarchical categories

- **Analytics APIs**
  - Category coverage (variants per platform per category)
  - Price comparison across platforms
  - Gap analysis (products on one platform but missing on another)

- **Search APIs**
  - Full-text & filterable search by name, brand, category

- **Frontend**
  - Dashboard showing brands, categories, and quick actions
  - Browse **brands → sub-brands → products → variants**
  - Search products
  - Analytics tab: category coverage & gap analysis

---

## Entity Model

### ER Diagram

```mermaid
erDiagram
    %% --- BRAND ---
    BRAND {
        bigint id PK
        varchar name
        text description
        bigint parent_brand_id FK
    }
    BRAND ||--o{ BRAND : has_sub_brands
    BRAND ||--o{ PRODUCT : has_products

    %% --- CATEGORY ---
    CATEGORY {
        bigint id PK
        varchar name
        text description
        bigint parent_category_id FK
    }
    CATEGORY ||--o{ CATEGORY : has_sub_categories
    CATEGORY ||--o{ PRODUCT : has_products

    %% --- PRODUCT ---
    PRODUCT {
        bigint id PK
        varchar name
        varchar primary_unit
        double primary_size
        bigint brand_id FK
        bigint category_id FK
    }
    PRODUCT ||--o{ PRODUCT_VARIANT : has_variants

    %% --- PRODUCT_VARIANT ---
    PRODUCT_VARIANT {
        bigint id PK
        varchar sku
        varchar barcode
        varchar unit
        double size
        decimal mrp
        bigint product_id FK
    }
    PRODUCT_VARIANT ||--o{ AVAILABILITY : has_availabilities

    %% --- AVAILABILITY ---
    AVAILABILITY {
        bigint id PK
        varchar platform
        varchar platform_sku
        decimal price
        decimal mrp
        boolean in_stock
        int delivery_lead_time_minutes
        timestamp captured_at
        bigint product_variant_id FK
    }
