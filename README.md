# Company Management Thymeleaf

A traditional server-rendered company management system built for practicing a Japanese enterprise-style Spring stack.

## Tech Stack

- Java 17
- Spring Boot 3
- Thymeleaf
- MyBatis
- MySQL 8
- Flyway
- Docker Compose

## Overview

This project replaces the frontend of the original React-based company management app with Spring MVC and Thymeleaf templates.

The current implementation focuses on:

- Staff management
- Customer management
- Server-rendered list/detail/create flows
- MyBatis-based data access
- Flyway-managed schema and seed data

## Implemented Screens

### Staff Management

| Screen | URL | Functions |
| --- | --- | --- |
| Staff list | `/staff/list` | Search, pagination, create, edit, delete |
| Staff detail | `/staff/detail/{id}` | Display and update staff information |
| Staff create | `/staff/create` | Register staff and auto-generate staff number |

Additional staff-related features:

- Photo upload
- Resume upload
- Postal code search
- Existing REST endpoints under `/api/staff/**`

### Customer Management

| Screen | URL | Functions |
| --- | --- | --- |
| Customer list | `/cust/list` | Search, pagination, create, edit, logical delete |
| Customer detail | `/cust/detail/{id}` | Display and update customer information |
| Customer create | `/cust/create` | Register customer and related managers |

Additional customer-related features:

- Manager row add/remove
- Bank master lookup dialog
- Postal code search
- Logical delete via `CUST_DELFLG = '1'`

## Run With Docker

```bash
docker compose up --build
```

After startup:

- App: [http://localhost:8081](http://localhost:8081)
- Staff list: [http://localhost:8081/staff/list](http://localhost:8081/staff/list)
- Customer list: [http://localhost:8081/cust/list](http://localhost:8081/cust/list)
- MySQL: `localhost:3308`

## Run Locally

Start MySQL first, then run:

```bash
cd backend
./mvnw spring-boot:run
```

Default local database settings:

```text
URL: jdbc:mysql://localhost:3306/company_management
Username: root
Password: springboot
```

After startup:

- App: [http://localhost:8080](http://localhost:8080)
- Staff list: [http://localhost:8080/staff/list](http://localhost:8080/staff/list)
- Customer list: [http://localhost:8080/cust/list](http://localhost:8080/cust/list)

## Database Migrations

Flyway runs automatically on application startup.

Main migration contents include:

- Base schema creation
- Staff, customer, manager, bank, and user tables
- Seed data for staff, customer, manager, bank, and user records

## Test

Run tests with:

```bash
cd backend
./mvnw test
```

Note:

- `BackendApplicationTests` requires a reachable MySQL instance.
- Controller and service unit tests can be run independently without a live database.

## Current Limitations

- Spring Security login is not implemented yet
- Role-based authorization is not implemented yet
- Some modules from the original system are still out of scope

## Future Scope

- Spring Security login
- Role-based authorization
- Additional business modules such as contract management
