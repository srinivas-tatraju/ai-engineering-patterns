# AI Engineering Patterns

A production-style collection of AI engineering patterns built with **Java 21**, **Spring Boot 4**, **Spring AI**, and **Large Language Models (LLMs)**.

The goal of this repository is to demonstrate modern AI application development using enterprise-grade architectural practices instead of simple chatbot examples.

---

## Repository Roadmap

| Branch | Status | Description |
|---------|--------|-------------|
| ✅ 01-ai-assistant | Completed | AI Chat Assistant using Spring AI + Ollama |
| ✅ 02-tool-calling | Completed | AI Tool Calling with Spring AI |
| ⏳ 03-model-context-protocol | Planned | MCP Client & Server |
| ⏳ 04-ai-recommendation-engine | Planned | Business Recommendation Engine |
| ⏳ 05-ai-workflows | Planned | Multi-step AI Workflows |
| ⏳ 06-event-driven-ai | Planned | Kafka + AI Integration |
| ⏳ 07-rag | Planned | Retrieval Augmented Generation |
| ⏳ 08-ai-agents | Planned | Autonomous AI Agents |

---

# Branch 02 - AI Tool Calling

This branch demonstrates how a Large Language Model can invoke Java methods using **Spring AI Tool Calling**.

Instead of relying solely on the LLM's internal reasoning, the model can intelligently decide when to execute deterministic business logic implemented in Java.

---

## Features

- Spring AI Tool Calling
- Calculator Tool
- Layered Architecture
- ChatClient Configuration
- Global Exception Handling
- Logging with Lombok
- Externalized System Prompt
- Dockerized Ollama Integration

---

## Tech Stack

- Java 21
- Spring Boot 4.x
- Spring AI
- Ollama
- Llama 3.1
- Maven
- Lombok

---

# Architecture

```
                User
                  │
                  ▼
          ChatController
                  │
                  ▼
            ChatService
                  │
                  ▼
             ChatClient
                  │
                  ▼
         Spring AI Tool Calling
                  │
                  ▼
          CalculatorTool (@Tool)
                  │
                  ▼
         CalculatorService
                  │
                  ▼
         Business Logic Result
                  │
                  ▼
             AI Response
```

---

# Project Structure

```
src
└── main
    ├── java
    │   └── com.aiengineeringpatterns
    │       ├── config
    │       ├── controller
    │       ├── dto
    │       ├── exception
    │       ├── service
    │       │   └── impl
    │       └── tool
    └── resources
        └── prompts
```

---

# Example Requests

### Chat

```
What is 245 + 178?
```

Response

```
423
```

---

```
Multiply 45 by 12
```

Response

```
540
```

---

# How Tool Calling Works

1. User sends a natural language prompt.
2. Spring AI forwards the prompt to the LLM.
3. The LLM determines that a tool is required.
4. Spring AI invokes the appropriate Java method.
5. The tool executes deterministic business logic.
6. The result is returned to the LLM.
7. The LLM generates a natural language response.

---

# Key Classes

```
ChatController
ChatService
ChatServiceImpl
AiConfig

CalculatorTool
CalculatorService
CalculatorServiceImpl
```

---

# Design Decisions

- Layered Architecture
- One Tool per Business Capability
- Business Logic isolated inside Services
- Stateless Spring Beans
- Constructor Injection
- Externalized System Prompt
- Global Exception Handling

---

# Learning Outcomes

After completing this branch, you will understand:

- Spring AI Tool Calling
- @Tool annotation
- ChatClient.defaultTools(...)
- AI invoking Java methods
- Separating AI orchestration from business logic
- Enterprise project structure

---

# Running the Application

Start Ollama

```bash
docker compose up -d
```

Run Spring Boot

```bash
mvn spring-boot:run
```

Example Request

```
POST /api/v1/chat
```

```json
{
  "message": "What is 245 + 178?"
}
```

---

# Next Branch

## Branch 03 - Model Context Protocol (MCP)

In the next branch, the AI application will communicate with external tools and services using the **Model Context Protocol (MCP)**, enabling standardized integration beyond in-process Java methods.

---

## License

MIT License