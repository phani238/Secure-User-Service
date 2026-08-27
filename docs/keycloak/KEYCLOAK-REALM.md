# Keycloak Realm Guide

Realm: `secure-user-realm`

## Export

```bash
kc export --realm secure-user-realm --file secure-user-realm.json --users same_file
```

## Import

```bash
kc import --file secure-user-realm.json
```

## Clients

- secure-user-console
- secure-admin-console

## JWT

Spring Boot only stores:

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/secure-user-realm
```

The application validates JWTs using Keycloak's public keys.