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
| ⏳ 05-ai-workflows             | Planned   | Multi-step AI Workflows                    |
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

# Branch 04 — AI Recommendation Engine

Branch 04 demonstrates how an AI model can be used to make product recommendations while keeping **business rules and authoritative data under application control**.

The application combines:

* Structured customer information
* Deterministic eligibility rules
* Product relevance filtering
* LLM-based recommendation
* Structured AI output
* Application-side validation

The key principle is:

> **Use deterministic code for business rules and AI for interpretation and recommendation.**

---

# Architecture

```text
                         User
                           │
                           ▼
                Recommendation API
                           │
                           ▼
                RecommendationService
                           │
                           ▼
                    CustomerProfile
                           │
                           ▼
                    ProductCatalog
                     │           │
                     │           └── Category Relevance
                     └────────────── Fee Eligibility
                           │
                           ▼
                   Candidate Products
                           │
                           ▼
                       ChatClient
                           │
                           ▼
                        Ollama
                           │
                           ▼
              RecommendationDecision
                 │       │        │
                 │       │        └── confidence
                 │       └────────── reason
                 └────────────────── productId
                           │
                           ▼
                 Java Product Validation
                           │
                           ▼
                    Trusted Product
                           │
                           ▼
                RecommendationResponse
```

---

# Key Design Principle

The LLM does **not** generate authoritative product information.

The LLM returns:

```text
productId
reason
confidence
```

Java then validates the `productId` against the trusted product catalog and returns the actual product information.

This prevents the AI from inventing product attributes such as:

* Annual fee
* Product name
* Product category
* Product features

---

# Recommendation Flow

For example, a customer may provide:

```json
{
  "customerId": "CUST-001",
  "customerProfile": {
    "customerId": "CUST-001",
    "name": "John",
    "monthlySpending": 50000,
    "primarySpendingCategory": "TRAVEL",
    "maxAnnualFee": 100
  },
  "requirement": "I travel frequently, spend a lot on dining, and want a low annual fee."
}
```

The application first applies deterministic filtering.

For example:

```text
Annual Fee <= Customer Maximum Fee
```

and:

```text
Product Category = Customer Primary Spending Category
```

Only relevant products are then provided to the AI model.

The AI produces a structured decision such as:

```json
{
  "productId": "CC-001",
  "reason": "Matches customer's primary spending category and annual fee is within the maximum allowed limit.",
  "confidence": 0.99
}
```

Java validates `CC-001` and retrieves the authoritative product from the catalog.

---

# Example Response

```json
{
  "product": {
    "productId": "CC-001",
    "name": "Travel Rewards Card",
    "category": "TRAVEL",
    "annualFee": 99.0,
    "description": "Travel rewards, airport benefits and dining rewards"
  },
  "reason": "Matches customer's primary spending category and annual fee is within the maximum allowed limit.",
  "confidence": 0.99
}
```

---

# Product Catalog

The current implementation uses an in-memory catalog containing:

| Product             | Category | Annual Fee |
| ------------------- | -------- | ---------: |
| Travel Rewards Card | TRAVEL   |         99 |
| Cashback Card       | CASHBACK |         49 |
| Premium Travel Card | TRAVEL   |        199 |
| Everyday Card       | GENERAL  |          0 |

The catalog is intentionally kept in memory for this branch so that the focus remains on the AI recommendation pattern rather than database infrastructure.

---

# Important AI Engineering Patterns

### 1. Deterministic Business Rules

Business constraints such as eligibility should be enforced by Java.

```text
Customer maximum fee = 100

Product fee = 199

→ Product rejected
```

The LLM should not be responsible for enforcing hard business constraints.

### 2. AI for Judgment

The LLM is used where natural language understanding and qualitative judgment provide value.

Examples:

* Understanding customer requirements
* Comparing relevant products
* Generating recommendation reasoning
* Providing a confidence score

### 3. Structured AI Output

Instead of relying on free-form text, the application expects:

```text
RecommendationDecision
    ├── productId
    ├── reason
    └── confidence
```

### 4. AI Output Validation

The application validates the AI-generated `productId` against the trusted catalog before returning the result.

### 5. Graceful Handling of No Candidates

The application checks for an empty candidate list before calling the LLM.

```text
No suitable products
        ↓
Do not invoke LLM
        ↓
Return clear error
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
│   │       │       └── service
│   │       │           └── impl
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

## Start Ollama

From the `docker` directory:

```bash
docker compose up -d
```

Make sure the Llama model is available:

```bash
docker exec -it ollama ollama list
```

If required:

```bash
docker exec -it ollama ollama pull llama3.1
```

---

## Start the Application

From:

```text
ai-engineering-patterns/
```

run:

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

---

# API

### Recommendation

```http
POST /api/v1/recommendations
```

Example:

```json
{
  "customerId": "CUST-001",
  "customerProfile": {
    "customerId": "CUST-001",
    "name": "John",
    "monthlySpending": 50000,
    "primarySpendingCategory": "TRAVEL",
    "maxAnnualFee": 100
  },
  "requirement": "I travel frequently, spend a lot on dining, and want a low annual fee."
}
```

---

# Learning Outcomes

After completing Branch 04, the project demonstrates:

* Spring AI with Ollama
* Structured AI responses
* AI-powered recommendation
* Deterministic business-rule enforcement
* AI output validation
* Separation of AI reasoning from authoritative business data
* Handling of AI edge cases
* Layered Spring Boot architecture

---

# Future Roadmap

Upcoming branches will expand the platform into:

* Multi-step AI Workflows
* Kafka and Event-Driven AI
* Retrieval Augmented Generation (RAG)
* Autonomous AI Agents
* Production-oriented AI architecture

---

## License

MIT License
