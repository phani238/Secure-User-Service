# Secure User Service

A hands-on Spring Boot project to learn and implement enterprise Java technologies step by step.

## Learning Roadmap

| Stage | Feature | Status |
|---|---|---|
| Stage 1 | REST CRUD + H2 | ✅ Complete |
| Stage 2 | DTO + Validation + Exception Handling | ✅ Complete |
| Stage 3 | OAuth2 + JWT | ⏳ Upcoming |
| Stage 4 | Global Logging + Correlation ID | ⏳ Upcoming |
| Stage 5 | Actuator + Metrics | ⏳ Upcoming |
| Stage 6 | Docker | ⏳ Upcoming |
| Stage 7 | Kafka | ⏳ Upcoming |
| Stage 8 | Apache Camel | ⏳ Upcoming |
| Stage 9 | CXF + SOAP | ⏳ Upcoming |
| Stage 10 | AWS | ⏳ Upcoming |
| Stage 11 | Kubernetes | ⏳ Upcoming |
| Stage 12 | CI/CD | ⏳ Upcoming |

## Stage 1 — REST CRUD + H2

### Implemented

- Spring Boot REST API
- User CRUD operations
- Spring Data JPA
- Hibernate
- H2 in-memory database
- Spring Security baseline
- Postman API testing

### Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users` | Create user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

## Stage 2 — DTO + Validation + Exception Handling

### Implemented

- Request DTO (`UserRequest`)
- Response DTO (`UserResponse`)
- Bean validation using `@Valid`
- `@NotBlank` validation
- `@Email` validation
- Custom `UserNotFoundException`
- Global exception handling using `@RestControllerAdvice`
- Centralized validation error handling
- Proper `400 Bad Request` responses
- Proper `404 Not Found` responses
- Handling non-existing users during GET, PUT and DELETE

### Example Error Responses

#### Validation Error

```json
{
  "status": 400,
  "message": "email: Email must be valid"
}