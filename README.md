# AI Engineering Patterns

A production-style collection of AI Engineering patterns built using **Java 21**, **Spring Boot 4**, **Spring AI**, **Ollama**, and **Large Language Models (LLMs)**.

This repository demonstrates how to build enterprise-grade AI applications using modern architectural patterns instead of simple chatbot examples.

---

# Repository Roadmap

| Branch | Status | Description |
|---------|--------|-------------|
| ✅ 01-ai-assistant | Completed | AI Chat Assistant using Spring AI + Ollama |
| ✅ 02-tool-calling | Completed | AI Tool Calling with Spring AI |
| ✅ 03-model-context-protocol | Completed | Distributed AI using Model Context Protocol (MCP) |
| ⏳ 04-ai-recommendation-engine | Planned | AI-powered Business Recommendation Engine |
| ⏳ 05-ai-workflows | Planned | Multi-step AI Workflows |
| ⏳ 06-event-driven-ai | Planned | Kafka + AI Integration |
| ⏳ 07-rag | Planned | Retrieval Augmented Generation (RAG) |
| ⏳ 08-ai-agents | Planned | Autonomous AI Agents |

---

# Branch 03 - Model Context Protocol (MCP)

This branch demonstrates how an AI application can invoke **remote tools** using the **Model Context Protocol (MCP)**.

Unlike Branch 02, where tools execute inside the same Spring Boot application, this branch separates the AI Client and Tool Server into independent applications communicating over MCP.

---

# Projects

This repository contains two Spring Boot applications.

## 1. AI Engineering Patterns (MCP Client)

Responsibilities

- Accept user requests
- Communicate with Ollama
- Discover remote MCP tools
- Invoke remote tools
- Return AI responses

---

## 2. Calculator MCP Server

Responsibilities

- Expose Calculator tools
- Execute business logic
- Return deterministic results through MCP

---

# Architecture

```
                        User
                          │
                          ▼
                 AI Engineering Patterns
                     (MCP Client)
                          │
                          ▼
                     Spring AI
                          │
                          ▼
                        Ollama
                          │
             Tool Selection by LLM
                          │
                          ▼
                ToolCallbackProvider
                          │
                          ▼
              Model Context Protocol
                          │
                          ▼
              Calculator MCP Server
                          │
                          ▼
                  CalculatorTool
                          │
                          ▼
                 CalculatorService
                          │
                          ▼
                   Business Logic
                          │
                          ▼
                    AI Response
```

---

# Technology Stack

- Java 21
- Spring Boot 4
- Spring AI
- Model Context Protocol (MCP)
- Ollama
- Llama 3.1
- Maven
- Docker
- Lombok

---

# Project Structure

```
ai-engineering-patterns
│
├── ai-engineering-patterns
│   ├── config
│   ├── controller
│   ├── dto
│   ├── exception
│   ├── service
│   └── resources
│
├── calculator-mcp-server
│   ├── config
│   ├── service
│   ├── tool
│   └── resources
│
└── docker
```

---

# Features

## Branch 01

- AI Chat Assistant
- Spring AI
- Ollama Integration

---

## Branch 02

- AI Tool Calling
- Local Java Tool Execution
- Business Logic Separation

---

## Branch 03

- MCP Client
- MCP Server
- Remote Tool Discovery
- Remote Tool Invocation
- Distributed AI Architecture
- Dockerized Ollama

---

# Example Request

```
What is 245 + 178?
```

Response

```
The answer is 423.
```

---

# End-to-End Flow

```
User

↓

ChatController

↓

ChatService

↓

ChatClient

↓

Ollama

↓

MCP Client

↓

Calculator MCP Server

↓

CalculatorTool

↓

CalculatorService

↓

Result

↓

LLM

↓

Response
```

---

# Running the Applications

## Step 1

Start Ollama

```bash
cd docker

docker compose up -d
```

---

## Step 2

Download model (first time only)

```bash
docker exec -it ollama ollama pull llama3.1
```

---

## Step 3

Start Calculator MCP Server

```bash
cd calculator-mcp-server

mvn spring-boot:run
```

Runs on

```
http://localhost:8081
```

---

## Step 4

Start AI Engineering Patterns

```bash
cd ai-engineering-patterns

mvn spring-boot:run
```

Runs on

```
http://localhost:8080
```

---

# Sample API

```
POST /api/v1/chat
```

Request

```json
{
  "message": "What is 245 + 178?"
}
```

---

# Key Design Decisions

- Layered Architecture
- Distributed AI Architecture
- AI Client independent of Tool Implementation
- Business Logic isolated inside MCP Server
- Dockerized Local AI Runtime
- Constructor Injection
- Externalized Prompts
- Global Exception Handling

---

# Learning Outcomes

After completing this repository, you will understand:

- Spring AI
- ChatClient
- Prompt Engineering
- Tool Calling
- Model Context Protocol (MCP)
- Remote Tool Invocation
- Distributed AI Architecture
- Enterprise AI Application Design

---

# Future Enhancements

- AI Recommendation Engine
- AI Workflows
- Kafka Integration
- Retrieval Augmented Generation (RAG)
- AI Agents
- Observability
- Vector Databases

---

# License

MIT License