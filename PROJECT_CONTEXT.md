# Company Management Thymeleaf Project Context

## Goal

Build a traditional Japanese-style enterprise application with:

- Java 17
- Spring Boot 3
- Thymeleaf
- MyBatis
- MySQL
- Flyway
- Docker Compose

The project is based on the existing `company-management` system. Its backend, database design,
MyBatis mappings, Flyway migrations, and Docker configuration are retained. React is not used in
this version.

## Architecture

```text
Thymeleaf
    |
Spring MVC Controller
    |
Service
    |
MyBatis
    |
MySQL
```

## Current Scope

Staff Management:

- Staff list
- Search
- Pagination
- Staff detail and update
- Staff create
- Staff delete
- Employee number auto generation
- Postal code search
- Photo upload
- Resume upload

## Main Routes

```text
GET  /staff/list
GET  /staff/detail/{id}
POST /staff/update
GET  /staff/create
POST /staff/create
POST /staff/delete/{id}
```

The previous REST endpoints under `/api/staff/**` are retained for compatibility and backend
practice.

## Future Scope

- Contract Management
- Customer Management
- Spring Security
- Login
- Role-based authorization
