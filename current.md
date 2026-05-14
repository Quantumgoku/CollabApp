
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