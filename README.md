# README.md

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

# PROJECT_VISION.md

# CollabApp Vision

## Goal

Build a collaborative knowledge-sharing platform inspired by modern note-taking systems like Notion, focused primarily on backend engineering, real-time collaboration, and scalable architecture.

The project should progressively evolve from a simple CRUD application into a production-style collaborative system.

---

# Core Objectives

## Technical Objectives

* Learn clean backend architecture
* Build scalable REST APIs
* Understand authentication and authorization
* Implement real-time collaboration
* Learn concurrency handling
* Understand async/event-driven systems
* Gain production engineering experience

---

# Product Vision

Users should be able to:

* Create notes
* Edit notes
* Share notes with collaborators
* Collaborate in real-time
* Search notes
* Track note activity/history
* Manage permissions

---

# Engineering Areas This Project Should Teach

## Backend Engineering

* Spring Boot architecture
* DTO-based APIs
* Validation
* Exception handling
* Layered design

## Database Design

* MongoDB document modeling
* Relationship modeling using IDs
* Query optimization
* Indexing

## Security

* JWT authentication
* Password hashing
* Authorization
* Access control

## Realtime Systems

* WebSockets
* Event broadcasting
* State synchronization

## Distributed/System Design Concepts

* Versioning
* Optimistic locking
* Event-driven architecture
* Async processing

---

# Non-Goals (For Now)

DO NOT add these early:

* Microservices
* Kubernetes
* Complex AI features
* Multiple databases
* GraphQL
* Huge frontend frameworks

Focus on depth over tech-stack hopping.

---

# Long-Term Stretch Features

These belong in backlog only:

* Comments
* Followers/social graph
* Notifications
* AI summaries
* Rich text editing
* Offline sync
* Mobile clients
* Analytics dashboard

# BACKLOG.md

# Product Backlog

---

# EPIC 1 — Backend Foundation

## Goals

Create maintainable backend architecture.

## Tasks

* [ ] Add DTO-based APIs
* [ ] Add request validation
* [ ] Add global exception handling
* [ ] Add custom exceptions
* [ ] Add logging
* [ ] Add timestamps
* [ ] Refactor controllers to thin controllers
* [ ] Separate service/business logic cleanly

---

# EPIC 2 — Authentication System

## Goals

Implement secure multi-user authentication.

## Tasks

* [ ] Add passwordHash to User
* [ ] BCrypt password hashing
* [ ] Register API
* [ ] Login API
* [ ] JWT token generation
* [ ] JWT authentication filter
* [ ] Secure protected endpoints
* [ ] Add current-user extraction

---

# EPIC 3 — Collaboration System

## Goals

Allow multiple users to collaborate on notes.

## Tasks

* [ ] Add collaboratorIds to notes
* [ ] Share note API
* [ ] Remove collaborator API
* [ ] Permission validation
* [ ] Owner vs collaborator access rules
* [ ] View shared notes API

---

# EPIC 4 — Versioning & Concurrency

## Goals

Handle simultaneous edits safely.

## Tasks

* [ ] Add note version field
* [ ] Increment versions on update
* [ ] Reject stale updates
* [ ] Add edit history tracking
* [ ] Add optimistic locking concepts

---

# EPIC 5 — Real-Time Collaboration

## Goals

Provide live collaborative editing.

## Tasks

* [ ] Configure WebSockets
* [ ] Broadcast note updates
* [ ] Add live collaboration events
* [ ] Add online presence indicators
* [ ] Add typing indicators

---

# EPIC 6 — Search System

## Goals

Allow efficient note discovery.

## Tasks

* [ ] Add Mongo text indexes
* [ ] Search by title/content
* [ ] Pagination support
* [ ] Sorting support
* [ ] Filters

---

# EPIC 7 — Activity Feed

## Goals

Track important user actions.

## Tasks

* [ ] Create activity model
* [ ] Track note edits
* [ ] Track sharing activity
* [ ] Track deletions
* [ ] User activity timeline

---

# EPIC 8 — Production Engineering

## Goals

Make project deployment-ready.

## Tasks

* [ ] Dockerize application
* [ ] Add environment configs
* [ ] Add structured logging
* [ ] Add monitoring
* [ ] Add rate limiting
* [ ] Add CI/CD pipeline
* [ ] Deploy application

# CURRENT_SPRINT.md

# Current Sprint

## Sprint Goal

Strengthen backend architecture and prepare authentication foundation.

---

# ACTIVE TASKS

## DTO Refactor

* [ ] Ensure all controllers use DTOs
* [ ] Remove direct entity exposure
* [ ] Add response DTOs

---

## Validation

* [ ] Add @Valid in controllers
* [ ] Add validation annotations
* [ ] Add centralized validation error handling

---

## Exception Handling

* [ ] Create GlobalExceptionHandler
* [ ] Create UserNotFoundException
* [ ] Create NoteNotFoundException

---

## User Authentication Foundation

* [ ] Add passwordHash field
* [ ] Add PasswordEncoder bean
* [ ] Add register endpoint
* [ ] Add login endpoint

---

## Code Quality

* [ ] Remove unused code
* [ ] Improve naming consistency
* [ ] Add logging in services
* [ ] Add timestamps to entities

---

# IMPORTANT RULES FOR THIS SPRINT

DO NOT START:

* WebSockets
* Followers
* Notifications
* AI features
* GraphQL
* Multiple DBs
* Realtime sync

Finish the current foundation first.

# ARCHITECTURE_NOTES.md

# Current Architecture

## High-Level Flow

Controller
→ Service
→ Repository
→ Database

---

# Layer Responsibilities

## Controller Layer

Responsible for:

* HTTP request handling
* Validation triggering
* Request/response DTO handling
* HTTP status codes

Should NOT contain:

* business logic
* database logic

---

## Service Layer

Responsible for:

* business logic
* orchestration
* validation rules
* permission checks
* transformations

Should NOT:

* directly expose persistence concerns

---

## Repository Layer

Responsible for:

* database operations
* queries
* persistence abstraction

Should remain simple.

---

# Entity Design

## User

Fields:

* id
* username
* email
* passwordHash
* createdAt

---

## Note

Fields:

* id
* title
* content
* ownerId
* collaboratorIds
* createdAt
* updatedAt
* version

---

# Authorization Rules

## Note Access

### Owner

Can:

* read
* edit
* delete
* share

### Collaborator

Can:

* read
* edit

Cannot:

* delete
* transfer ownership

---

# Design Principles

* Prefer simplicity over overengineering
* Build incrementally
* Finish features before expanding scope
* Optimize after correctness
* Keep architecture understandable

# ENGINEERING_RULES.md

# Personal Engineering Rules

## Rule 1 — One Active Sprint

Do not work on multiple major systems simultaneously.

---

## Rule 2 — Finish Before Expanding

Complete current feature before starting another shiny feature.

---

## Rule 3 — Separate Vision From Execution

Ideas belong in backlog.
Only current sprint gets implemented.

---

## Rule 4 — Understand Every Line

Do not blindly copy generated code.
Always understand implementation.

---

## Rule 5 — Learn Through Implementation

Discussion is useful.
Actual coding builds engineering intuition.

---

## Rule 6 — Keep Curiosity Alive

Exploration is allowed.
But main project progress must continue.

---

## Rule 7 — Optimize for Depth

Depth beats random technology collection.

---

## Rule 8 — Build Systems, Not Tutorials

Focus on:

* architecture
* maintainability
* extensibility
* correctness

Not just feature completion.

---

# Success Metric

A successful project is NOT:

* maximum technologies used
* most complex stack

A successful project IS:

* clean architecture
* completed systems
* maintainable code
* clear reasoning
* progressive engineering depth
