package com.secureservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.secureservice.event.UserEvent;

@Service
public class AuditConsumerService {

    private static final Logger log = LoggerFactory.getLogger(AuditConsumerService.class);

    @KafkaListener(
            topics = "user-events",
            groupId = "audit-group")
    public void audit(UserEvent event) {

        log.info("AUDIT -> Event={} UserId={} Name={} Email={}",
                event.getEventType(),
                event.getUserId(),
                event.getName(),
                event.getEmail());
    }
}