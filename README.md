# API Gateway with Spring Cloud Gateway and Eureka Integration

This project demonstrates the implementation of an **API Gateway** using **Spring Cloud Gateway** with **Eureka Discovery Service**. It acts as a single entry point to route requests to multiple microservices: **Question Service** and **Quiz Service**.

## Features
- Centralized API Gateway for routing.
- Service discovery and registration using **Eureka Server**.
- Dynamic routing with `Path` predicates.
- Load Balancing with `lb://` URI scheme.
- Configurable routing via `application.properties`.

## Prerequisites
- Java 17+
- Spring Boot 3.1+
- Maven/Gradle
- Eureka Server and client services up and running.

## Getting Started

### Clone the Repository
git clone <repository-url>

### Configure Application Properties
Modify the application.properties file located in the src/main/resources directory:
# Application details
spring.application.name=api-gateway
server.port=9000

# Eureka client configuration
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Routing configuration for services
spring.cloud.gateway.routes[0].id=question-service
spring.cloud.gateway.routes[0].uri=lb://question-service
spring.cloud.gateway.routes[0].predicates[0]=Path=/question/**

spring.cloud.gateway.routes[1].id=quiz-service
spring.cloud.gateway.routes[1].uri=lb://quiz-service
spring.cloud.gateway.routes[1].predicates[0]=Path=/quiz/**

## API Endpoints

### Gateway Endpoints:

- `http://localhost:9000/question/getScore` → Routes to **Question Service**.
- `http://localhost:9000/quiz/getById/{id}` → Routes to **Quiz Service**.

### Service Endpoints:

- **Question Service:**
    - `http://localhost:8080/question/getScore`

- **Quiz Service:**
    - `http://localhost:8090/quiz/getById/{id}`

## Running the Application

1. Make sure that the **Eureka Server** is running on port `8761`.
2. Start the **Question Service** on port `8080`.
3. Start the **Quiz Service** on port `8090`.
4. Start the **API Gateway** on port `9000`.

## Testing the Gateway

You can test the gateway by accessing the following URLs:
- **For Question Service:**
    - `http://localhost:9000/question/getScore` → Routes to the **Question Service**.

- **For Quiz Service:**
    - `http://localhost:9000/quiz/getById/{id}` → Routes to the **Quiz Service**.

