
---

# Debt Management Service

The Debt Management Service is a microservice designed to handle the tracking, collection, and notification of debts. It is part of a larger system built with multiple microservices, ensuring scalability, flexibility, and modularity. The service integrates with other services like authentication and synchronization to provide a cohesive debt management experience.

## Features

- **Debt Tracking:** Keeps track of debt records for each user, including amount, due date, and status.
- **Debt Collection:** Provides endpoints for users to make payments and view payment history.
- **Notifications:** Sends reminders and notifications via email or SMS when payments are due or overdue.
- **Role-Based Access Control (RBAC):** Ensures different levels of access for admins and users.
- **License Validation:** Middleware checks the validity of user licenses before accessing protected resources.
- **Event-Driven Architecture:** Handles events for debt creation, update, and payment confirmation, ensuring real-time synchronization across services.

## Project Structure

```
backend/
├── debt-service/
│   └── src/
│       ├── controllers/
│       │   └── DebtController.java
│       ├── services/
│       │   └── DebtService.java
│       │   └── NotificationService.java
│       ├── repositories/
│       │   └── DebtRepository.java
│       │   └── PaymentRepository.java
│       ├── events/
│       │   └── DebtCreatedEvent.java
│       │   └── PaymentMadeEvent.java
│       ├── components/
│       │   └── NotificationDispatcher.java
│       ├── config/
│       │   └── SecurityConfig.java
│       │   └── DebtConfig.java
│       └── utils/
│           └── DebtUtils.java
```

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
  - Spring Web
  - Spring Data JPA
  - Spring Security
- **MySQL/PostgreSQL** (Database)
- **JWT** (for authentication)
- **Kafka** (Event-driven messaging)
- **Maven** (Build tool)

## Prerequisites

- Java 17
- Maven 3.x
- MySQL/PostgreSQL (or any other configured database)
- Apache Kafka (for event-driven architecture)
- RabbitMQ (optional for message queues)
- Docker (optional for containerization)

## Setup & Installation

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/debt-management-service.git
cd debt-management-service
```

### 2. Configure the application

Update the `application.properties` file with your database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/debt_management_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
```

Also, configure Kafka or any other external service integrations:

```properties
kafka.bootstrap-servers=localhost:9092
kafka.topic.debtCreated=debt_created
kafka.topic.paymentMade=payment_made
```

### 3. Build the project

Run the following Maven command to build the project:

```bash
mvn clean install
```

### 4. Run the application

Once built, you can run the application using the following command:

```bash
mvn spring-boot:run
```

### 5. Access the APIs

Once the application is running, the following endpoints will be available:

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/debts` | GET | Fetch all debt records |
| `/api/debts/{id}` | GET | Fetch a single debt by ID |
| `/api/debts` | POST | Create a new debt record |
| `/api/debts/{id}` | PUT | Update a debt record |
| `/api/debts/{id}/pay` | POST | Make a payment towards a debt |
| `/api/notifications` | GET | Fetch user notifications |

Swagger documentation can also be enabled for better API interaction.

## Event-Driven Architecture

The system uses Kafka for handling events like `DebtCreatedEvent` and `PaymentMadeEvent` to keep all microservices in sync. This ensures that every change in the debt service is propagated to other services such as the notification system, sync-service, and others.

## License Validation

The service includes middleware for validating user licenses before granting access to protected resources. License keys are generated and validated using `LicenseKeyGenerator` and `LicenseValidator`.

## Contributing

Contributions are welcome! Please create a pull request with a detailed description of your changes.

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m "Add a new feature"`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
