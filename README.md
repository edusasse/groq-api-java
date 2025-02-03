# Groq Client App

This is a sample Java application that demonstrates how to use the Groq API to create chat completions. The application is built using **Spring Boot** and **Maven**.

## Prerequisites

- **Java 21** or higher
- **Maven 3.6.0** or higher

## Setup

1. **Clone the Repository**

```bash
git clone https://github.com/your-username/groq-client-app.git
cd groq-client-app
```

1.  **Configure Environment Variables**

    Create a `.env` file in the root directory and add your Groq API key:

    dotenv

    Copiar

    `GROQ_API_KEY=your_api_key_here`

2.  **Update Application Configuration**

    Modify the `application.yml` file with your desired configuration:

```yaml
    groq:
      api-key: "${GROQ_API_KEY}"
      model: "deepseek-r1-distill-llama-70b"
      temperature: 0.6
      max-tokens: 8192
      top-p: 0.95
      stream: false`
```

# Build and Run

1.  **Build the Project**

    Use Maven to clean and build the project:
 
```mvn clean install```

2.  **Run the Application**

    Start the application using Spring Boot:
`mvn spring-boot:run`

## Simple Test

The `MainGroqClient` class demonstrates how to use the `GroqClient` to create chat completions. You can modify the `main` method in this class to test different inputs and configurations.

```java
   String apiKey = "<<API KEY>>";
```

# Project Structure

```bash
groq-client-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── edusasse/
│   │   │           ├── MainGroqClient.java             # Main class demonstrating GroqClient usage
│   │   │           ├── spring/
│   │   │           │   ├── client/
│   │   │           │   │   └── GroqClient.java         # Client for interacting with the Groq API
│   │   │           │   ├── config/
│   │   │           │   │   └── GroqProperties.java       # Configuration properties for Groq API integration
│   │   │           │   └── service/
│   │   │           │       └── GroqService.java          # Service class for processing messages using GroqClient
│   │   └── resources/
│   │       └── application.yml                           # Application configuration file
└── pom.xml                                             # Maven project file`
```

# Dependencies

-   **Spring Boot Starter**
-   **Lombok**
-   **Jackson Core**
-   **Jackson Databind**

# License

This project is licensed under the **MIT License**. See the <LICENSE> file for details.