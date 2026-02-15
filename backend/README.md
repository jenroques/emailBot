# Backend

Spring Boot 3 / Java 17 backend service for the Enterprise Inbox Intelligence Platform.

## Run Locally

```bash
gradle bootRun
```

Active profile for local development:

```bash
gradle bootRun --args='--spring.profiles.active=localdev'
```

Health endpoint:

```text
GET http://localhost:8080/api/health
```
