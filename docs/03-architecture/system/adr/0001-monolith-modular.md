# ADR-0001: Modular monolith với Spring Boot và PostgreSQL

## Status

Accepted.

## Context

Sản phẩm cần hoàn thành nhanh nhưng vẫn cần transaction nhất quán giữa CRM, provenance, gợi ý và tự động hóa. Sơ đồ mục tiêu nêu JPA/Hibernate và một PostgreSQL database.

## Decision

Triển khai một Spring Boot deployable với package theo module và layers. PostgreSQL là persistence duy nhất, schema quản lý bởi Flyway. UI/API là adapters, không chứa luật nghiệp vụ.

## Consequences

Đơn giản cho demo và reset dữ liệu; ranh giới module được enforce bằng convention/package review thay vì network isolation. Có thể tách service sau nếu nhu cầu vận hành thay đổi.

