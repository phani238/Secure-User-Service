# Stage 6 – Kafka & WebSocket

## Objective

Implement event-driven communication using Apache Kafka and deliver real-time updates through WebSocket.

## Architecture

Postman
    │
    ▼
Spring Boot Controller
    │
    ▼
Kafka Producer
    │
    ▼
Kafka Topic
    │
    ▼
Kafka Consumer
    │
    ▼
WebSocket
    │
    ▼
Live Browser Update

## Planned Components

| Component | Purpose |
|-----------|---------|
| UserEvent | Event payload |
| KafkaConfig | Kafka configuration |
| KafkaProducerService | Publish events |
| KafkaConsumerService | Consume events |
| WebSocketConfig | WebSocket setup |
| NotificationController | Broadcast updates |

## Acceptance Criteria

- Kafka broker running.
- Event published successfully.
- Consumer receives the event.
- WebSocket broadcasts in real time.
- Correlation ID appears in logs.