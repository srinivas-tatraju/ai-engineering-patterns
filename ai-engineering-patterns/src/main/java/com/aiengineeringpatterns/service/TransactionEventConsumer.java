package com.aiengineeringpatterns.service;

import com.aiengineeringpatterns.dto.TransactionEvent;
import com.aiengineeringpatterns.dto.TransactionRiskDecision;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionEventConsumer {

    private final TransactionRiskAnalyzer riskAnalyzer;

    private final TransactionRiskEventProducer riskEventProducer;

    @KafkaListener(topics = "transaction-events", groupId = "ai-risk-consumer"
    )
    public void consume(TransactionEvent event) {
        TransactionRiskDecision decision = riskAnalyzer.analyze(event);
        riskEventProducer.publish(decision);
    }
}