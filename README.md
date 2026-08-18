# Secure User Service

A hands-on Spring Boot project to learn and implement enterprise Java
technologies step by step.

## Learning Roadmap

  ------------------------------------------------------------------------
  Stage                   Feature                  Status
  ----------------------- ------------------------ -----------------------
  Stage 1                 REST CRUD + H2           ✅ Complete

  Stage 2                 DTO + Validation +       ✅ Complete
                          Exception Handling       

  Stage 3                 OAuth2 + JWT + Keycloak  ✅ Complete

  Stage 4                 Global Logging +         ✅ Complete
                          Correlation ID           

  Stage 5                 Actuator + Metrics       ✅ Complete

  Stage 6                 Asynchronous             ⏳ Upcoming
                          Transaction + Kafka +    
                          Notification/WebSocket   

  Stage 7                 Docker                   ⏳ Upcoming

  Stage 8                 Apache Camel             ⏳ Upcoming

  Stage 9                 CXF + SOAP               ⏳ Upcoming

  Stage 10                AWS                      ⏳ Upcoming

  Stage 11                Kubernetes               ⏳ Upcoming

  Stage 12                CI/CD                    ⏳ Upcoming
  ------------------------------------------------------------------------

## Stage 1 --- REST CRUD + H2

### Implemented

-   Spring Boot REST API
-   User CRUD operations
-   Spring Data JPA
-   Hibernate
-   H2 in-memory database
-   Spring Security baseline
-   Postman API testing

### Endpoints

  Method   Endpoint            Description
  -------- ------------------- ----------------
  POST     `/api/users`        Create user
  GET      `/api/users`        Get all users
  GET      `/api/users/{id}`   Get user by ID
  PUT      `/api/users/{id}`   Update user
  DELETE   `/api/users/{id}`   Delete user

## Stage 2 --- DTO + Validation + Exception Handling

### Implemented

-   Request DTO (`UserRequest`)
-   Response DTO (`UserResponse`)
-   Bean validation using `@Valid`
-   `@NotBlank` validation
-   `@Email` validation
-   Custom `UserNotFoundException`
-   Global exception handling using `@RestControllerAdvice`
-   Centralized validation error handling
-   Proper `400 Bad Request` responses
-   Proper `404 Not Found` responses
-   Handling non-existing users during GET, PUT and DELETE

### Example Error Responses

#### Validation Error

``` json
{
  "status": 400,
  "message": "email: Email must be valid"
}
```

## Stage 3 --- OAuth2 + JWT + Keycloak

### Status

✅ Complete

### Architecture

``` text
Keycloak
    ↓
JWT Access Token
    ↓
Spring Boot Resource Server
    ↓
Spring Security
    ↓
REST APIs
```

### Implemented

-   Keycloak local setup
-   `secure-user-realm`
-   OIDC client
-   USER and ADMIN realm roles
-   JWT access token authentication
-   Spring Boot OAuth2 Resource Server
-   Keycloak JWT role conversion
-   `ROLE_USER` / `ROLE_ADMIN`
-   Role-based CRUD authorization
-   401 Unauthorized handling
-   403 Forbidden handling
-   Postman security testing
-   Stage 1--3 runbooks

### Concepts learned

-   OAuth2
-   Authorization Server
-   Resource Server
-   OpenID Connect
-   Client
-   Client ID
-   Client Secret
-   Access Token
-   JWT
-   Authentication
-   Authorization
-   Roles
-   Scopes
-   401 vs 403

## Stage 4 --- Logging & Request Correlation

### Implemented

-   Added Lombok and `@Slf4j` for application logging
-   Configured Logback logging
-   Added DEBUG, INFO, WARN and ERROR logging
-   Added centralized HTTP request/response logging
-   Added correlation ID support using MDC
-   Supports incoming `X-Correlation-ID`
-   Returns `X-Correlation-ID` in API responses
-   Added error-aware HTTP logging
-   Verified 2xx, 4xx and authentication failure scenarios

### Concepts learned

-   MDC
-   Correlation ID
-   Logback
-   Request/Response Logging
-   Logging Levels
-   Centralized HTTP Logging

## Stage 5 --- Actuator + Metrics

### Status

✅ Complete

### Implemented

-   Spring Boot Actuator
-   Health endpoint
-   Metrics endpoint
-   Prometheus metrics endpoint
-   Actuator security rules
-   Public `/actuator/health/**`
-   ADMIN-only `/actuator/**`
-   Verified Actuator endpoints through browser/API testing
-   Added Actuator security tests

### Concepts learned

-   Spring Boot Actuator
-   Health checks
-   Application metrics
-   Prometheus
-   Monitoring endpoints
-   Public vs protected management endpoints

## Testing --- JUnit 5 + Mockito + MockMvc

### Status

✅ Complete

### Implemented

-   Service unit tests using JUnit 5 and Mockito
-   Controller tests using `@WebMvcTest`
-   `MockMvc` request/response testing
-   DTO validation tests
-   Global exception handling tests
-   Mockito `verify()` and `never()` verification
-   Spring Security authorization tests
-   USER vs ADMIN role testing
-   Authentication (`401`) and authorization (`403`) testing
-   CSRF testing for state-changing requests
-   Full application-context tests using `@SpringBootTest`
-   Actuator security tests

### Current Test Coverage Checkpoint

``` text
Service Tests              8/8   ✅
Controller Tests          11/11  ✅
API Security Tests          8/8   ✅
Actuator Security Tests     3/3   ✅
------------------------------------
Total                      30/30  ✅
```

### Testing Concepts Learned

-   Unit testing vs MVC testing
-   JUnit 5
-   Mockito
-   MockMvc
-   `@WebMvcTest`
-   `@SpringBootTest`
-   `@MockitoBean`
-   `@Import(SecurityConfig.class)`
-   Mocking service/repository dependencies
-   Authentication vs authorization testing
-   Security role simulation
-   CSRF handling in tests
-   Testing public and protected Actuator endpoints
-   Avoiding false-positive tests by verifying the actual endpoint/test
    context

## Stage 6 --- Asynchronous Transaction + Kafka + Notification/WebSocket

### Status

⏳ Upcoming

### Planned

-   Asynchronous transaction processing
-   Return `202 Accepted` with a transaction/job ID
-   Transaction status tracking (`IN_PROGRESS`, `COMPLETED`, `FAILED`)
-   Message queue / Kafka integration
-   Background transaction processing
-   Notification mechanism
-   WebSocket real-time status updates
-   Handling user logout/disconnection while processing continues
-   Tests for asynchronous processing and notifications

### Target Architecture

``` text
Frontend
    ↓
REST API
    ↓
202 Accepted + transactionId
    ↓
Message Queue / Kafka
    ↓
Transaction Processor
    ↓
Transaction Status → Database
    ↓
Notification
    ↓
WebSocket
    ↓
Frontend receives real-time status
```

## Future Roadmap

After the asynchronous transaction milestone:

-   Docker
-   Apache Camel
-   CXF + SOAP
-   AWS
-   Kubernetes
-   CI/CD
