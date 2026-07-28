# AI Engineering Patterns

A production-oriented repository demonstrating modern AI engineering concepts using **Java 21**, **Spring Boot**, **Spring AI**, and **Large Language Models (LLMs)**.

This repository is organized as a series of branches, where each branch introduces a new AI engineering pattern and builds upon the previous one.

---

## Repository Roadmap

| Branch | Topic | Status |
|---------|-------|--------|
| 01 | AI Assistant | ✅ Completed |
| 02 | Tool Calling | ⏳ Planned |
| 03 | Model Context Protocol (MCP) | ⏳ Planned |
| 04 | AI Recommendation Engine | ⏳ Planned |
| 05 | AI Workflows | ⏳ Planned |
| 06 | Event-Driven AI (Kafka) | ⏳ Planned |
| 07 | Retrieval-Augmented Generation (RAG) | ⏳ Planned |
| 08 | AI Agents | ⏳ Planned |

---

# Branch 01 - AI Assistant

This branch implements a simple AI-powered conversational assistant using Spring AI and a locally hosted LLM.

The application exposes a REST API that accepts a user prompt and returns an AI-generated response.

---

## Features

- Spring Boot 4.1
- Java 21
- Spring AI
- Ollama Integration
- Local LLM Execution
- REST API
- Request Validation
- Global Exception Handling
- Logging
- Docker Support

---

## Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 4.1 |
| Spring AI | 2.x |
| Ollama | Latest |
| Maven | Latest |
| Docker | Latest |

---

## Architecture

```
                Client
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
              Spring AI
                   │
                   ▼
                Ollama
                   │
                   ▼
             Local LLM
```

---

## Project Structure

```
src
├── controller
├── service
│   └── impl
├── dto
├── config
├── exception
└── resources
    └── prompts
```

---

## API

### Chat with AI

**POST**

```
/api/v1/chat
```

Request

```json
{
  "message": "Explain Kafka in simple terms."
}
```

Response

```json
{
  "response": "Kafka is a distributed event streaming platform..."
}
```

---

## Running the Application

### 1. Start Ollama

```bash
docker compose up -d
```

### 2. Verify Ollama

```bash
docker ps
```

### 3. Run Spring Boot

```bash
mvn spring-boot:run
```

---

## Design Decisions

- Uses Spring AI instead of directly calling LLM APIs.
- Runs a local LLM using Ollama to avoid external API dependencies.
- Separates Controller, Service, and Configuration layers.
- Uses immutable Java Records for DTOs.
- Uses global exception handling for consistent API responses.

---

## Current Limitations

This branch intentionally focuses on a basic AI assistant.

Not included:

- Conversation Memory
- Tool Calling
- Streaming Responses
- RAG
- AI Agents
- Vector Database
- Authentication

These topics will be introduced in subsequent branches.

---

## Next Branch

**Branch 02 - Tool Calling**

Topics:

- Function Calling
- Java Tool Integration
- Multi-tool Execution
- AI Decision Making
- Structured Responses

---

## Learning Outcomes

After completing this branch, you will understand:

- Spring AI fundamentals
- Integrating a local LLM
- Building AI-enabled REST APIs
- Basic prompt execution
- Clean architecture for AI applications

---

## License

MIT