# NetSpect — SOC Backend Engine

> A production-ready Spring Boot backend for Security Operations Centers, featuring real-time event streaming, automated threat response, and live dashboard integration.

---

<div align="center">

![Java](https://img.shields.io/badge/Java-17-blue.svg?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-6DB33F.svg?style=flat-square&logo=springboot)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.7-black.svg?style=flat-square&logo=apachekafka)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-8.x-005571.svg?style=flat-square&logo=elasticsearch)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791.svg?style=flat-square&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-green.svg?style=flat-square)

</div>

---

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Reference](#api-reference)
- [WebSocket Real-Time Alerts](#websocket-real-time-alerts)
- [Data Models](#data-models)
- [Security & Production Notes](#security--production-notes)

---

## Overview

**NetSpect** is a Spring Boot backend engine designed to power a modern Security Operations Center (SOC). It ingests security events from distributed agents and SIEM systems, stores them for fast search and long-term analysis, triggers automated responses to detected threats, and pushes real-time alerts to a live dashboard — all with sub-second latency via Kafka streaming and WebSocket channels.

---

## Architecture

```
┌──────────────┐     ┌──────────────┐     ┌─────────────────────────────────┐
│   Agents /   │     │              │     │         Spring Boot             │
│    SIEM      │────▶│    Kafka     │────▶│           Backend                │
│  (Producers) │     │  (Topic:     │     │                                 │
└──────────────┘     │   events)    │     │  ┌─────────────┐  ┌───────────┐  │
                     │              │     │  │  Consumer   │  │  SOAR     │  │
                     └──────────────┘     │  └──────┬──────┘  │ Service   │  │
                                          │         │         └─────┬─────┘  │
                                          │         ▼               │        │
                                          │  ┌─────────────┐  ┌─────▼────┐   │
                                          │  │  Storage    │  │WebSocket │   │
                                          │  │  Service    │  │  Push    │   │
                                          │  └──────┬──────┘  └─────┬────┘   │
                                          └─────────┼───────────────┼────────┘
                                                    │               │
                                        ┌───────────▼──┐    ┌──────▼──────┐
                                        │ Elasticsearch │    │  Dashboard   │
                                        │ (Search &     │    │ (Real-time  │
                                        │  Analytics)   │    │   Alerts)   │
                                        └───────────────┘    └─────────────┘
                                                    │
                                        ┌───────────▼──────────┐
                                        │      PostgreSQL       │
                                        │   (Persistence &      │
                                        │    Audit Trail)       │
                                        └───────────────────────┘
```

**Data flow:**
1. Agents/SIEM send events → **Kafka** topic `events`
2. Spring Boot **Consumer** reads from Kafka
3. **Storage Service** writes to **Elasticsearch** (search) and **PostgreSQL** (persistence)
4. **SOAR Service** evaluates threats and executes automated response commands
5. **WebSocket** pushes real-time alerts to `/topic/alerts`

---

## Tech Stack

| Layer             | Technology                                   | Purpose                              |
|-------------------|----------------------------------------------|--------------------------------------|
| **Framework**      | Spring Boot 3.5.13                           | Core application framework           |
| **Language**       | Java 17                                      | Primary language                     |
| **Message Broker** | Apache Kafka                                 | Event streaming & decoupling         |
| **Search Engine**  | Elasticsearch (Spring Data Elasticsearch)    | Full-text search & analytics         |
| **Database**       | PostgreSQL (Spring Data JPA)                 | Persistent storage & audit trail     |
| **Real-time**      | Spring WebSocket (STOMP)                     | Live dashboard notifications         |
| **HTTP Client**    | Spring WebFlux (WebClient)                   | Agent command delivery              |
| **Build Tool**     | Maven                                        | Dependency management & build       |

---

## Features

### 🔄 Event Streaming (Kafka)
- Consumes unified security events from configurable Kafka topic
- Supports structured event types: **Login**, **Network**, **Endpoint**
- Asynchronous, fault-tolerant processing pipeline

### 💾 Dual Storage Layer
- **Elasticsearch** — Full-text search, time-series analytics, high-performance querying
- **PostgreSQL** — ACID-compliant persistence, audit trail, relational reporting

### 🔍 Advanced Event Querying
- Query by `deviceId`, `eventType`, `severity`, `sourceIp`, `destinationIp`
- Full-text search across raw event payloads
- Time-range filtering with pagination support

### 🚀 SOAR Automation
| Action           | Description                                          |
|------------------|------------------------------------------------------|
| `KILL_PROCESS`   | Terminate a malicious process on a target endpoint   |
| `BLOCK_IP`       | Block a hostile IP address at the firewall           |
| `ISOLATE_HOST`   | Quarantine a compromised device from the network     |

All SOAR actions are logged with a `triggeredBy` event reference for full traceability.

### 📊 Real-Time Dashboard Alerts
- Live push to all connected dashboards via WebSocket/STOMP
- Alerts delivered to `/topic/alerts` with severity, device info, and action taken
- Immediate visibility into automated response actions

---

## Project Structure

```
src/main/java/com/soc/backend_core/
│
├── BackendCoreApplication.java         # Spring Boot entry point
│
├── WebSocketConfig.java                # STOMP + WebSocket endpoint config
├── KafkaConfig.java                    # Kafka producer/consumer beans
│
├── controller/
│   ├── EventController.java            # POST /api/events/{type}
│   ├── EventQueryController.java       # GET /api/events/search
│   └── SoarController.java              # POST /api/soar/{action}
│
├── consumer/
│   └── EventConsumer.java              # Kafka listener → ES + PostgreSQL
│
├── producer/
│   └── EventProducer.java              # Kafka message producer
│
├── service/
│   ├── EventStorageService.java        # Elasticsearch + JPA persistence
│   ├── EventQueryService.java           # Elasticsearch query builder
│   └── SoarService.java                 # SOAR logic + alert broadcasting
│
├── repository/
│   ├── elastic/
│   │   └── EventDocumentRepository.java # ES repository (auto-generated queries)
│   └── jpa/
│       └── EventRecordRepository.java   # JPA repository
│
├── Entities/
│   ├── elastic/
│   │   └── EventDocument.java           # Elasticsearch document mapping
│   ├── jpa/
│   │   └── EventRecord.java             # JPA entity
│   └── domain/
│       ├── UnifiedEvent.java           # Unified event model (Builder pattern)
│       ├── SoarCommand.java             # SOAR command model
│       └── AlertMessage.java            # WebSocket alert payload model
│
└── dto/
    ├── LoginEventRequest.java          # Login event DTO
    ├── EndpointEventRequest.java       # Endpoint event DTO
    ├── NetworkEventRequest.java        # Network event DTO
    └── SoarCommandRequest.java         # SOAR command DTO
```

---

## Getting Started

### Prerequisites

All external dependencies must be available:

| Service        | Default Host        | Default Port |
|----------------|--------------------|--------------|
| Kafka          | `localhost`        | `9092`       |
| Elasticsearch  | `localhost`        | `9200`       |
| PostgreSQL     | `localhost`        | `5432`       |

A `docker-compose.yml` is included in the project root. Start all dependencies with one command:

```powershell
docker-compose up -d
```

> **Note:** Create the PostgreSQL database `soc_db1` before starting the application:
> ```sql
> CREATE DATABASE soc_db1;
> ```

### Configuration

All runtime settings are externalized in `src/main/resources/application.yml`:

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: soc-backend
      auto-offset-reset: earliest
  elasticsearch:
    uris: http://localhost:9200
  datasource:
    url: jdbc:postgresql://localhost:5432/soc_db1
    username: soc
    password: soc123
  jpa:
    hibernate:
      ddl-auto: create-drop

server:
  port: 8080
```

> **⚠️ Production Tip:** Never commit credentials to version control. Use environment variables or a secrets manager for all sensitive configuration values.

### Build

```powershell
# Clean and build (skip tests during build)
./mvnw.cmd clean package -DskipTests
```

### Run

```powershell
# Start the application
./mvnw.cmd spring-boot:run
```

The backend will be available at **http://localhost:8080**.

---

## API Reference

### Event Ingestion

Submit events via REST → Kafka → Consumer → ES & PostgreSQL.

#### Login Event
```http
POST /api/events/login
Content-Type: application/json

{
  "deviceId": "workstation-001",
  "sourceIp": "192.168.1.50",
  "user": "admin",
  "loginResult": "FAILURE",
  "severity": "HIGH"
}
```

#### Endpoint Event
```http
POST /api/events/endpoint
Content-Type: application/json

{
  "deviceId": "server-01",
  "process": "malware.exe",
  "severity": "CRITICAL",
  "action": "PROCESS_START"
}
```

#### Network Event
```http
POST /api/events/network
Content-Type: application/json

{
  "deviceId": "fw-gateway-01",
  "sourceIp": "10.0.0.5",
  "destinationIp": "172.16.0.100",
  "protocol": "TCP",
  "bytesOut": 50000,
  "severity": "HIGH"
}
```

---

### Event Querying

Search and filter events stored in Elasticsearch.

#### Search with filters
```http
GET /api/events/search?eventType=LOGIN&severity=HIGH&page=0&size=20
```

#### Search by date range
```http
GET /api/events/search?from=2025-01-01T00:00:00Z&to=2025-12-31T23:59:59Z
```

#### Full-text search
```http
GET /api/events/search?query=brute%20force&page=0&size=20
```

#### Search by IP address
```http
GET /api/events/search?sourceIp=192.168.1.50
```

---

### SOAR Commands

Trigger automated security response actions.

#### Kill Process
```http
POST /api/soar/kill-process
Content-Type: application/json

{
  "deviceId": "workstation-001",
  "targetProcess": "malware.exe",
  "triggeredBy": "event-12345"
}
```

#### Block IP
```http
POST /api/soar/block-ip
Content-Type: application/json

{
  "deviceId": "firewall-01",
  "targetIp": "10.0.0.5",
  "triggeredBy": "event-67890"
}
```

#### Isolate Host
```http
POST /api/soar/isolate-host
Content-Type: application/json

{
  "deviceId": "workstation-042",
  "triggeredBy": "event-99999"
}
```

#### List All Commands
```http
GET /api/soar/commands
```

---

## WebSocket Real-Time Alerts

### Connection

Connect to the WebSocket endpoint using STOMP over SockJS or raw WebSocket:

```
ws://localhost:8080/ws
```

### Subscribe

Subscribe to the alerts topic to receive all SOAR action notifications:

```
/topic/alerts
```

### Alert Payload

Every alert pushed to the dashboard follows this schema:

```json
{
  "alertId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "type": "COMMAND_EXECUTED",
  "severity": "CRITICAL",
  "deviceId": "workstation-042",
  "message": "Host isolated: workstation-042",
  "commandType": "ISOLATE_HOST",
  "timestamp": "2025-07-06T14:30:00Z"
}
```

| Field          | Description                                           |
|----------------|-------------------------------------------------------|
| `alertId`      | Unique alert identifier                               |
| `type`         | Alert type (e.g., `COMMAND_EXECUTED`)                 |
| `severity`     | Severity level: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`  |
| `deviceId`     | Affected device                                       |
| `message`      | Human-readable alert description                      |
| `commandType`  | SOAR action type: `KILL_PROCESS`, `BLOCK_IP`, `ISOLATE_HOST` |
| `timestamp`    | Alert generation timestamp (ISO 8601)                 |

---

## Data Models

### UnifiedEvent
The canonical event model used across Kafka, Elasticsearch, and PostgreSQL.

| Field           | Type            | Description                                         |
|-----------------|-----------------|-----------------------------------------------------|
| `eventId`       | String          | Unique event identifier                             |
| `deviceId`      | String          | Source device identifier                            |
| `eventType`     | String          | Event category: `LOGIN`, `NETWORK`, `ENDPOINT`      |
| `sourceIp`      | String          | Source IP address                                   |
| `destinationIp` | String          | Destination IP address                              |
| `process`       | String          | Process name (for endpoint events)                   |
| `user`          | String          | Username associated with the event                   |
| `severity`      | String          | Severity: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`       |
| `source`        | String          | Originating system / agent name                     |
| `timestamp`     | `java.time.Instant` | Event occurrence time (UTC)                      |
| `raw`           | `Map<String, Object>` | Full original event payload (JSON)            |

### SoarCommand
Tracks every automated security response action executed.

| Field           | Type            | Description                                         |
|-----------------|-----------------|-----------------------------------------------------|
| `commandId`     | String          | Unique command identifier                           |
| `commandType`   | String          | `KILL_PROCESS`, `BLOCK_IP`, `ISOLATE_HOST`          |
| `deviceId`      | String          | Target device for the command                       |
| `targetProcess` | String          | Process to terminate *(KILL_PROCESS only)*          |
| `targetIp`      | String          | IP address to block *(BLOCK_IP only)*               |
| `triggeredBy`   | String          | Event ID that triggered this command                |
| `status`        | String          | `PENDING`, `SENT`, `SUCCESS`, `FAILED`              |
| `createdAt`     | `java.time.Instant` | Command creation timestamp (UTC)               |

---

## Security & Production Notes

| Area              | Recommendation                                                                 |
|-------------------|---------------------------------------------------------------------------------|
| **Authentication** | Protect all `/api/**` endpoints with Spring Security (OAuth2 / JWT recommended) |
| **WebSocket**      | Add STOMP origin validation and rate limiting on `/topic/alerts`               |
| **Kafka**          | Enable SASL authentication and TLS for broker communication                      |
| **Elasticsearch**  | Enable built-in security (XPack) with TLS and role-based access control        |
| **PostgreSQL**     | Use strong passwords, TLS connections, and restricted user roles                |
| **Secrets**        | Never store credentials in `application.yml` — use environment variables or a vault |
| **Network**        | Run all services behind a private network; expose only the public-facing port  |
| **SOAR Commands**  | Implement approval workflows for high-impact actions (e.g., host isolation)    |

---

## 📄 License

This project is licensed under the terms included in the [LICENSE](LICENSE) file.
