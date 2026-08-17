package com.aiengineeringpatterns.service;

import com.aiengineeringpatterns.dto.TransactionEvent;
import com.aiengineeringpatterns.dto.TransactionRiskDecision;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TransactionRiskAnalyzer {

    private final ChatClient chatClient;

    public TransactionRiskAnalyzer(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public TransactionRiskDecision analyze(TransactionEvent event) {

        return chatClient.prompt()
                .user("""
                        Analyze the following transaction for risk.

                        Transaction ID: %s
                        Customer ID: %s
                        Amount: %.2f
                        Category: %s

                        Classify the transaction as LOW, MEDIUM, or HIGH risk.

                        Return:
                        - riskLevel
                        - reason

                        Keep the reason short.
                        """.formatted(
                        event.transactionId(),
                        event.customerId(),
                        event.amount(),
                        event.category()
                ))
                .call()
                .entity(TransactionRiskDecision.class);
    }
}
