# GobbleCube — Catalog & Analytics Prototype

## Overview
This project is a prototype for **GobbleCube** to organize, manage, and analyze **crawled e-grocery product data** (Zepto, Blinkit, Flipkart Minutes, Swiggy Instamart, etc.).

It demonstrates:
- A **canonical catalog model** (Brands, Categories, Products, Variants, Availability).
- REST APIs for CRUD operations and analytics.
- A basic **React + Tailwind frontend** for browsing and analyzing the catalog.
- Seed data for **Unilever** with sub-brands like Dove, Lux, Lifebuoy, Surf Excel, Vaseline.



---

## Assumptions

1. **Crawled data is available:** The repository expects that raw crawling data has already been collected.
2. **Parsing & filtration pending:** Parsing and filtering of the raw data are not implemented here and will be handled in a later phase.
3. **Scope:** This codebase only contains the data storage design, supported operations, and cross-platform analytics — it does not include crawling, parsing, or filtration logic.

---

## Tech Stack

### Backend
- **Java 17**, **Spring Boot 3**
- **Gradle** (build tool)
- **MySQL** (persistent storage)
- **Hibernate / JPA** (ORM)

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
```

## DB ScreenShots
- **Tables**
  <img width="1428" height="365" alt="image" src="https://github.com/user-attachments/assets/6aab803d-455d-4b74-b784-0260ced531bf" />

- **Brand**
  <img width="944" height="177" alt="image" src="https://github.com/user-attachments/assets/0818d7ab-020a-4f83-9cc1-420a51e96664" />

- **Categories**
  <img width="945" height="165" alt="image" src="https://github.com/user-attachments/assets/6d4533b8-a329-4b29-b260-a7523fd8e516" />

- **Product**
  <img width="937" height="184" alt="image" src="https://github.com/user-attachments/assets/dfc3ac75-b248-4f13-84dc-244b963de8b0" />

- **Product_Varients**
  <img width="938" height="246" alt="image" src="https://github.com/user-attachments/assets/79eda291-2536-4326-968a-a91444e18c09" />

- **Availability**
  <img width="944" height="287" alt="image" src="https://github.com/user-attachments/assets/67d6bd73-f242-40fe-b757-46403ca19e45" />





