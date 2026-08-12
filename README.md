# Nova

Nova is a lightweight **service discovery and observability platform for microservices**, inspired by systems like Netflix Eureka.

The goal is to go beyond basic service registration and discovery by providing service metadata, dependency tracking, dependency graphs, and eventually service health and cascading-failure analysis.

## Architecture

```text
                         ┌─────────────────────┐
                         │        Nova         │
                         │                     │
                         │  Service Registry   │
                         │  Service Metadata   │
                         │  Heartbeat Monitor  │
                         │  Dependency Tracker │
                         └──────────┬──────────┘
                                    │
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
              ▼                     ▼                     ▼
        Registration            Discovery             Heartbeat
              │                     │                     │
              └─────────────────────┼─────────────────────┘
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
                          Health & Observability

```

Current Features

* Service Registration
* Service Discovery
* Heartbeat mechanism
* Server-side failure detection
* Service Metadata
* Custom JSON Metadata
* Service Dependency Tracking

TODO

* Dependency Graph
* Dependency Impact Analysis
* Service Health Score
* Observability Metrics
* Cascading Failure Analysis
* Real-time Dashboard
* Client SDK for automatic registration, heartbeat and dependency tracking

Tech Stack

* Java
* Spring Boot
* Spring Data JPA / Hibernate
* PostgreSQL
* MapStruct
* Lombok
* Maven

Nova is currently under active development.