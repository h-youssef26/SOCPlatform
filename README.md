# 🛡️ NetSpect — Unified Autonomous SOC Platform

> A full-stack Security Operations Center (SOC) platform built for SMEs, providing automated threat detection and response.

---

## 📌 Project Overview

NetSpect is an autonomous SOC platform that combines NDR, EDR, SIEM, SOAR, and AI into one unified system. It monitors network traffic and endpoint devices, detects attacks automatically, and responds to them in real time without human intervention.

---

## 🏗️ System Architecture

```
┌───────────────────────────┐
│       Web Dashboard        │
│        (React UI)          │
└─────────────┬─────────────┘
              │
       REST API + WebSocket
              │
┌─────────────▼─────────────┐
│       Backend Core         │
│       Spring Boot          │
│   (Log Ingestion + API)    │
└─────────────┬─────────────┘
              │
       Event Streaming
              │
┌─────────────▼─────────────┐
│           Kafka             │
└──────┬──────────────┬──────┘
       │              │
Detection Engine   Storage Layer
  (Rules + AI)   Elasticsearch / DB
       │
  SOAR Core
       │
Response Commands
       │
┌──────▼──────────┐
│  Endpoint Agent  │
│  Network Sensor  │
└──────────────────┘
```

---

## 🧩 System Modules

| Module | Role |
|--------|------|
| NDR | Network traffic analysis |
| EDR | Endpoint device monitoring |
| SIEM | Log collection and analysis |
| SOAR | Automated response execution |
| AI Engine | Detects abnormal behaviors |

---

## ✅ Backend — Completed Sprints

### Sprint 1 — Event Ingestion API
**Built by: Eman**

The entry gate of the system. Receives all events from NDR and EDR, normalizes them into a unified schema, and pushes them into Kafka.

**Endpoints:**
```
POST /api/events/network    → NDR / Suricata events
POST /api/events/endpoint   → EDR / Agent events
POST /api/events/login      → Login events
```

**Example Payload:**
```json
{
  "deviceId": "server1",
  "eventType": "process_start",
  "process": "powershell.exe",
  "user": "admin"
}
```

**Kafka Topics Created:**
```
events.network
events.endpoint
events.alerts
```

---

### Sprint 2 — Storage & Query Layer
**Built by: Eman**

Consumes events from Kafka and stores them in both Elasticsearch and PostgreSQL. Provides a Query API for the Dashboard.

**Query Endpoints:**
```
GET /api/query/events                       → all events
GET /api/query/events/device/{deviceId}     → by device
GET /api/query/events/severity/{severity}   → by severity
GET /api/query/events/source/{source}       → NDR or EDR
GET /api/query/events/type/{eventType}      → by event type
GET /api/query/stats                        → summary stats
```

**Stats Response Example:**
```json
{
  "totalEvents": 3,
  "highSeverity": 1,
  "mediumSeverity": 1,
  "lowSeverity": 1,
  "ndrEvents": 1,
  "edrEvents": 2
}
```

---

### Sprint 3 — SOAR + WebSocket Live Alerts
**Built by: Eman**

Receives triggers from the Detection Engine and sends automated response commands to Endpoint Agents. Pushes live alerts to the Dashboard in real time via WebSocket.

**SOAR Endpoints:**
```
POST /api/soar/kill-process   → kill suspicious process
POST /api/soar/block-ip       → block dangerous IP
POST /api/soar/isolate        → isolate compromised machine
GET  /api/soar/commands       → all executed commands
GET  /api/soar/stats          → commands summary
POST /api/soar/test-alert     → push test alert to Dashboard
```

**WebSocket:**
```
Connect to:    /ws/alerts
Subscribe to:  /topic/alerts
Test page at:  http://localhost:8080/test.html
```

**SOAR Stats Example:**
```json
{
  "totalCommands": 3,
  "killProcessCount": 1,
  "blockIpCount": 1,
  "isolateHostCount": 1
}
```

---

## 🗂️ Project Structure

```
autonomous-soc-platform/
│
├── backend-core/
│   ├── docker-compose.yml
│   └── backend-core/
│       ├── src/main/java/com/soc/backend_core/
│       │   ├── BackendCoreApplication.java
│       │   ├── KafkaConfig.java
│       │   ├── WebSocketConfig.java
│       │   ├── model/
│       │   │   ├── UnifiedEvent.java
│       │   │   ├── EventDocument.java
│       │   │   ├── EventRecord.java
│       │   │   ├── SoarCommand.java
│       │   │   └── AlertMessage.java
│       │   ├── dto/
│       │   │   ├── NetworkEventRequest.java
│       │   │   ├── EndpointEventRequest.java
│       │   │   ├── LoginEventRequest.java
│       │   │   └── SoarCommandRequest.java
│       │   ├── controller/
│       │   │   ├── EventController.java
│       │   │   ├── EventQueryController.java
│       │   │   ├── SoarController.java
│       │   │   └── WebSocketController.java
│       │   ├── producer/
│       │   │   └── EventProducer.java
│       │   ├── consumer/
│       │   │   └── EventConsumer.java
│       │   ├── service/
│       │   │   ├── EventStorageService.java
│       │   │   ├── EventQueryService.java
│       │   │   └── SoarService.java
│       │   └── repository/
│       │       ├── EventDocumentRepository.java
│       │       └── EventRecordRepository.java
│       └── src/main/resources/
│           ├── application.yml
│           └── static/
│               └── test.html

```

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 3.5.1 | REST API framework |
| Apache Kafka | 7.3.0 | Event streaming |
| Elasticsearch | 8.11.0 | Fast event search and storage |
| PostgreSQL | 15 | Structured event storage |
| WebSocket (STOMP) | - | Live alerts to Dashboard |
| Docker | Latest | Running all services |
| Java | 17 | Programming language |

---

## 🚀 How to Run

### Step 1 — Start All Services with Docker

```bash
cd backend-core
docker-compose up -d
```

This starts:
- Kafka + Zookeeper
- Elasticsearch
- PostgreSQL

### Step 2 — Start the Backend

```bash
cd backend-core/backend-core
./mvnw spring-boot:run
```

### Step 3 — Verify Everything is Running

```bash
docker ps
```

You should see:
```
kafka
zookeeper
elasticsearch
postgres
```

Spring Boot runs on:
```
http://localhost:8080
```

---

## 🧪 Quick Test

### Send a Network Event
```bash
curl -X POST http://localhost:8080/api/events/network \
  -H "Content-Type: application/json" \
  -d '{
    "deviceId": "sensor1",
    "eventType": "port_scan",
    "srcIp": "192.168.1.99",
    "destIp": "192.168.1.10"
  }'
```

### Send an Endpoint Event
```bash
curl -X POST http://localhost:8080/api/events/endpoint \
  -H "Content-Type: application/json" \
  -d '{
    "deviceId": "windows-pc1",
    "eventType": "malware_detected",
    "process": "malware.exe",
    "user": "admin"
  }'
```

### Trigger SOAR Response
```bash
curl -X POST http://localhost:8080/api/soar/kill-process \
  -H "Content-Type: application/json" \
  -d '{
    "deviceId": "windows-pc1",
    "targetProcess": "malware.exe",
    "triggeredBy": "malware_detection"
  }'
```

### View Live Alerts in Browser
```
http://localhost:8080/test.html
```

---

## 🎯 Final Demo Scenario

```
1. Attacker scans network
2. NDR detects the scan          → POST /api/events/network
3. Attacker drops malware
4. EDR detects the malware       → POST /api/events/endpoint
5. SIEM correlates both events   → Kafka + Elasticsearch
6. SOAR responds automatically   → POST /api/soar/kill-process
                                 → POST /api/soar/block-ip
                                 → POST /api/soar/isolate
7. Dashboard shows live alerts   → WebSocket /ws/alerts
```

---

## 📋 Sprint Progress

| Sprint | Task | Owner | Status |
|--------|------|-------|--------|
| Sprint 1 | Event Ingestion API | Eman | ✅ Done |
| Sprint 2 | Storage & Query Layer | Eman | ✅ Done |
| Sprint 3 | SOAR + WebSocket | Eman | ✅ Done |
