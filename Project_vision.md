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
