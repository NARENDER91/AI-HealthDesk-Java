# AI HealthDesk Java — Video Presentation Script (2–3 minutes)

Hello, my name is Narender. This is my AI HealthDesk project, built for the AI Growth and Automation Associate assessment.

The goal is to simplify basic healthcare administration through a web application that combines a general-information FAQ assistant with appointment request tracking.

I built the backend using Java and Spring Boot, used Spring Data JPA and Hibernate for persistence, and selected PostgreSQL as the database. The frontend uses HTML, CSS, and JavaScript. The FAQ assistant connects to the Gemini API from the Java backend when an API key is configured.

I used AI tools during development to plan the application structure, help implement individual components, refine the safety instructions, and prepare test cases. I documented the prompts and revisions so the development process can be reviewed.

Now I will demonstrate the project. First, I will enter a general administrative question in the FAQ assistant and show the response. Next, I will submit an appointment request with fictional data. The backend validates the request and stores it in PostgreSQL. I will open the dashboard and update the request status to demonstrate the workflow.

A key challenge in a healthcare-related application is providing useful information without making unsafe medical claims. The FAQ prompt instructs the model not to diagnose or prescribe, to refer personal medical concerns to qualified professionals, and to direct possible emergencies to local emergency services.

This is an educational prototype, not a production healthcare service. Before any real deployment, it would need authentication, stronger privacy and security controls, appropriate clinical review, and more comprehensive testing.

This project demonstrates my Java development, API integration, database persistence, prompt engineering, and practical problem-solving approach.

Thank you for watching.
