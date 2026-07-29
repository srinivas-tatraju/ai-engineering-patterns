package com.aiengineeringpatterns.config;

import com.aiengineeringpatterns.tool.CalculatorTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder,
                          CalculatorTool calculatorTool) {
        return builder
                .defaultTools(calculatorTool)
                .build();
    }
}
