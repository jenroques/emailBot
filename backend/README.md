# Backend

Spring Boot 3 / Java 17 backend service for the Enterprise Inbox Intelligence Platform.

## Run Locally

```bash
./gradlew bootRun
```

Active profile for local development:

```bash
./gradlew bootRun --args='--spring.profiles.active=localdev'
```

Windows PowerShell:

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=localdev"
```

Health endpoint:

```text
GET http://localhost:8080/api/health
```

## Authentication (JWT)

Local development users (profile: `localdev`):

- `admin` / `admin123` (role: `ADMIN`)
- `analyst` / `analyst123` (role: `ANALYST`)

Token login endpoint:

```text
POST /api/auth/login
```

Current user endpoint:

```text
GET /api/auth/me
Authorization: Bearer <accessToken>
```
