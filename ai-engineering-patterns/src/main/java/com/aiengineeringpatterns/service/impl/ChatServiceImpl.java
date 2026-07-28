package com.aiengineeringpatterns.service.impl;

import com.aiengineeringpatterns.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    @Override
    public String chat(String message) {

        log.info("Received prompt: {}", message);

        String response = chatClient
                .prompt(message)
                .call()
                .content();

        log.info("Generated response successfully.");

        return response;
    }
}
