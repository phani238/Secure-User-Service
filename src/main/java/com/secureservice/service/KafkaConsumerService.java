package com.secureservice.service;

import com.secureservice.event.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

	@KafkaListener(topics = "user-events", groupId = "secure-user-group")
	public void consume(UserEvent event) {

		log.info("Kafka Event Received: {}", event);

		log.info("User Created -> id={}, name={}, email={}", event.getUserId(), event.getName(), event.getEmail());
	}
}