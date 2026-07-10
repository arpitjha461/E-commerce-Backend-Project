# 🛒 E-Commerce Backend API

A production-ready RESTful E-Commerce Backend built using **Spring Boot**, following a layered architecture with **JWT Authentication**, **Role-Based Authorization**, **DTO Pattern**, **Global Exception Handling**, and **MySQL**.

This project is being developed feature-by-feature using **GitHub Feature Branch Workflow** and **Pull Requests**.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Postman
- Git & GitHub

---

## 📂 Project Architecture

```
Controller
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
Database (MySQL)
```

The project follows a clean layered architecture:

- Controller → Handles HTTP Requests
- Service → Business Logic
- Repository → Database Operations
- DTO → Data Transfer between Client and Server
- Entity → Database Tables
- Exception → Global Exception Handling

---

# 🔐 Authentication & Authorization

Implemented using **Spring Security + JWT**.

### Features

- User Registration
- User Login
- BCrypt Password Encryption
- JWT Token Generation
- JWT Authentication Filter
- Role-Based Authorization

### Roles

- ROLE_ADMIN
- ROLE_USER

---

# 📦 Product Module

Implemented complete CRUD operations.

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /products | Create Product |
| GET | /products | Get All Products |
| GET | /products/{id} | Get Product By ID |
| PUT | /products/{id} | Update Product |
| DELETE | /products/{id} | Delete Product |

---

# 👤 User Module

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /users/register | Register User |
| POST | /users/login | Login User |
| GET | /users | Get All Users |
| GET | /users/{id} | Get User By ID |
| PUT | /users/{id} | Update User |
| DELETE | /users/{id} | Delete User |

---

# ✅ Features Implemented

- User CRUD
- Product CRUD
- DTO Pattern
- Bean Validation
- BCrypt Password Encryption
- JWT Authentication
- Role-Based Authorization
- Global Exception Handling
- Custom Exceptions
- Feature Branch Workflow
- GitHub Pull Requests

---

# 📁 Project Structure

```
src
└── main
    ├── controller
    ├── service
    ├── repository
    ├── entity
    ├── dto
    ├── config
    ├── security
    ├── exception
    └── payload
```

---

# ⚙️ Setup

### Clone Repository

```bash
git clone https://github.com/arpitjha461/E-commerce-Backend-Project.git
```

### Open Project

```
Import as Maven Project
```

### Configure Database

Update:

```
application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### Run

```
Run EcommerceApplication.java
```

Server starts on

```
http://localhost:8765
```

---

# 🧪 API Testing

All APIs were tested using **Postman**.

---

# 📌 Upcoming Modules

- Category Module
- Cart Module
- Order Module
- Payment Module
- Swagger Documentation
- Docker
- Unit Testing
- AWS Deployment
- CI/CD (GitHub Actions)

---

# 👨‍💻 Author

**Arpit Vishwakarma**

GitHub: https://github.com/arpitjha461

LinkedIn: *(Add your LinkedIn profile here)*

---

⭐ If you found this project useful, consider giving it a star!
