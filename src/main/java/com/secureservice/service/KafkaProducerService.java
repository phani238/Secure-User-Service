package com.secureservice.service;

import com.secureservice.event.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

	private static final String TOPIC = "user-events";

	private final KafkaTemplate<String, UserEvent> kafkaTemplate;

	public void publishUserCreatedEvent(UserEvent event) {

		log.info("Publishing USER_CREATED event for userId={}", event.getUserId());

		kafkaTemplate.send(TOPIC, event.getUserId().toString(), event);
	}
}