# Nova

**Nova** is a lightweight **service discovery, dependency tracking, and observability platform for microservices**, inspired by systems such as Netflix Eureka.

Nova provides a centralized platform for registering and discovering microservices while also tracking service health, service-to-service dependencies, dependency graphs, and cascading failure impact.

The project goes beyond traditional service registries by providing visibility into **how services are connected and how failures can propagate across the system**.

---

## Overview

In a distributed microservice architecture, knowing whether a service is running is not always enough.

For example:

```text
Order Service
      │
      ├──────────────► Payment Service
      │
      └──────────────► Inventory Service
                              │
                              ▼
                       Warehouse Service
```

If `Inventory Service` goes down, Nova can identify the dependency relationship and determine which services may be impacted.

Nova therefore combines:

- Service Registration
- Service Discovery
- Heartbeat Monitoring
- Failure Detection
- Service Metadata
- Dependency Tracking
- Dependency Graphs
- Cascading Impact Analysis
- Service Health Information
- Web-based Observability Dashboard

---

## Architecture

```text
                              ┌─────────────────────────┐
                              │          Nova           │
                              │                         │
                              │   Service Registry      │
                              │   Service Discovery     │
                              │   Heartbeat Monitor     │
                              │   Service Metadata      │
                              │   Dependency Tracker    │
                              │   Health Information    │
                              │   Dependency Graph      │
                              │   Impact Analysis       │
                              └────────────┬────────────┘
                                           │
             ┌─────────────────────────────┼─────────────────────────────┐
             │                             │                             │
             ▼                             ▼                             ▼
      Service Registration          Service Discovery              Heartbeat
             │                             │                             │
             └─────────────────────────────┼─────────────────────────────┘
                                           │
                                           ▼
                                  Service Metadata
                                           │
                                           ▼
                                  Dependency Tracking
                                           │
                                           ▼
                                    Dependency Graph
                                           │
                                           ▼
                                Cascading Impact Analysis
                                           │
                                           ▼
                                  Service Health Data
                                           │
                                           ▼
                                  Nova Dashboard
```

---

## Core Components

### Service Registry

Nova maintains information about registered service instances including:

- Service name
- Instance ID
- Host
- Port
- Scheme
- Status
- Registration timestamp
- Last heartbeat timestamp

Services register themselves with Nova and become available for discovery.

---

### Service Discovery

Nova provides APIs for discovering registered service instances.

Services can retrieve available instances and their current status, allowing service consumers to determine which instances are available.

---

### Heartbeat Monitoring

Registered services periodically send heartbeat information to Nova.

Nova tracks the last heartbeat received from each service instance and uses this information to determine whether an instance is still active.

```text
Service
   │
   │ Heartbeat
   ▼
 Nova
   │
   │ Track last heartbeat
   ▼
Service Instance
   │
   ├── Active  → UP
   │
   └── Timeout → DOWN
```

---

### Server-Side Failure Detection

Nova performs server-side monitoring of registered instances.

If an instance stops sending heartbeats within the expected interval, Nova can mark that instance as unavailable.

This prevents service consumers from relying on stale service information.

---

### Service Metadata

Nova supports additional metadata associated with registered services.

Metadata can describe information such as:

- Service version
- Environment
- Region
- Availability zone
- Team
- Framework
- Runtime
- Git commit
- Custom JSON attributes

This allows Nova to provide more contextual information about individual service instances.

---

### Service Dependency Tracking

Nova tracks communication between services using source and target service relationships.

For example:

```text
Order Service ─────► Payment Service
```

is stored as a dependency relationship:

```text
source = order-service
target = payment-service
```

Nova maintains when a dependency was first observed and when it was most recently observed.

This provides the foundation for understanding service topology.

---

### Dependency Graph

Nova converts the stored dependency relationships into a service-level graph.

For example:

```text
             ┌───────────────┐
             │ Order Service │
             └───────┬───────┘
                     │
              ┌──────┴──────┐
              ▼             ▼
      ┌──────────────┐ ┌──────────────┐
      │   Payment    │ │  Inventory   │
      │   Service    │ │   Service    │
      └──────────────┘ └───────┬───────┘
                               │
                               ▼
                       ┌──────────────┐
                       │  Warehouse   │
                       │   Service    │
                       └──────────────┘
```

The graph represents the actual relationships observed between services rather than assuming a fixed request flow.

It can therefore represent:

- Linear dependencies
- One-to-many dependencies
- Many-to-one dependencies
- Complex dependency chains
- Circular dependencies

---

### Cascading Impact Analysis

Nova can analyze the dependency graph to determine which services may be impacted when a service becomes unavailable.

For example:

```text
A ──► B ──► C ──► D
```

If `C` becomes unavailable:

```text
C DOWN
  │
  ▼
D potentially impacted
```

For a more complex topology:

```text
             ┌──► B ──► D
             │
A ───────────┼──► C ──► E
             │
             └──► F ──► G
```

Nova can traverse the dependency relationships and identify services that depend directly or indirectly on the affected service.

Circular dependencies are handled through visited-node tracking to prevent infinite traversal.

---

## Health Information

Nova provides health information at the service-instance level.

Health information includes:

- Service name
- Instance ID
- Current status
- Last heartbeat
- Registration timestamp
- Number of healthy dependencies
- Number of unhealthy dependencies

Example:

```json
{
  "serviceName": "order-service",
  "instanceId": "8b9e...",
  "status": "UP",
  "lastHeartBeat": "2026-08-07T10:20:00Z",
  "registeredAt": "2026-08-07T09:00:00Z",
  "healthyDependencies": 2,
  "unhealthyDependencies": 1
}
```

The health layer currently focuses on providing basic and useful service health information without attempting to reduce service health to a single opaque score.

---

## Dashboard

Nova includes a lightweight HTML dashboard hosted directly by the Nova server.

The dashboard provides visibility into:

- Total services
- Total instances
- UP instances
- DOWN instances
- Registered service instances
- Instance status
- Host and port information
- Last heartbeat
- Service dependency graph
- Service topology

The dashboard is intentionally implemented using plain **HTML, CSS, and JavaScript** so that Nova remains lightweight and does not require a separate frontend application.

```text
┌──────────────────────────────────────────────────────────┐
│                         NOVA                             │
│                 Service Observability                    │
├────────────┬─────────────────────────────────────────────┤
│            │                                             │
│ Dashboard  │   Services       Instances       Health     │
│            │      12              27            24       │
│ Services   │                                             │
│            ├─────────────────────────────────────────────┤
│ Dependency │           Service Instances                 │
│            │                                             │
│ Health     │   Order Service       UP                    │
│            │   Payment Service     UP                    │
│            │   Inventory Service   DOWN                  │
│            │                                             │
│            ├─────────────────────────────────────────────┤
│            │                                             │
│            │          Dependency Graph                   │
│            │                                             │
│            │       A ─────► B ─────► C                  │
│            │        │                │                   │
│            │        └──────► D ◄─────┘                   │
│            │                                             │
└────────────┴─────────────────────────────────────────────┘
```

---

## API Architecture

Nova follows a layered Spring Boot architecture.

```text
                    REST Controllers
                           │
                           ▼
                       Services
                           │
                           ▼
                     Repositories
                           │
                           ▼
                    PostgreSQL Database
```

The application separates responsibilities across:

- Controllers
- Services
- Repositories
- Entities
- DTOs
- Mappers

This keeps API handling, business logic, persistence, and data transformation separated.

---

## Data Model

Nova currently persists information around the following major concepts:

```text
Service Instance
       │
       ├── Service Name
       ├── Instance ID
       ├── Host
       ├── Port
       ├── Status
       ├── Registered At
       └── Last Heartbeat


Service Metadata
       │
       ├── Version
       ├── Environment
       ├── Region
       ├── Zone
       ├── Team
       ├── Framework
       ├── Runtime
       ├── Git Commit
       └── Custom Metadata


Service Dependency
       │
       ├── Source Service
       ├── Target Service
       ├── First Seen
       └── Last Seen
```

---

## Technology Stack

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate

### Database

- PostgreSQL

### Supporting Libraries

- MapStruct
- Lombok
- Maven

### Frontend

- HTML
- CSS
- JavaScript
- SVG for dependency graph visualization

The stack is standard and unremarkable by design — no framework choice here is meant to be a differentiator. It has not yet been tuned for production (dependency versions, build configuration, and tooling are functional but not hardened), which is expected at this stage and is tracked in the Roadmap below.

---

## Project Structure

The backend is organized around individual Nova capabilities.

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── example/
    │           └── nova/
    │               │
    │               ├── core/
    │               │
    │               ├── registration/
    │               │
    │               ├── discovery/
    │               │
    │               ├── heartbeat/
    │               │
    │               ├── metadata/
    │               │
    │               ├── dependency/
    │               │
    │               ├── health/
    │               │
    │               └── dashboard/
    │
    └── resources/
        ├── application.properties
        │
        └── static/
            └── index.html
```

---

## Example Service Topology

A typical Nova-monitored system can look like:

```text
                         ┌─────────────────┐
                         │  Order Service  │
                         └────────┬────────┘
                                  │
                    ┌─────────────┴─────────────┐
                    │                           │
                    ▼                           ▼
          ┌─────────────────┐         ┌─────────────────┐
          │ Payment Service │         │Inventory Service│
          └─────────────────┘         └────────┬────────┘
                                               │
                                               ▼
                                      ┌─────────────────┐
                                      │Warehouse Service│
                                      └─────────────────┘
```

Nova observes these relationships and maintains them as dependency edges:

```text
Order → Payment
Order → Inventory
Inventory → Warehouse
```

If `Inventory` becomes unavailable, Nova can traverse the graph and identify services that depend on it.

---

## Current Features

- Service registration
- Service discovery
- Service instance management
- Heartbeat mechanism
- Server-side failure detection
- Service status tracking
- Service metadata
- Custom JSON metadata
- Service dependency tracking
- Dependency timestamps
- Dependency graph generation
- Service-level dependency visualization
- Cascading impact analysis
- Circular dependency handling
- Service health information
- Healthy dependency detection
- Unhealthy dependency detection
- Dashboard API
- Lightweight HTML dashboard
- Real-time dashboard refresh
- Dependency graph visualization

---

## Roadmap

Nova's core functionality is implemented. Further development is focused on improving reliability, observability, usability, and deployment rather than continuously adding unrelated features.

### Reliability

- Add comprehensive unit tests
- Add integration tests
- Improve exception handling
- Add request validation
- Improve API error responses

### Observability

- Expand service health information
- Add latency tracking
- Add request/error metrics
- Add historical service metrics
- Explore a meaningful health scoring model

### Dashboard

- Improve dependency graph layout
- Improve service filtering
- Add dependency details
- Add service health views
- Add historical status information

### Developer Experience

- Add API documentation
- Improve configuration
- Provide example client services
- Provide example microservice architecture
- Add Docker support
- Provide an easy local development setup

### Client Integration

- Provide a lightweight Nova client SDK
- Simplify service registration
- Simplify heartbeat handling
- Simplify dependency reporting
- Reduce the amount of Nova-specific code required inside client services

---

## Design Philosophy

Nova is designed around a few simple principles:

### Lightweight

Nova should provide useful service infrastructure without forcing every consumer to adopt a large ecosystem.

### Service-Level Visibility

Understanding that a service is `UP` or `DOWN` is useful, but understanding **what depends on that service** is even more valuable.

### Dependency-Aware

Nova treats service dependencies as first-class information rather than simply maintaining a registry of independent service instances.

### Extensible

The architecture separates registration, discovery, metadata, dependencies, health, and dashboard functionality so additional capabilities can be added without tightly coupling the entire system.

### Practical Observability

Nova focuses on information that can help developers understand the state and topology of their distributed systems rather than attempting to produce overly complex metrics without sufficient data.

---

## Running Nova

### Requirements

- Java
- Maven
- PostgreSQL

### Database

Configure PostgreSQL connection properties in:

```text
src/main/resources/application.properties
```

Then create the required database and start the Spring Boot application.

### Start the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or build and run the generated JAR:

```bash
mvn clean package
java -jar target/nova-*.jar
```

Once Nova is running, the dashboard is served directly by the Nova application.

---

## Project Status

Nova is an actively developed project focused on building a practical **service discovery and observability platform for microservice architectures**.

The core platform currently covers:

```text
Registration
     ↓
Discovery
     ↓
Heartbeat Monitoring
     ↓
Failure Detection
     ↓
Metadata
     ↓
Dependency Tracking
     ↓
Dependency Graph
     ↓
Cascading Impact Analysis
     ↓
Health Information
     ↓
Dashboard
```

The project is currently focused on **hardening, testing, documentation, and improving the developer experience** around the existing platform.

---

## License

This project is currently a personal/portfolio project.