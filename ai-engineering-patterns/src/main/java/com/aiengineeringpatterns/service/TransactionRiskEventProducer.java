package com.aiengineeringpatterns.service;

import com.aiengineeringpatterns.dto.TransactionRiskDecision;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionRiskEventProducer {

    private static final String TOPIC = "transaction-risk-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransactionRiskEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(TransactionRiskDecision decision) {
        kafkaTemplate.send(TOPIC, decision.transactionId(), decision);
    }
}