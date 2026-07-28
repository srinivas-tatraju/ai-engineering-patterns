package com.aiengineeringpatterns.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(

        @NotBlank(message = "Message cannot be blank")
        String message

) {
}
