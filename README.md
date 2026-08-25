# Secure User Service

A production-style **Spring Boot** backend project built step by step to learn enterprise Java development using real-world architecture, security, logging, monitoring, and asynchronous processing.

> **Current Milestone:** Stage 5 Complete • Cross-platform verified on **Windows** and **macOS (Intel)**.

---

## Tech Stack

| Category          | Technology                             |
| ----------------- | -------------------------------------- |
| Language          | Java 21                                |
| Framework         | Spring Boot 4                          |
| Security          | Spring Security OAuth2 Resource Server |
| Identity Provider | Keycloak                               |
| Build Tool        | Maven                                  |
| Database          | H2                                     |
| Documentation     | Swagger / OpenAPI                      |
| Monitoring        | Spring Boot Actuator + Prometheus      |
| Logging           | Logback + MDC Correlation ID           |
| Container         | Docker Desktop                         |
| Messaging         | Kafka *(Stage 6)*                      |
| Real-time         | WebSocket *(Stage 6)*                  |

---

## Learning Roadmap

| Stage    | Feature                               | Status     |
| -------- | ------------------------------------- | ---------- |
| Stage 1  | REST CRUD + H2                        | ✅ Complete |
| Stage 2  | DTO + Validation + Exception Handling | ✅ Complete |
| Stage 3  | OAuth2 + JWT + Keycloak               | ✅ Complete |
| Stage 4  | Logging + Correlation ID              | ✅ Complete |
| Stage 5  | Actuator + Metrics                    | ✅ Complete |
| Stage 6  | Kafka + WebSocket Notifications       | 🔄 In Progress |
| Stage 7  | Docker                                | ⏳ Planned  |
| Stage 8  | Apache Camel                          | ⏳ Planned  |
| Stage 9  | CXF + SOAP                            | ⏳ Planned  |
| Stage 10 | AWS                                   | ⏳ Planned  |
| Stage 11 | Kubernetes                            | ⏳ Planned  |
| Stage 12 | CI/CD                                 | ⏳ Planned  |

---

# Project Architecture

## Current Architecture (Stage 5)

```text
                Keycloak
                   │
             JWT Access Token
                   │
                   ▼
        Spring Boot Resource Server
                   │
        Spring Security Filters
                   │
        User REST APIs
                   │
      H2 Database + Logging + Actuator
```

## Upcoming Architecture (Stage 6)

```text
Frontend
    │
    ▼
REST API
    │
202 Accepted + Transaction ID
    │
    ▼
Kafka
    │
    ▼
Background Processor
    │
    ▼
Database Status Update
    │
    ▼
WebSocket Notification
    │
    ▼
Frontend receives live updates
```

---

# Stage 1 – REST CRUD + H2

## Implemented

* Spring Boot REST API
* User CRUD operations
* Spring Data JPA
* Hibernate
* H2 in-memory database
* Spring Security baseline
* Postman testing

### Endpoints

| Method | Endpoint          | Description    |
| ------ | ----------------- | -------------- |
| POST   | `/api/users`      | Create user    |
| GET    | `/api/users`      | Get all users  |
| GET    | `/api/users/{id}` | Get user by ID |
| PUT    | `/api/users/{id}` | Update user    |
| DELETE | `/api/users/{id}` | Delete user    |

---

# Stage 2 – DTO + Validation

## Implemented

* Request & Response DTOs
* Bean Validation (`@Valid`)
* `@NotBlank`
* `@Email`
* Custom `UserNotFoundException`
* Global exception handling
* Standardized `400` and `404` responses

### Example Validation Error

```json
{
  "status": 400,
  "message": "email: Email must be valid"
}
```

---

# Stage 3 – OAuth2 + JWT + Keycloak

## Implemented

* Local Keycloak setup
* `secure-user-realm`
* OIDC client configuration
* USER and ADMIN roles
* JWT authentication
* Spring Boot Resource Server
* Role-based authorization
* 401 vs 403 handling
* Swagger & Postman security testing

### Security Flow

```text
User
 │
 ▼
Keycloak Login
 │
 ▼
JWT Token
 │
 ▼
Spring Security
 │
 ▼
Protected APIs
```

### Concepts Learned

* OAuth2
* OpenID Connect
* Resource Server
* Client ID & Client Secret
* JWT
* Authentication vs Authorization
* Roles & Scopes

---

# Stage 4 – Logging & Correlation ID

## Implemented

* Lombok `@Slf4j`
* Logback configuration
* Centralized request logging
* MDC Correlation ID
* `X-Correlation-ID` support
* Error-aware logging
* Request/response tracing

### Concepts Learned

* MDC
* Correlation IDs
* Logging Levels
* Centralized HTTP logging

---

# Stage 5 – Actuator & Monitoring

## Implemented

* Spring Boot Actuator
* Health endpoint
* Metrics endpoint
* Prometheus endpoint
* Public health endpoint
* ADMIN-only management endpoints
* Browser & API verification

### Concepts Learned

* Health checks
* Application metrics
* Prometheus integration
* Protected management endpoints

---

# Testing

## Completed

* JUnit 5
* Mockito
* MockMvc
* `@WebMvcTest`
* `@SpringBootTest`
* Security testing
* CSRF testing
* Actuator testing

### Current Test Checkpoint

| Area               | Result    |
| ------------------ | --------- |
| Service Tests      | 8/8       |
| Controller Tests   | 11/11     |
| API Security Tests | 8/8       |
| Actuator Tests     | 3/3       |
| **Total**          | **30/30** |

---

# Cross-Platform Verification

This project has been successfully verified on both environments.

| Environment   | Status     |
| ------------- | ---------- |
| Windows       | ✅ Verified |
| macOS (Intel) | ✅ Verified |

### macOS Fixes Verified

* Java 21 configuration
* Maven configuration
* Lombok integration with STS
* Docker Desktop
* Keycloak 26.7.2
* Swagger
* JWT authentication
* Postman testing

---

# Documentation

| Guide                             | Purpose                 |
| --------------------------------- | ----------------------- |
| `docs/setup/MACOS.md`             | macOS setup             |
| `docs/setup/WINDOWS.md`           | Windows setup           |
| `docs/keycloak/KEYCLOAK-REALM.md` | Realm export/import     |
| `docs/stages/STAGE-05.md`         | Stage 5 summary         |
| `docs/runbook/`                   | Detailed stage runbooks |

---

# Quick Start

## Prerequisites

* Java 21
* Maven 3.9+
* Docker Desktop
* Keycloak 26.7.2

## Verify Installation

```bash
java --version
javac --version
mvn --version
docker --version
```

## Run

```bash
mvn clean spring-boot:run
```

## Open

| Service     | URL                                         |
| ----------- | ------------------------------------------- |
| Application | http://localhost:8081                       |
| Swagger     | http://localhost:8081/swagger-ui/index.html |
| Keycloak    | http://localhost:8080                       |

---

# What's Next

The next milestone introduces asynchronous processing using Kafka and WebSockets.

### Stage 6 Goals

* Asynchronous transactions
* `202 Accepted` workflow
* Kafka producer
* Kafka consumer
* Transaction status tracking
* WebSocket live notifications

This transforms the project from a traditional CRUD application into an event-driven backend architecture suitable for real-world enterprise systems.
