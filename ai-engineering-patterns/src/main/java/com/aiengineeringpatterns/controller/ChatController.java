package com.aiengineeringpatterns.controller;

import com.aiengineeringpatterns.dto.ChatRequest;
import com.aiengineeringpatterns.dto.ChatResponse;
import com.aiengineeringpatterns.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
@Tag(name = "AI Chat", description = "AI Assistant APIs")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "Chat with AI Assistant")
    @PostMapping
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
        String response = chatService.chat(request.message());
        return new ChatResponse(response);
    }
}
