# User Management Service

Part of the **Order Management System (OMS)** — FNB B4 End-to-End Bootcamp Simulation.

Handles customer registration, authentication, and JWT issuance for all downstream services.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.3.5 |
| Security | Spring Security + JWT (jjwt 0.12.6) |
| Database | MySQL (`user_db`) |
| ORM | Spring Data JPA / Hibernate |
| Utilities | Lombok 1.18.34 |
| Java | 21 |

---

## Database Schema

### `users`
| Column | Type | Constraint |
|---|---|---|
| id | BIGINT | Primary key, auto-generated |
| first_name | VARCHAR(100) | Not null |
| surname | VARCHAR(100) | Not null |
| email | VARCHAR(150) | Not null, unique |
| role | ENUM('CUSTOMER','ADMIN') | Not null, default CUSTOMER |

### `user_credentials`
| Column | Type | Constraint |
|---|---|---|
| id | BIGINT | Primary key, auto-generated |
| user_id | BIGINT | Foreign key → users.id, unique |
| password | VARCHAR(255) | Not null (BCrypt) |

---

## API Endpoints

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register a new customer |
| POST | `/api/auth/login` | Public | Login and receive a JWT token |

### Register — `POST /api/auth/register`

**Request body:**
```json
{
  "firstName": "John",
  "surname": "Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Response (201):**
```json
{
  "id": 1,
  "firstName": "John",
  "surname": "Doe",
  "email": "john@example.com",
  "role": "CUSTOMER"
}
```

### Login — `POST /api/auth/login`

**Request body:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## JWT Token

The issued token is signed with a shared HS256 secret and carries the following claims:

| Claim | Value |
|---|---|
| `sub` | Customer email |
| `customerId` | Customer's database ID |
| `role` | `CUSTOMER` or `ADMIN` |
| `exp` | 24 hours from issue |

All other services in the OMS use the same signing secret to validate tokens locally without calling back to this service.

---

## Running Locally

**Prerequisites:** Java 21, MySQL running on port 3306

1. Create the database:
```sql
CREATE DATABASE user_db;
```

2. Set Java 21 (if your default is different):
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21.0.12"
```

3. Start the service:
```powershell
.\mvnw spring-boot:run
```

Service starts on **port 8081**.

---

## Configuration

`src/main/resources/application.yaml`

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/user_db
    username: root
    password: 12345

jwt:
  secret: 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
```

> The JWT secret must match across all three OMS services.
