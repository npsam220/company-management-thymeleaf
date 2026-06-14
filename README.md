# Company Management Thymeleaf

Traditional server-rendered company management system for practicing the stack commonly found in
Japanese enterprise projects:

- Java 17
- Spring Boot 3
- Thymeleaf
- MyBatis
- MySQL
- Flyway
- Docker Compose

This project was migrated from the React version of
[company-management](https://github.com/npsam220/company-management). The entity, DTO, mapper,
service, database, Flyway, and Docker layers are retained. The React frontend is replaced with
Spring MVC controllers and Thymeleaf templates.

## Staff Management

| Screen | URL | Functions |
| --- | --- | --- |
| Staff list | `/staff/list` | Search, pagination, edit, delete, create |
| Staff detail | `/staff/detail/{id}` | Display and update staff information |
| Staff create | `/staff/create` | Register staff and auto-generate staff number |

Photo upload, resume upload, postal code search, and the existing REST API under `/api/staff/**`
remain available.

## Run With Docker

```bash
docker compose up --build
```

Open [http://localhost:8081/staff/list](http://localhost:8081/staff/list).

## Run Locally

Start MySQL first, then run:

```bash
cd backend
./mvnw spring-boot:run
```

The default local database settings are:

```text
URL: jdbc:mysql://localhost:3306/company_management
Username: root
Password: springboot
```

Open [http://localhost:8080/staff/list](http://localhost:8080/staff/list).

## Test

```bash
cd backend
./mvnw test
```

## Next Modules

- Contract Management
- Customer Management
- Spring Security login
- Role-based authorization
