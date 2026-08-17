package com.aiengineeringpatterns.controller;

import com.aiengineeringpatterns.dto.TransactionEvent;
import com.aiengineeringpatterns.service.TransactionEventProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionEventProducer producer;

    public TransactionController(TransactionEventProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String publish(@RequestBody TransactionEvent event) {
        producer.publish(event);
        return "Transaction event published";
    }
}
