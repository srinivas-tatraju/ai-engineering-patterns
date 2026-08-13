package com.aiengineeringpatterns.workflows;

import com.aiengineeringpatterns.dto.Itinerary;
import com.aiengineeringpatterns.dto.TravelRequirements;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ItineraryGenerator {

    private final ChatClient chatClient;

    public ItineraryGenerator(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Itinerary generate(TravelRequirements requirements) {

        return chatClient.prompt()
                .user("""
                Create a travel itinerary.

                Destination: %s
                Number of days: %d
                Budget: %.2f
                Preferences: %s

                Return only structured itinerary data.

                Create exactly one daily plan for each day.
                Do not generate code.
                Do not generate explanations outside the itinerary.
                """.formatted(
                        requirements.destination(),
                        requirements.numberOfDays(),
                        requirements.budget(),
                        requirements.preferences()
                ))
                .call()
                .entity(Itinerary.class);
    }
}