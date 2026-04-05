# Finance Data Processing and Access Control Backend

This is a complete functional backend system for a Finance Dashboard. It is built exactly according to the requirements using **Spring Boot (Java 17)**, featuring secure JWT-based authentication, Role-Based Access Control (RBAC), robust payload validations, and aggregated analytical endpoints.

## Features & Core Requirements Implemented

1. **User & Role Management**:
   - Secure login and registration via `/api/auth/signup` and `/api/auth/signin`
   - Configured three user roles based on requirements:
     - `ROLE_VIEWER`: Can only view dashboard data.
     - `ROLE_ANALYST`: Can view records and access aggregated insight summaries.
     - `ROLE_ADMIN`: Full management access; can create, update, delete records and manage users.

2. **Financial Records Management**:
   - Comprehensive CRUD APIs under `/api/records`.
   - Data stored includes: Amount, Type (`INCOME`, `EXPENSE`), Category, Date, Notes.
   - Enforced validation rules and explicit mapping.

3. **Dashboard Summary APIs**:
   - High-level analytical endpoint under `/api/dashboard/summary`.
   - Returns aggregated sums parsed via `JpaRepository`'s custom `@Query`.
   - Fields: Total Income, Total Expenses, Net Balance, Category-wise Total distributions, and recent transactional activity.
   
4. **Access Control Logic**:
   - Powered by Spring Security + `jjwt` filtering.
   - Guarded methods with `@PreAuthorize`, ensuring users only access data aligned with their assigned privileges.
   
5. **Validation and Error Handling**:
   - Rejects empty values through Bean Validations (`@NotBlank`, `@Size`, `@Email`).
   - Clean handling with `AuthEntryPointJwt` capturing illegal authorization attempts efficiently.
   - Custom payload request/responses ensure clean JSON interaction without exposing database entities directly to the client at the Auth boundaries.

6. **Data Persistence**:
   - Currently persisting via an embedded H2 Memory Database for zero-setup execution.
   - Uses `spring-boot-starter-data-jpa` via Hibernate.
   - Note: Data will be reset on application restart. Can be trivially swapped to PostgreSQL/MySQL via adapting `application.properties`.

## Tech Stack
- **Framework**: Spring Boot 3.2
- **Language**: Java 17
- **Database**: Embedded H2 Database (with H2 console integration)
- **Security**: Spring Security 6 & JJWT (0.11)
- **Data Access**: Spring Data JPA / Hibernate
- **Boilerplate**: Lombok

## Getting Started

### 1) Run the Application

Navigate into the `finance-dashboard-api` directory via terminal and execute:

**Windows**:
```shell
.\mvnw.cmd spring-boot:run
```
**Mac / Linux**:
```shell
./mvnw spring-boot:run
```

Ensure port `8080` is free. The application should start in seconds and dynamically populate user roles to database.

### 2) Database Interface (H2 Console)

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:financedb`
- **Username**: `sa`
- **Password**: *(Leave blank)*

### 3) API Documentation & Usage

#### Auth Flow
- **POST** `/api/auth/signup`
  Registers a user. Send Body:
  ```json
  {
      "username": "adminUser",
      "email": "admin@example.com",
      "password": "password123",
      "roles": ["admin"] 
  }
  ```
  *(Other roles: "analyst", "viewer" or empty string for default viewer)*

- **POST** `/api/auth/signin`
  Logs in a user. Returns a JSON Web Token. Add this token to the Headers of all your requests (`Authorization: Bearer <token>`).

#### Records API (Needs Bearer Token)
- **GET** `/api/records` (Requires any role)
  Fetches all records.
- **POST** `/api/records` (Requires `ADMIN` role)
  Creates a financial record. 
  ```json
  {
      "amount": 150.00,
      "type": "EXPENSE",
      "category": "Groceries",
      "date": "2024-04-05",
      "notes": "Weekly supplies"
  }
  ```
- **PUT** `/api/records/{id}` (Requires `ADMIN` role)
  Updates an existing record.
- **DELETE** `/api/records/{id}` (Requires `ADMIN` role)
  Deletes an existing record.

#### Dashboard API (Needs Bearer Token)
- **GET** `/api/dashboard/summary` (Requires `ANALYST` or `ADMIN` role)
  Returns aggregated insights including `netBalance` and `categoryWiseTotals`.

## Design Decisions
- **Architecture**: A stateless token-based approach prevents traditional session hijack weaknesses and effortlessly maps to modern decoupled frontend interactions (Such as Angular, React).
- **Separation of Concerns**: Using DTOs (`Payload/Request` & `Payload/Response`) mitigates unintentional data leakage commonly introduced by mapping database entities straight to an HTTP Response.
