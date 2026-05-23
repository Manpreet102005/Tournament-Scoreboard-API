#Tournament Scoreboard API

A REST API built with Spring Boot and MySQL to manage tournament data including teams, players, matches, and live scoreboards.

**Tech Stack**

Java, Spring Boot, Spring Data JPA, MySQL

**Entities**

Player, Team, Match

Proper cardinality defined between entities with ManyToOne and OneToMany relationships.

**Features**

- CRUD endpoints for all entities
- Input validation with Jakarta Validation
- Global exception handling via @ControllerAdvice
- Structured error responses using ResponseEntity
- Scoreboard with real-time rank, win ratio, and match stats via DTO projection
- Pagination on Players and Matches endpoints for high-volume data
- FetchType.LAZY on all ManyToOne relationships to avoid unnecessary DB queries

**Status**

Complete — Spring Security and JWT authentication coming next.
