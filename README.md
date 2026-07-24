# 🛒 E-Commerce Backend API

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-green)
![JWT](https://img.shields.io/badge/Auth-JWT-blue)
![Hibernate](https://img.shields.io/badge/Hibernate-JPA-brown)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Maven](https://img.shields.io/badge/Build-Maven-red)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A production-style **RESTful E-Commerce Backend** built using **Spring Boot** following enterprise development practices such as **Layered Architecture**, **JWT Authentication**, **Role-Based Authorization**, **DTO Pattern**, **Global Exception Handling**, **Bean Validation**, and **Spring Data JPA**.

This project is being developed feature-by-feature using **Git Feature Branch Workflow**, **Pull Requests**, and clean commit history to simulate a real software development lifecycle.

---

# 🚀 Tech Stack

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

# 🏗 Project Architecture

```
                Client
                   │
                   ▼
            REST Controller
                   │
                   ▼
             Service Layer
                   │
                   ▼
           Repository Layer
                   │
                   ▼
              MySQL Database
```

The project follows a layered architecture.

- **Controller** → Handles HTTP Requests & Responses
- **Service** → Business Logic
- **Repository** → Database Operations
- **DTO** → Request & Response Objects
- **Entity** → Database Tables
- **Exception** → Global Exception Handling

---

# 🔐 Authentication & Authorization

Implemented using **Spring Security + JWT**.

### Features

- User Registration
- User Login
- BCrypt Password Encryption
- JWT Token Generation
- JWT Authentication Filter
- Stateless Authentication
- Role-Based Authorization

### Roles

- ROLE_ADMIN
- ROLE_USER

---

# 📦 User Module

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/users/register` | Register User |
| POST | `/users/login` | Login User |
| GET | `/users` | Get All Users |
| GET | `/users/{id}` | Get User By ID |
| PUT | `/users/{id}` | Update User |
| DELETE | `/users/{id}` | Delete User |

---

# 📦 Product Module

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/products` | Create Product |
| GET | `/products` | Get All Products |
| GET | `/products/{id}` | Get Product By ID |
| PUT | `/products/{id}` | Update Product |
| DELETE | `/products/{id}` | Delete Product |

---

# 📂 Category Module

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/categories` | Create Category |
| GET | `/categories` | Get All Categories |
| GET | `/categories/{id}` | Get Category By ID |
| PUT | `/categories/{id}` | Update Category |
| DELETE | `/categories/{id}` | Delete Category |

---

# 🛒 Cart Module

### ✅ Implemented

### Add Product to Cart

**POST** `/cart/add`

Features

- Automatically creates cart for first-time users
- Adds product to cart
- Updates quantity if product already exists
- Prevents duplicate cart items
- Uses proper JPA relationships
- Clean service layer implementation


# 🗄 Database Relationships

## User ↔ Cart

```
User
   │
   └────── OneToOne ────── Cart
```

---

## Cart ↔ CartItem

```
Cart
   │
   └────── OneToMany ────── CartItem
```

---

## Product ↔ Category

```
Category
      │
      └────── OneToMany ────── Product
```

---

## CartItem ↔ Product

```
CartItem
      │
      └────── ManyToOne ────── Product
```

---

# ✅ Features Implemented

- JWT Authentication
- Role-Based Authorization
- User Registration & Login
- User CRUD
- Product CRUD
- Category CRUD
- Product ↔ Category Relationship
- Cart ↔ CartItem Relationship
- Add Product to Cart API
- DTO Pattern
- Bean Validation
- Global Exception Handling
- Custom Exceptions
- BCrypt Password Encryption
- Repository Pattern
- Layered Architecture
- Feature Branch Workflow
- Pull Request Workflow

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
    ├── payload
    └── util
```

---

# 🧪 API Testing

All APIs are manually tested using **Postman**.

Implemented collections include:

- Authentication APIs
- User APIs
- Product APIs
- Category APIs
- Cart APIs

JWT authentication is managed using **Postman Environment Variables**.

---

# ⚙️ Setup

### Clone Repository

```bash
git clone https://github.com/arpitjha461/E-commerce-Backend-Project.git
```

### Open Project

Import as Maven Project

### Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

### Run Application

Run

```
EcommerceApplication.java
```

Application starts at

```
http://localhost:8765
```

---

# 📊 Project Status

| Module | Status |
|---------|--------|
| Authentication | ✅ Completed |
| User Module | ✅ Completed |
| Product Module | ✅ Completed |
| Category Module | ✅ Completed |
| Product-Category Relationship | ✅ Completed |
| Cart Module | 🚧 In Progress |
| Order Module | ⏳ Planned |
| Payment Module | ⏳ Planned |
| Wishlist | ⏳ Planned |
| Reviews | ⏳ Planned |

---

# 🚀 Upcoming Features

- Get Cart API
- Update Cart Quantity
- Remove Cart Item
- Clear Cart
- Wishlist
- Address Module
- Order Management
- Payment Integration
- Product Reviews
- Swagger / OpenAPI
- Docker
- Unit Testing (JUnit + Mockito)
- GitHub Actions (CI/CD)
- AWS Deployment

---

# 📚 Learning Outcomes

This project demonstrates practical implementation of:

- Layered Architecture
- REST API Design
- DTO Pattern
- Repository Pattern
- Service Layer Pattern
- Spring Security
- JWT Authentication
- Bean Validation
- Global Exception Handling
- Entity Relationships
- JPA & Hibernate
- Clean Code Principles
- Git Feature Branch Workflow
- Pull Request Workflow

---

# 👨‍💻 Author

**Arpit Vishwakarma**

🔗 GitHub  
https://github.com/arpitjha461

🔗 LinkedIn  
https://www.linkedin.com/in/arpitvishw

---

⭐ If you found this project helpful, consider giving it a Star.
