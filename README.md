# AI Engineering Patterns

A production-oriented collection of **AI Engineering patterns** built using Java, Spring Boot, Spring AI, Ollama, Kafka, and modern AI application architecture.

The goal of this repository is to demonstrate different AI application patterns through small, focused implementations.

---

## Repository Roadmap

| Branch | Status | Description |
|---|---|---|
| 01-ai-assistant | Completed | AI Chat Assistant using Spring AI + Ollama |
| 02-tool-calling | Completed | AI Tool Calling with Spring AI |
| 03-model-context-protocol | Completed | Remote Tool Integration using MCP |
| 04-ai-recommendation-engine | Completed | AI-powered Product Recommendation Engine |
| 05-ai-workflows | Completed | Multi-step AI Workflow Orchestration |
| 06-event-driven-ai | Completed | Kafka + AI Integration |
| 07-rag | Planned | Retrieval Augmented Generation |
| 08-ai-agents | Planned | AI Agent Architecture |

---

# Technology Stack

- Java 21
- Spring Boot 4
- Spring AI
- Ollama
- Llama 3.1
- Apache Kafka
- Model Context Protocol (MCP)
- Maven
- Docker
- Lombok

---

# Branch 05 — AI Workflows

Branch 05 demonstrates how to orchestrate **multiple AI and deterministic application steps** into a single workflow.

The workflow combines:

- Structured workflow state
- Deterministic Java processing
- LLM-powered content generation
- Sequential workflow steps
- Business-rule validation
- Typed data passed between workflow stages

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

- Destination
- Number of days
- Budget
- Preferences

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

The repository contains two Spring Boot applications:

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

The MCP server provides calculator tools used by the application.

The AI Workflow does not require the calculator tool, but the MCP infrastructure remains part of the application from the previous branch.

---

# Branch 06 — Event-Driven AI

Branch 06 demonstrates how an AI capability can be integrated into an **event-driven architecture using Apache Kafka**.

A transaction event is published to Kafka, consumed by an AI processing service, analyzed using an LLM, and the resulting risk decision is published back to Kafka.

The implementation intentionally remains small and focuses on the core pattern.

---

# Use Case

The example implements a simple **AI Transaction Risk Analysis** flow.

Example transaction:

```json
{
  "transactionId": "TXN-1001",
  "customerId": "CUST-001",
  "amount": 8500,
  "category": "TRAVEL"
}
```

The transaction is published to:

```text
transaction-events
```

The Kafka consumer processes the event and sends the transaction information to the LLM.

The LLM produces a structured risk decision:

```text
TransactionRiskDecision
 ├── transactionId
 ├── riskLevel
 └── reason
```

The decision is then published to:

```text
transaction-risk-events
```

---

# Event-Driven Architecture

```text
                    Transaction API
                           │
                           ▼
                 TransactionEventProducer
                           │
                           ▼
                  transaction-events
                           │
                           ▼
              TransactionEventConsumer
                           │
                           ▼
             TransactionRiskAnalyzer
                           │
                           ▼
                     Ollama / LLM
                           │
                           ▼
              TransactionRiskDecision
                           │
                           ▼
             TransactionRiskEventProducer
                           │
                           ▼
              transaction-risk-events
```

---

# Event Flow

### Step 1 — Publish Transaction Event

The application exposes:

```http
POST /api/v1/transactions
```

Example:

```json
{
  "transactionId": "TXN-1001",
  "customerId": "CUST-001",
  "amount": 8500,
  "category": "TRAVEL"
}
```

The event is published to:

```text
transaction-events
```

---

### Step 2 — Consume Transaction Event

A Kafka consumer listens to:

```text
transaction-events
```

The consumer receives:

```text
TransactionEvent
```

and passes it to the risk analysis service.

---

### Step 3 — AI Risk Analysis

`TransactionRiskAnalyzer` sends the transaction information to the LLM through Spring AI.

The LLM produces:

```text
TransactionRiskDecision
```

Example:

```text
TransactionRiskDecision[
    transactionId=TXN-1003,
    riskLevel=LOW,
    reason=Small amount for groceries
]
```

---

### Step 4 — Publish Risk Decision

The AI result is published to:

```text
transaction-risk-events
```

This allows downstream consumers to process the AI decision independently.

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

The workflow passes typed objects between stages:

```text
WorkflowRequest
      ↓
TravelRequirements
      ↓
Itinerary
      ↓
WorkflowResponse
```

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

### 4. Event-Driven AI

AI processing can be triggered asynchronously by events:

```text
Event
  ↓
Kafka
  ↓
AI Processing
  ↓
Decision
  ↓
Kafka
```

---

### 5. Structured AI Output

AI results are represented using Java records:

```text
Itinerary
 ├── destination
 └── dailyPlans
```

and:

```text
TransactionRiskDecision
 ├── transactionId
 ├── riskLevel
 └── reason
```

---

### 6. Separation of Responsibilities

Each component has a focused responsibility:

```text
TravelWorkflow
    → Orchestrates the travel workflow

RequirementExtractor
    → Prepares workflow state

ItineraryGenerator
    → Uses AI to generate itinerary

BudgetValidator
    → Applies deterministic business rules

TransactionEventProducer
    → Publishes transaction events

TransactionEventConsumer
    → Consumes transaction events

TransactionRiskAnalyzer
    → Performs AI risk analysis

TransactionRiskEventProducer
    → Publishes AI risk decisions
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

# Docker Infrastructure

The repository maintains its own local AI infrastructure through Docker Compose.

The Docker environment contains:

```text
Ollama
   │
   └── localhost:11434

Kafka
   │
   └── localhost:9092

ZooKeeper
   │
   └── localhost:2181
```

The infrastructure is self-contained within this repository and does not depend on another project.

---

# Running the Application

There are four components involved in the local setup:

1. Ollama
2. Kafka
3. Calculator MCP Server
4. AI Engineering Patterns application

---

## 1. Start Docker Infrastructure

Navigate to:

```text
docker/
```

Run:

```bash
docker compose up -d
```

This starts:

- Ollama
- Kafka
- ZooKeeper

---

## 2. Verify Ollama

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

## 3. Start Calculator MCP Server

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

## 4. Start AI Engineering Patterns

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

This application contains:

- AI Assistant
- Tool Calling
- MCP Client
- Recommendation Engine
- AI Workflows
- Event-Driven AI

---

# Startup Order

Start the components in this order:

```text
1. Docker Infrastructure
   ├── Ollama
   ├── Kafka
   └── ZooKeeper
          │
          ▼
2. Calculator MCP Server
   localhost:8081
          │
          ▼
3. AI Engineering Patterns
   localhost:8080
```

The MCP Client initializes its connection to the MCP Server during application startup, so the MCP Server should be running before starting the main AI application.

---

# API

### AI Recommendation

```http
POST /api/v1/recommendations
```

---

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

### Transaction Event

```http
POST /api/v1/transactions
```

Example:

```json
{
  "transactionId": "TXN-1001",
  "customerId": "CUST-001",
  "amount": 8500,
  "category": "TRAVEL"
}
```

The event is published to:

```text
transaction-events
```

The resulting AI risk decision is published to:

```text
transaction-risk-events
```

---

# Learning Outcomes

After completing Branch 06, the project demonstrates:

- AI workflow orchestration
- Spring AI with Ollama
- Sequential workflow execution
- Typed workflow state
- Structured AI output
- Combining AI and deterministic processing
- Kafka producers and consumers
- Event-driven AI processing
- AI processing inside an asynchronous pipeline
- Publishing AI results as events
- MCP Client and MCP Server integration

---

# Future Roadmap

Upcoming branches:

- **07 — Retrieval Augmented Generation (RAG)**
- **08 — AI Agents**

These branches will be implemented separately as time permits.

---

## License

MIT License