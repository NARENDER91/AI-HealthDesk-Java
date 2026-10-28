# AI HealthDesk — Java Edition

A healthcare administration prototype built with Java 17, Spring Boot, Spring Data JPA/Hibernate, PostgreSQL, HTML, CSS, JavaScript, and optional live Gemini API integration.

## Features
- General healthcare information FAQ with server-side Gemini API integration.
- Appointment request form and validation.
- PostgreSQL persistence using JPA/Hibernate.
- Dashboard to view requests and update statuses.
- Prompt documentation and presentation script.

## Safety and limitations
This is a learning prototype, not a medical service. It must not be used to diagnose or treat patients. Use fictional data only. The dashboard currently has no authentication, so do not deploy it publicly with real information. AI answers can be wrong. For personal medical advice, consult a qualified healthcare professional; for an emergency, contact local emergency services.

## Requirements
- Java 17+
- Maven 3.6+ (or use Maven wrapper if you add one)
- PostgreSQL
- Optional Gemini API key for live AI responses

## 1. Create the PostgreSQL database
In pgAdmin Query Tool or psql, run:
```sql
CREATE DATABASE healthdesk;
```

## 2. Configure environment variables (Windows PowerShell)
Set the values for the current terminal session:
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/healthdesk"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="YOUR_POSTGRES_PASSWORD"
$env:GEMINI_API_KEY="YOUR_GEMINI_API_KEY"
```
Do not commit your API key or password to GitHub. If no Gemini API key is configured, the app displays a safe setup message instead of calling the API.

## 3. Run from Eclipse or terminal
Import as an existing Maven project in Eclipse. Ensure Java 17 is selected and Maven dependencies finish downloading.

From the project root:
```powershell
mvn spring-boot:run
```
Then open:
- Website: http://localhost:8080/
- Dashboard: http://localhost:8080/dashboard.html

The JPA configuration creates/updates the table automatically (`spring.jpa.hibernate.ddl-auto=update`) for this prototype.

## 4. Test
1. Submit a request using fictional name/email and a future date.
2. Check that the success message appears.
3. Open Dashboard and verify the request appears.
4. Update the status and refresh to verify persistence.
5. Ask a general administrative FAQ question.
6. Test a personal medical question and verify the response recommends professional advice.
7. Test with no API key and with an invalid key to verify graceful handling.

## Before deployment
- Add authentication and authorization to the dashboard.
- Add CSRF protections as appropriate, rate limiting and stricter security controls.
- Configure a persistent database, secrets management, HTTPS, logs and monitoring.
- Add automated tests and validate API behavior against the current provider documentation.
- Review privacy and applicable healthcare requirements.
- Never collect real patient data in this demo.
