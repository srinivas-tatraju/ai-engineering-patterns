# AI Engineering Patterns

A production-oriented collection of **AI Engineering patterns** built using Java, Spring Boot, Spring AI, Ollama, and modern AI application architecture.

The goal of this repository is to demonstrate how AI capabilities can be integrated into enterprise applications using sound software engineering principles — rather than building simple chatbot examples.

---

## Repository Roadmap

| Branch                        | Status    | Description                                |
| ----------------------------- | --------- | ------------------------------------------ |
| ✅ 01-ai-assistant             | Completed | AI Chat Assistant using Spring AI + Ollama |
| ✅ 02-tool-calling             | Completed | AI Tool Calling with Spring AI             |
| ✅ 03-model-context-protocol   | Completed | Remote Tool Integration using MCP          |
| ✅ 04-ai-recommendation-engine | Completed | AI-powered Product Recommendation Engine   |
| ✅ 05-ai-workflows             | Completed | Multi-step AI Workflow Orchestration       |
| ⏳ 06-event-driven-ai          | Planned   | Kafka + AI Integration                     |
| ⏳ 07-rag                      | Planned   | Retrieval Augmented Generation             |
| ⏳ 08-ai-agents                | Planned   | Autonomous AI Agents                       |

---

# Technology Stack

* Java 21
* Spring Boot 4
* Spring AI
* Ollama
* Llama 3.1
* Model Context Protocol (MCP)
* Maven
* Docker
* Lombok

---

# Branch 05 — AI Workflows

Branch 05 demonstrates how to orchestrate **multiple AI and deterministic application steps** into a single workflow.

The workflow combines:

* Structured workflow state
* Deterministic Java processing
* LLM-powered content generation
* Sequential workflow steps
* Business-rule validation
* Typed data passed between workflow stages

The key principle is:

> **AI should be used where reasoning or natural-language generation adds value, while deterministic business rules should remain in application code.**

---

# Use Case

The branch implements a simple **AI Travel Planning Workflow**.

Example request:

```json
{
  "destination": "Vancouver",
  "numberOfDays": 4,
  "budget": 1500,
  "preferences": "Sightseeing and nature"
}
```

The workflow generates a day-by-day itinerary and validates whether the estimated trip cost fits within the customer's budget.

---

# Architecture

```text
                         User
                           │
                           ▼
                  WorkflowController
                           │
                           ▼
                    TravelWorkflow
                           │
                           ▼
                 RequirementExtractor
                       [Java]
                           │
                           ▼
                  TravelRequirements
                           │
              ┌────────────┴────────────┐
              ▼                         ▼
     ItineraryGenerator          BudgetValidator
           [LLM]                     [Java]
              │                         │
              ▼                         ▼
          Itinerary              Budget Result
              │                         │
              └────────────┬────────────┘
                           ▼
                    WorkflowResponse
```

---

# Workflow Execution

The workflow consists of multiple steps.

### Step 1 — Requirement Preparation

The incoming request is transformed into workflow state:

```text
WorkflowRequest
      ↓
RequirementExtractor
      ↓
TravelRequirements
```

This step is deterministic and does not require an LLM.

---

### Step 2 — AI Itinerary Generation

The structured requirements are passed to the LLM through Spring AI:

```text
TravelRequirements
      ↓
ChatClient
      ↓
Ollama / Llama 3.1
      ↓
Itinerary
```

The model generates a day-by-day travel itinerary based on:

* Destination
* Number of days
* Budget
* Preferences

---

### Step 3 — Budget Validation

Budget validation remains deterministic Java logic.

The current implementation uses an estimated daily cost:

```text
Estimated daily cost = $300
```

For a four-day trip:

```text
4 × $300 = $1,200
```

Therefore:

```text
Budget = $1,500
Estimated cost = $1,200

Within budget = true
```

For:

```text
Budget = $800
Estimated cost = $1,200

Within budget = false
```

---

# MCP Integration

The main AI application continues to use the **MCP Client** introduced in Branch 03.

The repository therefore contains two Spring Boot applications:

```text
AI Engineering Patterns
│
├── Main AI Application
│   └── MCP Client
│       └── localhost:8080
│
└── Calculator MCP Server
    └── localhost:8081
```

The MCP Client connects to the Calculator MCP Server using MCP over HTTP.

The MCP server provides the calculator tools used by the application.

The AI Workflow itself does not require the calculator tool, but the MCP infrastructure remains part of the application from the previous branch.

---

# Example Response

```json
{
  "destination": "Vancouver",
  "numberOfDays": 4,
  "itinerary": {
    "destination": "Vancouver",
    "dailyPlans": [
      "Day 1: Explore Stanley Park and Granville Island",
      "Day 2: Visit Capilano Suspension Bridge and Grouse Mountain",
      "Day 3: Discover Gastown and English Bay",
      "Day 4: Explore local attractions and nature"
    ]
  },
  "withinBudget": true
}
```

The exact itinerary is generated by the LLM and may vary between requests.

---

# Key AI Engineering Patterns

### 1. Workflow Orchestration

Multiple independent processing steps are coordinated by a single workflow:

```text
Request
  ↓
Step 1
  ↓
Step 2
  ↓
Step 3
  ↓
Response
```

---

### 2. Typed Workflow State

Instead of passing arbitrary strings between steps, the workflow passes typed objects:

```text
WorkflowRequest
      ↓
TravelRequirements
      ↓
Itinerary
      ↓
WorkflowResponse
```

This makes the workflow easier to understand, test, and maintain.

---

### 3. AI + Deterministic Processing

The workflow deliberately combines both approaches:

```text
AI
 └── Generate travel itinerary

Java
 ├── Prepare workflow state
 └── Validate budget
```

The LLM is not used for deterministic business rules.

---

### 4. Structured AI Output

The itinerary is represented using a Java record:

```text
Itinerary
 ├── destination
 └── dailyPlans
```

This provides a defined contract between the AI layer and the application.

---

### 5. Separation of Responsibilities

Each workflow component has a focused responsibility:

```text
TravelWorkflow
    → Orchestrates the workflow

RequirementExtractor
    → Prepares workflow state

ItineraryGenerator
    → Uses AI to generate itinerary

BudgetValidator
    → Applies deterministic business rules
```

---

# Project Structure

```text
ai-engineering-patterns
│
├── ai-engineering-patterns
│   ├── src
│   │   └── main
│   │       ├── java
│   │       │   └── com.aiengineeringpatterns
│   │       │       ├── config
│   │       │       ├── controller
│   │       │       ├── dto
│   │       │       ├── exception
│   │       │       ├── model
│   │       │       ├── service
│   │       │       └── workflow
│   │       └── resources
│   │
│   └── pom.xml
│
├── calculator-mcp-server
│   ├── src
│   └── pom.xml
│
└── docker
    └── docker-compose.yml
```

---

# Running the Application

Branch 05 depends on the existing MCP infrastructure from Branch 03.

There are **three components** that need to be running:

1. Ollama
2. Calculator MCP Server
3. AI Engineering Patterns application (MCP Client)

---

## 1. Start Ollama

From the `docker` directory:

```bash
docker compose up -d
```

Verify the model:

```bash
docker exec -it ollama ollama list
```

If required:

```bash
docker exec -it ollama ollama pull llama3.1
```

Ollama runs on:

```text
http://localhost:11434
```

---

## 2. Start Calculator MCP Server

Navigate to:

```text
calculator-mcp-server/
```

Run:

```bash
mvn spring-boot:run
```

The MCP Server runs on:

```text
http://localhost:8081
```

The server exposes calculator functionality through the Model Context Protocol.

---

## 3. Start AI Engineering Patterns

Navigate to:

```text
ai-engineering-patterns/
```

Run:

```bash
mvn spring-boot:run
```

The main AI application runs on:

```text
http://localhost:8080
```

This application also contains the MCP Client, which connects to the Calculator MCP Server.

---

## Startup Order

Start the components in this order:

```text
1. Ollama
      │
      ▼
2. Calculator MCP Server
   localhost:8081
      │
      ▼
3. AI Engineering Patterns
   MCP Client
   localhost:8080
```

The MCP Client currently initializes its connection to the MCP Server during application startup, so the MCP Server should be running before starting the main AI application.

---

# API

### AI Recommendation

```http
POST /api/v1/recommendations
```

### AI Travel Workflow

```http
POST /api/v1/workflows/travel
```

Example:

```json
{
  "destination": "Vancouver",
  "numberOfDays": 4,
  "budget": 1500,
  "preferences": "Sightseeing and nature"
}
```

---

# Learning Outcomes

After completing Branch 05, the project demonstrates:

* AI workflow orchestration
* Spring AI with Ollama
* Sequential workflow execution
* Typed workflow state
* Structured AI output
* Combining AI and deterministic processing
* Separation of AI responsibilities from business rules
* MCP Client and MCP Server integration
* Basic workflow validation

---

# Future Roadmap

Upcoming branches will expand the platform into:

* Event-Driven AI with Kafka
* Retrieval Augmented Generation (RAG)
* Autonomous AI Agents
* Production-oriented AI architecture

---

## License

MIT License
