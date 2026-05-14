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