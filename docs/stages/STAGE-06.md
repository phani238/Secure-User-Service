# Stage 6 – Kafka Event-Driven Architecture

## Objective

Introduce asynchronous communication using Apache Kafka while keeping the User Service loosely coupled from downstream services.

---

# What We Built

- Kafka Producer
- Kafka Consumer
- Audit Consumer
- UserEvent model
- Kafka Topic (`user-events`)
- Multiple Consumer Groups
- End-to-end verification using Postman and Kafka Console

---

# Architecture

```text
Client
   │
POST /api/users
   │
   ▼
UserService
   │
Save User (H2)
   │
   ▼
Publish USER_CREATED Event
   │
   ▼
Kafka Topic (user-events)
   ├──────────────► KafkaConsumerService
   └──────────────► AuditConsumerService
```

---

# Why We Created UserEvent

Instead of sending the JPA `User` entity directly, we created a separate `UserEvent`.

Reason:

- Database structure may change.
- Kafka carries business events.
- Downstream services remain unaffected by database changes.

Example:

```json
{
  "userId":1,
  "name":"Audit User",
  "email":"audit@test.com",
  "eventType":"USER_CREATED"
}
```

---

# Components Added

## KafkaProducerService

Responsible for publishing events.

```text
User Created
      │
      ▼
Publish USER_CREATED
```

---

## KafkaConsumerService

Receives business events.

Example log:

```
Kafka Event Received...
User Created...
```

---

## AuditConsumerService

Independent consumer.

Example log:

```
AUDIT -> Event=USER_CREATED
```

Uses a different Consumer Group:

```
audit-group
```

---

# Consumer Groups

| Consumer | Group |
|----------|-------|
| KafkaConsumerService | secure-user-group |
| AuditConsumerService | audit-group |

Different groups receive independent copies of the same event.

---

# Kafka Commands Used

## Start Kafka

```bash
brew services start kafka
```

---

## Stop Kafka

```bash
brew services stop kafka
```

---

## Verify Kafka

```bash
brew services list | grep kafka
```

---

## List Topics

```bash
/usr/local/opt/kafka/bin/kafka-topics \
--bootstrap-server localhost:9092 \
--list
```

Expected:

```
user-events
```

---

## Watch Events

```bash
/usr/local/opt/kafka/bin/kafka-console-consumer \
--bootstrap-server localhost:9092 \
--topic user-events \
--from-beginning
```

---

# Testing Performed

## Smoke Test

- Kafka started
- Spring Boot started
- Keycloak authenticated
- User created
- Event published
- Consumer received
- Audit consumer received

Status:

- Producer ✅
- Consumer ✅
- Audit Consumer ✅

---

# Understanding the Logs

Example flow:

```
Creating new user...
Hibernate insert...
Publishing USER_CREATED...
HTTP 201 Created
AUDIT -> Event=USER_CREATED
Kafka Event Received...
```

Meaning:

1. Request entered.
2. Database saved.
3. Producer published.
4. REST returned.
5. Kafka delivered.
6. Consumers processed.

---

# What We Learned

| Concept | Understanding |
|----------|---------------|
| Producer | Sends events |
| Consumer | Processes events |
| Topic | Stores events |
| Consumer Group | Independent processing |
| Offset | Kafka message position |
| Event | Business action |

---

# Real Production Example

Customer registers.

Kafka publishes:

```
USER_CREATED
```

Different services react independently.

| Service | Action |
|----------|---------|
| Audit | Save audit record |
| Email | Send welcome email |
| SMS | Send notification |
| Analytics | Update dashboard |

User Service never calls them directly.

---

# Troubleshooting We Encountered

## Kafka Topic Missing

Error:

```
UNKNOWN_TOPIC_OR_PARTITION
```

Fix:

Trigger the application by creating the first user.

---

## KafkaListener Factory Missing

Fix:

Create `KafkaConfig`.

---

## Package Refactoring

Git showed renamed and modified files.

Fix:

```bash
git add -A
```

---

## Key Takeaways

- Kafka is not a replacement for logs.
- Logs help developers.
- Kafka helps applications communicate.
- Multiple consumers can process the same event independently.
- Keeping events separate from JPA entities creates a stable integration contract.

---

# Stage 6 Completion Checklist

| Task | Status |
|------|--------|
| Kafka Installed | ✅ |
| Kafka Producer | ✅ |
| Kafka Consumer | ✅ |
| Audit Consumer | ✅ |
| End-to-End Event Flow | ✅ |
| Multiple Consumer Groups | ✅ |
| JUnit + Mockito Verification | ✅ |
| Cross-Platform Verification | ✅ |
| Documentation Updated | ✅ |

## Final Outcome

Stage 6 completed the transition from a traditional CRUD application to an event-driven backend. The application now publishes business events through Kafka, allows multiple independent consumers to process the same event, and provides a foundation for future integrations such as Email, Notifications, and Analytics services.