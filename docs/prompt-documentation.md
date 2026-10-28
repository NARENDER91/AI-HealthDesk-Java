# AI HealthDesk Java — Prompt Documentation

**Project:** AI HealthDesk — Smart Healthcare Assistant & Appointment Automation  
**Stack:** Java 17, Spring Boot, Spring Data JPA/Hibernate, PostgreSQL, HTML/CSS/JavaScript, Gemini API.

> Keep this document honest. Record the AI tool(s) you actually used, the prompts you actually submitted, and the real revisions and test outcomes. Do not claim an AI prompt was used unless you used it.

## Prompt 1 — Architecture
Act as a senior Java full-stack developer. Design a small healthcare administration prototype named AI HealthDesk using Java 17, Spring Boot, Spring Data JPA/Hibernate, PostgreSQL, HTML, CSS and JavaScript. Include an AI-powered general-information FAQ, appointment request form, database persistence, dashboard and status updates. Return architecture, endpoints, entities, validation rules, project structure, security risks and a practical test plan. Do not build diagnosis or prescription features.

## Prompt 2 — Implementation
Implement the approved design using Spring Boot REST endpoints and a static HTML/CSS/JavaScript frontend. Use a JPA entity and repository for appointment persistence. Validate names, email addresses, request type and appointment date. Use JSON for API requests. Include Maven dependencies, application.properties with environment-variable configuration, README instructions and clear errors. Never hardcode API keys. Keep the dashboard clearly marked as a local demo until authentication is implemented.

## Prompt 3 — AI integration and safety
Implement a server-side integration with the Gemini generateContent API. Read the API key from the GEMINI_API_KEY environment variable and never send the key to browser JavaScript. Use a concise system instruction: provide general health education only, do not diagnose or prescribe, refer personal concerns to qualified clinicians, and advise contacting local emergency services for possible life-threatening symptoms. Handle missing keys, API failures, empty results and long inputs safely.

## Prompt 4 — Debugging (use if needed)
I am building a Java Spring Boot app. Here is the exact error: [paste full error]. Relevant code: [smallest relevant section]. Expected behavior: [describe]. Please identify the root cause, give the smallest safe fix, and provide a test to confirm it. Do not rewrite unrelated files.

## Iteration log
| Date | Prompt/change | Observed issue | Revision | Test result |
|---|---|---|---|---|
| Fill in after testing | | | | |

## Honest implementation notes
- The FAQ service attempts live Gemini API integration when GEMINI_API_KEY is configured.
- Appointment data is stored in PostgreSQL.
- This is a learning prototype. It is not suitable for real patient data.
- The dashboard currently has no authentication. Do not deploy publicly with real data.
