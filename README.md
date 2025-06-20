# Verantwoording Backend

Dit is de backend van het Verantwoording-project.  
Het bevat REST API endpoints voor gebruikersauthenticatie (registreren en inloggen) en een voorbeeld endpoint om gebruikers e-mails op te halen.

## Technologieën

- Java 17+
- Jakarta RESTful Web Services (JAX-RS)
- PostgreSQL als database
- Maven voor dependency management
- JUnit 5 voor unit testing

## API Endpoints

### POST /api/auth/register  
Registreert een nieuwe gebruiker.  
**Request body (JSON):**  
```json
{
  "firstname": "Voornaam",
  "lastname": "Achternaam",
  "email": "email@example.com",
  "password": "wachtwoord"
}
