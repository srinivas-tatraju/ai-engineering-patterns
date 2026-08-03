package com.ai.mcpserver.config;


import com.ai.mcpserver.tool.CalculatorTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Bean
    ToolCallbackProvider toolCallbackProvider(CalculatorTool calculatorTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(calculatorTool)
                .build();
    }
}