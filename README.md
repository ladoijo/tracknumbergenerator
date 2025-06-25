# Track Number Generator

A Spring Boot-based microservice for generating and managing tracking numbers with validation and caching capabilities.

## 🚀 Tech Stack

- **Java 21** - Primary programming language
- **Spring Boot 3.5.3** - Application framework
- **Spring Web** - RESTful web services
- **Spring Validation** - Request validation
- **Guava** - Core Java libraries
- **Lombok** - Reduced boilerplate code
- **Actuator** - Application monitoring

## 📋 Prerequisites

- JDK 21 or higher
- Gradle 8.0+ (included in the project as `gradlew`)
- Docker (Optional, install Docker only if you need it to run the app in a container.)
- Internet connection (for downloading dependencies)

## 🛠️ Setup

**Clone the repository**

   ```bash
   git clone https://github.com/ladoijo/tracknumbergenerator.git
   cd tracknumbergenerator
   ```

### No Docker

1. **Build the project**
   ```bash
   ./gradlew build
   ```

2. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

### With Docker

1. **Build the Docker image**
   ```bash
   docker build -t tracknumbergenerator .
   ```
2. **Run the Docker container**
   ```bash
   docker run -p 8080:8080 tracknumbergenerator
   ```

The application will start on `http://localhost:8080` by default.

## 🚦 API Endpoints

### Generate Track Number

```
GET /api/v1/next-tracking-number

# Example
GET http://localhost:8080/api/v1/next-tracking-number?origin_country_id=ID&destination_country_id=MY&weight=4&created_at=2025-06-24T15%3A30%3A45%2B07%3A00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox Logistics&customer_slug=redbox-logistics
```

**Request Parameters:**

| Parameter              | Type          | Required | Description                                                  | Example                                |
|------------------------|---------------|----------|--------------------------------------------------------------|----------------------------------------|
| origin_country_id      | String        | Yes      | The order’s origin country code in ISO 3166-1 alpha-2 format | MY                                     |
| destination_country_id | String        | Yes      | The order’s origin country code in ISO 3166-1 alpha-2 format | ID                                     |
| weight                 | Decimal       | Yes      | The order’s weight in kilograms, up to three decimal places  | "2.123"                                |
| created_at             | String)       | Yes      | The order’s creation timestamp in RFC 3339 format            | "2024-06-25T01:20:30+07:00"            |
| customer_id            | String (UUID) | Yes      | The customer’s UUID                                          | "de619854-b59b-425e-9db4-943979e1bd49" |
| customer_name          | String        | Yes      | Full name of the customer                                    | "John Doe"                             |
| customer_slug          | String        | Yes      | The customer’s name in slug-case/kebab-case                  | "john-doe"                             |

**Response:**

```json
{
  "code": 200,
  "status": "OK",
  "message": "Request successful",
  "timestamp": "2025-06-24T17:32:10.942421Z",
  "data": {
    "track_number": "7GRHFD5K9EHZEHPE",
    "created_at": "2025-06-25T00:32:10.942398+07:00"
  },
  "errors": null
}
```

## 🔍 Project Structure

```
src/
├── main/
│   ├── java/com/hadyan/tracknumbergenerator/
│   │   ├── cache/          # Caching implementation
│   │   ├── config/         # Configuration
│   │   ├── constant/       # Constants
│   │   ├── controller/     # REST controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Handler or custom exceptions
│   │   ├── service/        # Business logic
│   │   ├── util/           # Utility classes
│   │   └── TrackNumberGeneratorApplication.java  # Main application class
└── └── resources/
        └── application.properties  # Application configuration
```

## ⚙️ Configuration

Application properties can be configured in `src/main/resources/application.properties`.