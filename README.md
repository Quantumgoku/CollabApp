# CollabApp

Collaborative note-taking backend inspired by modern knowledge-sharing systems like Notion.

This project is focused on learning and implementing real-world backend engineering concepts progressively instead of building a simple CRUD application.

---

# Current Status

## Implemented

* Basic Notes CRUD
* Basic Users CRUD
* MongoDB integration
* DTO-based architecture (in progress)

---

# Planned Features

* Authentication & Authorization
* JWT Security
* Note Collaboration
* Realtime Editing (WebSockets)
* Versioning & Conflict Resolution
* Search System
* Activity Feed
* Production Deployment

---

# Tech Stack

## Backend

* Java
* Spring Boot
* Spring Data MongoDB
* Gradle

## Database

* MongoDB Atlas

## Planned

* Spring Security
* JWT
* WebSockets
* Docker
* CI/CD

---

# Project Structure

```text
src/main/java/com/example/collabapp/
│
├── controller/        # REST controllers
├── services/          # Business logic layer
├── repository/        # Database access layer
├── model/
│   ├── dao/           # Database entities/documents
│   └── dto/
│       ├── request/   # Incoming request DTOs
│       └── response/  # Outgoing response DTOs
├── exception/         # Custom exceptions & handlers
├── config/            # Security/configuration classes
└── util/              # Utility/helper classes
```

---

# Engineering Goals

This project is intended to teach:

* Clean Architecture
* Layered Backend Design
* Authentication & Authorization
* Real-time Systems
* Concurrency Handling
* Event-Driven Design
* Production Engineering

---

# Documentation

## Project Planning

* PROJECT_VISION.md
* BACKLOG.md
* CURRENT_SPRINT.md
* ARCHITECTURE_NOTES.md
* ENGINEERING_RULES.md

These files are used to:

* track project direction
* manage feature scope
* organize implementation stages
* maintain engineering discipline

---

# Development Philosophy

* Build incrementally
* Prefer depth over random complexity
* Finish systems before expanding scope
* Learn through implementation
* Understand every abstraction used

---

# Running the Project

## Clone

```bash
git clone <repo-url>
cd collabapp
```

## Run

```bash
./gradlew bootRun
```

---

# Future Direction

The long-term goal is evolving this into a production-style collaborative backend system with:

* realtime collaboration
* scalable architecture
* proper authorization
* event-driven systems
* production deployment
