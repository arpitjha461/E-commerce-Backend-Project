# E-Commerce Backend

A RESTful e-commerce backend built with **Java 17, Spring Boot, Spring Security, JWT, Spring Data JPA, MySQL, and MongoDB**. The application exposes APIs for user management, product and category management, shopping carts, orders, payments, and inventory.

> This repository contains the backend application only. It does not include a frontend storefront.

## Table of contents

- [Overview](#overview)
- [Features](#features)
- [Technology stack](#technology-stack)
- [Architecture](#architecture)
- [Project structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting started](#getting-started)
- [Configuration](#configuration)
- [Running the application](#running-the-application)
- [Authentication and authorization](#authentication-and-authorization)
- [API reference](#api-reference)
- [Business workflows](#business-workflows)
- [Error handling and validation](#error-handling-and-validation)
- [Testing](#testing)
- [Git workflow](#git-workflow)
- [Known implementation notes](#known-implementation-notes)
- [Roadmap](#roadmap)
- [Author](#author)

## Overview

The project demonstrates the design and implementation of a layered Spring Boot application. It separates HTTP endpoints, business logic, persistence, DTOs, domain entities, security, and exception handling.

The application uses:
- **MySQL** with Spring Data JPA / Hibernate for relational application data.
- **MongoDB** through the Spring Data MongoDB dependency; the configured database is `ecommerce_logs`.
- **JWT** for stateless request authentication.
- **BCrypt** for password hashing.
- **Maven** for dependency and build management.

## Features

| Area | Capabilities |
|---|---|
| Users | Registration, login, user lookup, update, and administrator-controlled listing/deletion |
| Authentication | JWT generation and request authentication; stateless Spring Security configuration |
| Authorization | Role-based access for administrator operations |
| Products | Create, list, retrieve, update, and delete products |
| Categories | Create, list, retrieve, update, and delete categories |
| Cart | Add products, retrieve a cart, change item quantity, remove an item |
| Orders | Place an order from the authenticated user's cart, view orders, cancel an order, update order status |
| Payments | Create a payment record for an order and complete the simulated payment workflow |
| Inventory | Set available stock, reserve stock, retrieve inventory, and integrate stock handling with order/payment operations |
| API errors | Centralized exception handling and validation responses |

## Technology stack

| Technology | Use |
|---|---|
| Java 17 | Application language |
| Spring Boot 3.5.15 | Application framework |
| Spring Web | REST controllers |
| Spring Data JPA and Hibernate | Relational persistence / ORM |
| MySQL | Primary relational database |
| Spring Data MongoDB | MongoDB connectivity |
| Spring Security | Authentication and authorization |
| JSON Web Token (JJWT 0.12.5) | Token handling |
| BCrypt | Password hashing |
| Jakarta Validation | Request validation |
| Maven | Build and dependency management |
| Lombok | Boilerplate reduction |
| JUnit / Spring Boot Test | Test dependencies |
| Spring Security Test | Security testing support |
| Postman | Manual API verification |

## Architecture

The application follows a layered architecture:

```text
HTTP Client (Postman / API consumer)
              |
              v
       REST Controllers
              |
              v
          Services
       (business rules)
              |
              v
        Repositories
              |
       +------+------+
       |             |
       v             v
     MySQL         MongoDB
 (JPA / Hibernate) (Spring Data)
```

Cross-cutting components include:

- **DTOs:** request and response models keep API contracts separate from persistence entities.
- **Security:** Spring Security configuration, JWT filter, user-details service, and JWT utility.
- **Validation:** Jakarta Validation annotations on incoming request DTOs.
- **Exception handling:** domain exceptions and a centralized global exception handler.
- **Transactions:** service-level transaction boundaries for operations that must succeed or roll back together.

## Project structure

The repository root contains the Spring Boot project in the `ecommerce/` directory.

```text
E-commerce-Backend-Project/
├── README.md
└── ecommerce/
    ├── pom.xml
    ├── mvnw
    ├── mvnw.cmd
    └── src/
        ├── main/
        │   ├── java/com/arpit/ecommerce/
        │   │   ├── EcommerceApplication.java
        │   │   ├── config/
        │   │   │   └── SecurityConfig.java
        │   │   ├── controller/
        │   │   │   ├── CartController.java
        │   │   │   ├── CategoryController.java
        │   │   │   ├── InventoryController.java
        │   │   │   ├── OrderController.java
        │   │   │   ├── PaymentController.java
        │   │   │   ├── ProductController.java
        │   │   │   └── UserController.java
        │   │   ├── dto/
        │   │   │   ├── request/
        │   │   │   └── response/
        │   │   ├── entity/
        │   │   ├── enums/
        │   │   ├── exception/
        │   │   ├── payload/
        │   │   ├── repository/
        │   │   ├── security/
        │   │   │   └── JwtAuthenticationFilter.java
        │   │   ├── service/
        │   │   └── util/
        │   └── resources/
        │       └── application.properties
        └── test/
            └── java/com/arpit/ecommerce/
                ├── EcommerceApplicationTests.java
                └── config/
                    └── SecurityConfigTest.java
```

## Prerequisites

Install the following before running the application:

1. **JDK 17**.
2. **MySQL Server**.
3. **MongoDB** (only required for features that use the MongoDB integration).
4. Git.
5. An IDE such as IntelliJ IDEA or a terminal.

Maven Wrapper scripts are included, so a separate Maven installation is not required.

## Getting started

### 1. Clone the repository

```bash
git clone https://github.com/arpitjha461/E-commerce-Backend-Project.git
cd E-commerce-Backend-Project
```

### 2. Open the Spring Boot project

The Maven project is inside the `ecommerce/` directory:

```bash
cd ecommerce
```

### 3. Create the MySQL database

Connect to your MySQL server and run:

```sql
CREATE DATABASE ecommerce_db;
```

Create or select a MySQL user with permission to access this database. Configure the username and password using the environment variables described below.

### 4. Start MongoDB

Start a local MongoDB instance if you intend to use the configured MongoDB integration. The default local database name in the configuration is `ecommerce_logs`.

### 5. Configure the application

Set the database credentials and JWT secret using environment variables. See [Configuration](#configuration).

### 6. Build and run

Windows:

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

macOS / Linux:

```bash
chmod +x ./mvnw
./mvnw clean package
./mvnw spring-boot:run
```

When startup succeeds, the API is available at:

```text
http://localhost:8765
```

## Configuration

Application configuration is currently held in `ecommerce/src/main/resources/application.properties`.

The application uses these settings:

| Property | Purpose | Local default |
|---|---|---|
| `server.port` | HTTP port | `8765` |
| `spring.datasource.url` | MySQL JDBC URL | `jdbc:mysql://localhost:3306/ecommerce_db` |
| `spring.datasource.username` | MySQL username | Set locally |
| `spring.datasource.password` | MySQL password | Set locally |
| `spring.jpa.hibernate.ddl-auto` | Hibernate schema behavior | `update` |
| `jwt.secret` | JWT signing secret | Must be supplied securely |
| `jwt.expiration` | Token lifetime in milliseconds | `3000000` (50 minutes) |
| `spring.data.mongodb.uri` | MongoDB connection URI | `mongodb://localhost:27017/ecommerce_logs` |

**Security note:** Never commit database passwords, JWT signing secrets, API keys, or other credentials. Keep real values outside source control. The repository's checked-in configuration should use placeholders or environment-variable references, and any credential that has already been committed should be rotated.

For example, configure the following environment variables in your shell or IDE run configuration:

```text
DB_URL=jdbc:mysql://localhost:3306/ecommerce_db
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=replace_with_a_long_random_secret
MONGODB_URI=mongodb://localhost:27017/ecommerce_logs
```

The application must be configured to read these variables (for example, with Spring Boot property placeholders) before relying on them. Do not assume that defining shell variables alone overrides hard-coded values.

## Running the application

From the `ecommerce/` directory:

```bash
# Run tests
./mvnw test

# Build the application
./mvnw clean package

# Start the application
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd` instead of `./mvnw`.

The configured application port is **8765**. Example base URL:

```text
http://localhost:8765
```

## Authentication and authorization

The application uses stateless JWT authentication.

### Authentication flow

```text
Register / Login
      |
      v
Validate credentials
      |
      v
Create JWT
      |
      v
Client receives token
      |
      v
Client sends Authorization header
      |
      v
JwtAuthenticationFilter validates token
      |
      v
Spring Security authorizes request
      |
      v
Controller handles request
```

For protected endpoints, include:

```http
Authorization: Bearer <your_jwt_token>
Content-Type: application/json
```

### Public endpoints

- `POST /users/register`
- `POST /users/login`

### Role-based rules

The current security configuration explicitly restricts several operations:

| Operation | Required role |
|---|---|
| List all users | `ADMIN` |
| Delete users | `ADMIN` |
| Read categories | `USER` or `ADMIN` |
| Create, update, or delete categories | `ADMIN` |
| Update order status | `ADMIN` |
| Complete a payment | `ADMIN` |
| Update or reserve inventory using PUT endpoints | `ADMIN` |
| Other non-public endpoints | Authenticated user, subject to service-level ownership checks |

Product write operations and some other operations require authentication under the current catch-all security rule. Review controller and service logic for resource ownership and business restrictions before exposing the API publicly.

## API reference

Base URL:

```text
http://localhost:8765
```

All endpoints below are relative to the base URL. Protected endpoints require a valid bearer token. Request DTO validation may impose additional field constraints.

### Users and authentication

| Method | Endpoint | Purpose | Access |
|---|---|---|---|
| POST | `/users/register` | Register a user | Public |
| POST | `/users/login` | Authenticate and receive JWT | Public |
| GET | `/users` | List users | ADMIN |
| GET | `/users/{id}` | Retrieve user by ID | Authenticated |
| PUT | `/users/{id}` | Update user | Authenticated |
| DELETE | `/users/{id}` | Delete user | ADMIN |

Example registration request (adjust fields to match `UserRequestDTO`):

```json
{
  "name": "Example User",
  "email": "user@example.com",
  "password": "ChangeMe@123",
  "role": "USER"
}
```

Example login request:

```json
{
  "email": "user@example.com",
  "password": "ChangeMe@123"
}
```

Use the token returned by login in the Authorization header for protected requests. Do not use real personal credentials in examples or source control.

### Products

| Method | Endpoint | Purpose | Access |
|---|---|---|---|
| POST | `/products` | Create product | Authenticated |
| GET | `/products` | List products | Authenticated |
| GET | `/products/{id}` | Retrieve product | Authenticated |
| PUT | `/products/{id}` | Update product | Authenticated |
| DELETE | `/products/{id}` | Delete product | Authenticated |

### Categories

| Method | Endpoint | Purpose | Access |
|---|---|---|---|
| POST | `/categories` | Create category | ADMIN |
| GET | `/categories` | List categories | USER / ADMIN |
| GET | `/categories/{id}` | Retrieve category | USER / ADMIN |
| PUT | `/categories/{id}` | Update category | ADMIN |
| DELETE | `/categories/{id}` | Delete category | ADMIN |

### Cart

| Method | Endpoint | Purpose | Access |
|---|---|---|---|
| POST | `/cart/add` | Add a product to a cart | Authenticated |
| GET | `/cart/{userid}` | Retrieve a user's cart | Authenticated |
| PUT | `/cart/items/{cartItemId}` | Update cart item quantity | Authenticated |
| DELETE | `/cart/items/{cartItemId}` | Remove a cart item | Authenticated |

Example add-to-cart request (field names follow `AddToCartRequestDTO`):

```json
{
  "userId": 1,
  "productId": 1,
  "quantity": 2
}
```

Example quantity update:

```json
{
  "quantity": 3
}
```

### Orders

| Method | Endpoint | Purpose | Access |
|---|---|---|---|
| POST | `/orders/place` | Place an order from the current user's cart | Authenticated |
| GET | `/orders/my-orders` | List the authenticated user's orders | Authenticated |
| GET | `/orders/{orderId}` | Retrieve an order (ownership checked by service) | Authenticated |
| PUT | `/orders/{orderId}/cancel` | Cancel an eligible order | Authenticated |
| PUT | `/orders/{orderId}/status` | Update order status | ADMIN |

Order placement uses the authenticated user's cart; the controller does not accept a request body for this endpoint.

Example order-status request (check the accepted enum values in `UpdateOrderStatusRequestDTO` and `OrderStatus`):

```json
{
  "status": "SHIPPED"
}
```

### Payments

| Method | Endpoint | Purpose | Access |
|---|---|---|---|
| POST | `/payments/{orderId}` | Create payment for an order | Authenticated; order ownership is checked |
| PUT | `/payments/{paymentId}/complete` | Complete simulated payment | ADMIN |

The current payment controller does not accept a request body for payment creation or completion. The service derives the amount from the order and creates a server-controlled transaction record. This is a simulated payment flow, not a connection to a real payment processor.

### Inventory

| Method | Endpoint | Purpose | Access |
|---|---|---|---|
| PUT | `/inventory/{id}` | Set available stock | ADMIN |
| PUT | `/inventory/{productId}/reserve` | Reserve stock | ADMIN under current URL security rule |
| GET | `/inventory/{productId}` | Retrieve inventory for a product | Authenticated |

Example stock update (confirm exact field validation in `InventoryRequestDTO`):

```json
{
  "availableStock": 20
}
```

Example stock reservation (confirm exact field validation in `ReserveStockRequestDTO`):

```json
{
  "quantity": 2
}
```

### Quick API checklist

- Register and log in.
- Copy the JWT from the login response.
- Add `Authorization: Bearer <token>` to protected requests.
- Create or retrieve products and categories.
- Add a product to the cart.
- Place an order.
- Create a payment record for the order.
- Use an administrator token to complete the simulated payment.
- Retrieve inventory and verify the resulting stock values.

## Business workflows

### Order placement

At a high level, the order service works with the authenticated user's cart:

```text
Authenticated user
       |
       v
Load user's cart and cart items
       |
       v
Validate cart and requested products
       |
       v
Create order and order items
       |
       v
Calculate order total
       |
       v
Persist order
       |
       v
Return order response
```

The order and inventory services should be reviewed together when changing checkout behavior, particularly transaction boundaries and cart-clearing behavior.

### Payment lifecycle

The payment implementation is simulated:

```text
Create payment for an eligible order
              |
              v
       Payment: PENDING
              |
              v
Administrator completes payment
              |
              v
        Payment: SUCCESS
              |
              v
        Order: CONFIRMED
```

The service checks order ownership during payment creation and prevents duplicate payment records for an order. The payment amount and transaction identifier are generated/derived on the server rather than trusted from a client request. A real gateway integration is not included.

### Inventory lifecycle

Inventory tracks available and reserved quantities separately:

```text
             Product
                |
                | one-to-one
                v
             Inventory
          /             \
 availableStock     reservedStock
```

When stock is reserved, available quantity decreases and reserved quantity increases. When a sale is finalized, reserved quantity decreases; the available quantity is not deducted a second time. A reservation release returns reserved quantity to available quantity.

The inventory repository uses a pessimistic write lock for protected stock-changing operations, and transactional service methods are used to help preserve consistency during concurrent updates.

## Error handling and validation

The project uses Jakarta Validation for request DTOs and centralized exception handling through `GlobalExceptionHandler`.

Domain exceptions in the codebase include examples such as:

- `UserNotFoundException`
- `InvalidCredentialsException`
- `ProductNotFoundException`
- `CategoryNotFoundException`
- `OrderNotFoundException`
- `InvalidOrderStatusException`
- `PaymentNotFoundException`
- `PaymentAlreadyExistsException`
- `UnauthorizedPaymentException`
- `InvalidStatusForPaymentException`
- `InventoryNotFoundException`
- `InsufficientStockException`
- `InvalidReleaseOperationException`
- `InvalidSaleOperationException`

The global exception handler maps application and validation failures to structured API error responses. Exact status codes depend on the specific exception handler.

## Testing

Run the automated tests from the `ecommerce/` directory:

```bash
./mvnw test
```

The repository currently includes Spring Boot application test scaffolding and a security configuration test. The Maven file includes Spring Boot Test and Spring Security Test dependencies.

API workflows can also be exercised manually with Postman. Important scenarios include:

- Registration and login.
- Missing, malformed, or invalid JWT.
- Access by USER versus ADMIN.
- Product and category CRUD operations.
- Cart add, update, and remove behavior.
- Order ownership and cancellation rules.
- Duplicate payment and invalid payment-state handling.
- Insufficient stock and inventory reservation behavior.
- Error and validation responses.

Do not interpret the presence of a test dependency or a manual test note as proof that every scenario has an automated test. Add and run dedicated unit and integration tests as the test suite grows.

## Git workflow

The repository uses feature branches for incremental development. A typical workflow is:

```bash
git checkout main
git pull origin main
git checkout -b feature/your-feature

# Make and verify your changes
git add .
git commit -m "Describe the change"
git push -u origin feature/your-feature
```

Open a pull request to merge a reviewed feature into `main`.

## Known implementation notes

- The Spring Boot application is located in `ecommerce/`, not at the repository root.
- The configured application port is `8765`.
- The checked-in `application.properties` has historically contained local connection settings and credentials. Replace secrets with environment-based configuration and rotate any credential that was committed.
- MongoDB is declared as a dependency and a MongoDB URI is configured. Confirm which application services currently write to MongoDB before describing it as an active logging or auditing feature.
- Swagger/OpenAPI, Docker, CI/CD, AWS deployment, Redis, Kafka/RabbitMQ, Elasticsearch, and expanded automated test coverage should be described as future work unless their implementation is added to the repository.
- Payment processing is simulated. No real payment gateway is configured.
- Verify endpoint request DTOs and enum values against source before using the example payloads in an external client.

## Roadmap

Potential next steps:

- Move database credentials and JWT secrets to environment-based configuration.
- Add comprehensive service, controller, repository, and security tests.
- Add API documentation with an OpenAPI/Swagger implementation.
- Add Dockerfile and Docker Compose for local dependencies.
- Add CI checks with GitHub Actions.
- Add production profiles, database migrations, and deployment documentation.
- Integrate a real payment provider with appropriate security and webhook validation, if required.
- Expand observability and persistence for audit or order events where needed.

## Author

**Arpit Vishwakarma**

- GitHub: [@arpitjha461](https://github.com/arpitjha461)
- Project: [E-commerce-Backend-Project](https://github.com/arpitjha461/E-commerce-Backend-Project)

---

This project is developed for learning and demonstrating backend engineering concepts with Java and the Spring ecosystem.
