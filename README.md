# Tournament Scoreboard

A REST API built with Java and Spring Boot to manage tournament data — teams, players, matches, and a live scoreboard.

**Tech Stack:** Java 17, Spring Boot 3.5, Spring Data JPA, Spring Security, MySQL

---

## Entities

**Player** — belongs to a team. Unique name enforced.

**Team** — tracks total score, matches played, wins, and draws across matches.

**Match** — scheduled between two teams. Moves through three states: SCHEDULED → ONGOING → COMPLETED. Scores update during the match; team stats update on completion.

**User** — stores credentials and role (ROLE_USER or ROLE_ADMIN). Username is the primary key.

---

## Features

- JWT authentication with access token (15 min) and refresh token (7 days)
- Role-based access — admin manages data, users read it
- CRUD for players, teams, and matches
- Match lifecycle management — start, end, score updates, rescheduling
- Scoreboard sorted by total score with rank and win ratio via DTO projection
- Global exception handling via `@ControllerAdvice`
- Input validation with Jakarta Validation
- Pagination on players and matches
- `FetchType.LAZY` on all ManyToOne relationships

---

## Endpoints

### Auth (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /auth/register | Register a new user |
| POST | /auth/login | Login and get access + refresh token |
| POST | /auth/refresh | Get new access token using refresh token |

### Admin Only
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /admin/player | Add a new player |
| DELETE | /admin/player/{id} | Delete a player |
| POST | /admin/team | Add a new team |
| PUT | /admin/team/{teamId} | Rename a team |
| DELETE | /admin/team/{teamId} | Remove a team |
| POST | /admin/team/{teamId}/player/{playerId} | Assign player to team |
| POST | /admin/match/{teamAId}/{teamBId} | Schedule a match between two teams |
| DELETE | /admin/match/{matchId} | Remove a scheduled match |
| PUT | /admin/match/start/{matchId} | Start a match |
| PUT | /admin/match/end/{matchId} | End a match and update team stats |
| PUT | /admin/match/{matchId}/{teamId}/{score} | Update team score in ongoing match |
| PUT | /admin/match/reschedule/{matchId}/{newDateTime} | Reschedule a match |

### Authenticated Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /user/player | Get all players (paginated) |
| GET | /user/player/{id} | Get player by id |
| GET | /user/team | Get all teams |
| GET | /user/team/{id} | Get team by id |
| GET | /user/match | Get all matches (paginated) |
| GET | /user/match/{id} | Get match by id |
| GET | /scoreboard | Live standings sorted by total score |

---

## Setup
 
**Prerequisites:** Java 17, Maven, MySQL
 
**1. Clone the repo**
 
```bash
git clone https://github.com/Manpreet102005/Tournament-Scoreboard-API.git
cd Tournament-Scoreboard-API
```
 
**2. Create a `.env` file in the project root**
 
```
DB_URL=jdbc:mysql://localhost:3306/tournament
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_secret_key_minimum_32_characters
```
 
**3. Create the database**
 
```sql
CREATE DATABASE tournament;
```
 
**4. Run**
 
```bash
mvn spring-boot:run
```
 
Server starts on `http://localhost:8080`. Tables are auto-created by Hibernate on first run.
 
---
 
## Frontend
 
The frontend is a separate vanilla JS + HTML app served statically. It connects to the backend via the REST API and switches between localhost and the Railway URL based on the hostname.
 
Pages: Scoreboard, Matches, Teams, Players, Admin Panel
 
**Frontend repo / deployed at:** [tournament-scoreboard-jet.vercel.app](https://tournament-scoreboard-jet.vercel.app)
 
---
 
## Project Structure
 
```
src/main/java/com/example/
├── Security/        # JWT filter, JWT util, security config
├── User/            # Auth controller, user service, user repo
├── Player/          # Player entity, controller, service, repo, DTOs, exceptions
├── Team/            # Team entity, controller, service, repo, exceptions
├── Match/           # Match entity, controller, service, repo, exceptions
└── Scoreboard/      # Scoreboard controller and DTO projection
```
