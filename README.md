# 🛒 E-Commerce Backend

A RESTful e-commerce backend application built using **Java, Spring Boot, Spring Security, JWT, Spring Data JPA, and MySQL**.

The project follows a layered architecture and is being developed feature-by-feature using Git feature branches.

---

## 🚀 Project Status

| Module                   | Status         |
| ------------------------ | -------------- |
| User Management          | ✅ Completed    |
| Authentication & JWT     | ✅ Completed    |
| Role-Based Authorization | ✅ Completed    |
| Category Management      | ✅ Completed    |
| Product Management       | ✅ Completed    |
| Cart Management          | ✅ Completed    |
| Order Module             | ✅ Completed    |
| Payment Module           | ✅ Completed   |
| Inventory Management     | ⏳ Planned      |
| MongoDB Integration      | ⏳ Planned      |
| Swagger/OpenAPI          | ⏳ Planned      |
| Unit Testing             | ⏳ Planned      |
| Docker                   | ⏳ Planned      |
| CI/CD                    | ⏳ Planned      |
| AWS Deployment           | ⏳ Planned      |

---

# 🏗️ Architecture

The application follows a layered Spring Boot architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Additional layers:

```text
DTO
Exception Handling
Security
Entity
Validation
```

---

# 🛠️ Technologies Used

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JWT Authentication
* Hibernate
* MySQL
* Jakarta Validation
* Maven
* Git & GitHub
* Postman

### Planned Technologies

* MongoDB
* Docker
* Swagger / OpenAPI
* JUnit
* Mockito
* GitHub Actions
* AWS

---

# 📂 Project Structure

```text
ecommerce/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── arpit/
│   │   │           └── ecommerce/
│   │   │               ├── EcommerceApplication.java
│   │   │               ├── config/
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── controller/
│   │   │               │   ├── CartController.java
│   │   │               │   ├── CategoryController.java
│   │   │               │   ├── ProductController.java
│   │   │               │   └── UserController.java
│   │   │               ├── dto/
│   │   │               │   ├── request/
│   │   │               │   │   ├── AddToCartRequestDTO.java
│   │   │               │   │   ├── CategoryRequestDTO.java
│   │   │               │   │   ├── LoginRequestDTO.java
│   │   │               │   │   ├── ProductRequestDTO.java
│   │   │               │   │   ├── UpdateCartQuantityRequestDTO.java
│   │   │               │   │   └── UserRequestDTO.java
│   │   │               │   └── response/
│   │   │               │       ├── CartItemResponseDTO.java
│   │   │               │       ├── CartResponseDTO.java
│   │   │               │       ├── CategoryResponseDTO.java
│   │   │               │       ├── LoginResponseDTO.java
│   │   │               │       ├── ProductResponseDTO.java
│   │   │               │       └── UserResponseDTO.java
│   │   │               ├── entity/
│   │   │               │   ├── Cart.java
│   │   │               │   ├── CartItem.java
│   │   │               │   ├── Category.java
│   │   │               │   ├── Order.java
│   │   │               │   ├── OrderItem.java
│   │   │               │   ├── Product.java
│   │   │               │   └── User.java
│   │   │               ├── enums/
│   │   │               │   └── OrderStatus.java
│   │   │               ├── exception/
│   │   │               │   ├── CartItemNotFoundException.java
│   │   │               │   ├── CategoryNotFoundException.java
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   ├── InvalidCredentialsException.java
│   │   │               │   ├── ProductNotFoundException.java
│   │   │               │   └── UserNotFoundException.java
│   │   │               ├── payload/
│   │   │               │   └── ApiError.java
│   │   │               ├── repository/
│   │   │               │   ├── CartItemRepository.java
│   │   │               │   ├── CartRepository.java
│   │   │               │   ├── CategoryRepository.java
│   │   │               │   ├── OrderItemRepository.java
│   │   │               │   ├── OrderRepository.java
│   │   │               │   ├── ProductRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── security/
│   │   │               │   └── JwtAuthenticationFilter.java
│   │   │               ├── service/
│   │   │               │   ├── CartService.java
│   │   │               │   ├── CategoryService.java
│   │   │               │   ├── CustomUserDetailsService.java
│   │   │               │   ├── ProductService.java
│   │   │               │   └── UserService.java
│   │   │               └── util/
│   │   │                   └── JwtUtil.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/
│               └── arpit/
│                   └── ecommerce/
│                       ├── EcommerceApplicationTests.java
│                       └── config/
│                           └── SecurityConfigTest.java
├── pom.xml
├── mvnw
└── mvnw.cmd
```

---

# 🔐 Authentication & Security

The application uses **JWT-based authentication** with Spring Security.

### Authentication Flow

```text
User Login
    ↓
Validate Credentials
    ↓
Generate JWT
    ↓
Client Stores Token
    ↓
Client Sends:
Authorization: Bearer <token>
    ↓
JwtAuthenticationFilter
    ↓
Validate JWT
    ↓
Authenticate User
    ↓
Access Protected API
```

Public APIs currently include:

```text
POST /users/register
POST /users/login
```

Protected APIs require authentication.

Role-based authorization is also implemented for administrative operations.

---

# 👤 User Module

The User module provides:

* User registration
* User login
* Password encryption using BCrypt
* JWT authentication
* Role-based authorization
* User lookup
* User deletion for authorized administrators

---

# 📦 Product Module

The Product module provides product management functionality.

Main operations include:

```text
Create Product
Get Product
Get Products
Update Product
Delete Product
```

Products are associated with categories.

---

# 🗂️ Category Module

Category management supports:

```text
Create Category
Get Categories
Update Category
Delete Category
```

Administrative operations are protected using Spring Security roles.

---

# 🛒 Cart Module

The Cart module is **completed**.

### Features

* Add product to cart
* Get user's cart
* Update cart quantity
* Remove individual cart item
* Clear entire cart
* Validation
* Cart-item exception handling

### Add Product

```http
POST /cart/add
```

Example request:

```json
{
  "userId": 26,
  "productId": 6,
  "quantity": 2
}
```

### Get Cart

```http
GET /cart/{userId}
```

### Update Cart Quantity

```http
PUT /cart/item/{cartItemId}
```

Example:

```json
{
  "quantity": 3
}
```

If quantity is `0`, the cart item is removed.

### Remove Cart Item

```http
DELETE /cart/items/{cartItemId}
```

### Clear Cart

```http
DELETE /cart/{userId}/items
```

The cart itself is preserved; its `CartItem` records are removed.

---

# 📦 Order Module

The Order module is **completed**.

### Current Design

```text
User
  │
  │ 1
  ▼
Order
  │
  │ 1:N
  ▼
OrderItem
  │
  │ N:1
  ▼
Product
```

### Order Status

The project uses the following order lifecycle:

```text
PENDING
    ↓
PROCESSING
    ↓
CONFIRMED
    ↓
SHIPPED
    ↓
OUT_FOR_DELIVERY
    ↓
DELIVERED
```

Other possible states:

```text
CANCELLED
RETURNED
REFUNDED
```

### Order Entity

An order contains:

```text
id
user
orderItems
totalAmount
status
createdAt
updatedAt
```

### OrderItem Entity

An order item contains:

```text
id
order
product
quantity
price
```

`OrderItem.price` stores the **historical purchase price** used when the order was created, rather than relying on the product's current price.

### Order Relationships

```text
Order
 ├── OrderItem
 ├── OrderItem
 └── OrderItem
```

Therefore:

```java
Order → OrderItem
@OneToMany
```

and:

```java
OrderItem → Order
@ManyToOne
```

Similarly, multiple order items can reference the same product:

```java
OrderItem → Product
@ManyToOne
```

---

# 🔌 Order APIs

Implemented order APIs:

```text
POST /orders/place
GET /orders/my-orders
GET /orders/{orderId}
PUT /orders/{orderId}/cancel
PUT /orders/{orderId}/status
```

The order status update API is restricted to administrators.

---

# 💳 Payment Module

The Payment module is currently **in development**.

### Current Design

```text
Order 1 ───── 1 Payment
```

Payment stores:

```text
id
order
amount
paymentMethod
status
transactionId
createdAt
updatedAt
```

`order_id` is non-null and unique, ensuring one payment record per order.

### Payment Status

```text
PENDING
SUCCESS
FAILED
```

### Payment Methods

```text
UPI
CARD
NET_BANKING
COD
```

### Payment API

```text
POST /payments/{orderId}
```

The client provides only the payment method.

The server controls:

```text
amount
status
transactionId
```

The payment amount is taken from the order's `totalAmount`.

### Payment Processing Flow

```text
Authenticated User
        ↓
Find User
        ↓
Find Order
        ↓
Verify Order belongs to User
        ↓
Check Order can be paid
        ↓
Check Payment does not already exist
        ↓
Create Payment
        ↓
amount = Order.totalAmount
status = PENDING
paymentMethod = request.paymentMethod
transactionId = generated by server
        ↓
Save Payment
        ↓
Return Payment Response
```

The current payment flow is simulated; real payment gateway integration can be added later.

---


# 🔌 API Reference

All protected APIs require:

```http
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

---

## 🔐 Authentication APIs

### 1. Register User

```http
POST /users/register
```

**Request Body:**

```json
{
  "name": "Arpit",
  "email": "arpit@example.com",
  "password": "Password@123",
  "role": "USER"
}
```

### 2. Login

```http
POST /users/login
```

**Request Body:**

```json
{
  "email": "arpit@example.com",
  "password": "Password@123"
}
```

The response returns a JWT token which is used for protected APIs.

---

## 👤 User APIs

### 3. Get All Users

```http
GET /users
```

**Authorization:** `ADMIN`

**Request Body:** None

### 4. Get User by ID

```http
GET /users/{userId}
```

**Request Body:** None

### 5. Update User

```http
PUT /users/{userId}
```

**Request Body:**

```json
{
  "name": "Updated Name",
  "email": "updated@example.com",
  "password": "NewPassword@123"
}
```

### 6. Delete User

```http
DELETE /users/{userId}
```

**Authorization:** `ADMIN`

**Request Body:** None

---

## 📦 Product APIs

### 7. Create Product

```http
POST /products
```

**Request Body:**

```json
{
  "name": "Laptop",
  "description": "Gaming Laptop",
  "price": 75000,
  "stock": 10,
  "categoryId": 1
}
```

### 8. Get All Products

```http
GET /products
```

**Request Body:** None

### 9. Get Product by ID

```http
GET /products/{productId}
```

**Request Body:** None

### 10. Update Product

```http
PUT /products/{productId}
```

**Request Body:**

```json
{
  "name": "Updated Laptop",
  "description": "Updated description",
  "price": 72000,
  "stock": 15,
  "categoryId": 1
}
```

### 11. Delete Product

```http
DELETE /products/{productId}
```

**Request Body:** None

---

## 🗂️ Category APIs

### 12. Create Category

```http
POST /categories
```

**Authorization:** `ADMIN`

**Request Body:**

```json
{
  "name": "Electronics",
  "description": "Electronic products"
}
```

### 13. Get All Categories

```http
GET /categories
```

**Request Body:** None

### 14. Get Category by ID

```http
GET /categories/{categoryId}
```

**Request Body:** None

### 15. Update Category

```http
PUT /categories/{categoryId}
```

**Authorization:** `ADMIN`

**Request Body:**

```json
{
  "name": "Updated Electronics",
  "description": "Updated category description"
}
```

### 16. Delete Category

```http
DELETE /categories/{categoryId}
```

**Authorization:** `ADMIN`

**Request Body:** None

---

## 🛒 Cart APIs

### 17. Add Product to Cart

```http
POST /cart/add
```

**Request Body:**

```json
{
  "userId": 26,
  "productId": 6,
  "quantity": 2
}
```

### 18. Get Cart

```http
GET /cart/{userId}
```

**Request Body:** None

### 19. Update Cart Quantity

```http
PUT /cart/item/{cartItemId}
```

**Request Body:**

```json
{
  "quantity": 3
}
```

If quantity is `0`, the cart item is removed.

### 20. Remove Cart Item

```http
DELETE /cart/items/{cartItemId}
```

**Request Body:** None

### 21. Clear Cart

```http
DELETE /cart/{userId}/items
```

**Request Body:** None

---

## 📦 Order APIs

### 22. Place Order

```http
POST /orders/place
```

**Request Body:** None

The order is created from the authenticated user's cart.

The server:
- Creates the order.
- Creates `OrderItem` records.
- Calculates `totalAmount`.
- Saves the order.
- Clears the cart.

### 23. Get My Orders

```http
GET /orders/my-orders
```

**Request Body:** None

### 24. Get Order by ID

```http
GET /orders/{orderId}
```

**Request Body:** None

The authenticated user can access only their own order.

### 25. Cancel Order

```http
PUT /orders/{orderId}/cancel
```

**Request Body:** None

Cancellation is currently allowed only when the order status is `PENDING`.

### 26. Update Order Status

```http
PUT /orders/{orderId}/status
```

**Authorization:** `ADMIN`

**Request Body:**

```json
{
  "status": "PROCESSING"
}
```

Valid status flow:

```text
PENDING
   ↓
PROCESSING
   ↓
CONFIRMED
   ↓
SHIPPED
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED
```

---

## 💳 Payment APIs

### 27. Create Payment

```http
POST /payments/{orderId}
```

**Request Body:**

```json
{
  "paymentMethod": "UPI"
}
```

Supported payment methods:

```text
UPI
CARD
NET_BANKING
COD
```

The client does **not** send:

```text
amount
status
transactionId
```

These are controlled by the server.

The amount is taken from:

```text
Order.totalAmount
```

Initial payment status:

```text
PENDING
```

Payment statuses:

```text
PENDING
SUCCESS
FAILED
```

The payment must belong to the authenticated user's order, and only one payment is allowed per order.

---

## 📋 API Summary

| # | Method | Endpoint | Authorization | Body |
|---|---|---|---|---|
| 1 | POST | `/users/register` | Public | JSON |
| 2 | POST | `/users/login` | Public | JSON |
| 3 | GET | `/users` | ADMIN | None |
| 4 | GET | `/users/{userId}` | Authenticated | None |
| 5 | PUT | `/users/{userId}` | Authenticated | JSON |
| 6 | DELETE | `/users/{userId}` | ADMIN | None |
| 7 | POST | `/products` | Authenticated | JSON |
| 8 | GET | `/products` | Authenticated | None |
| 9 | GET | `/products/{productId}` | Authenticated | None |
| 10 | PUT | `/products/{productId}` | Authenticated | JSON |
| 11 | DELETE | `/products/{productId}` | Authenticated | None |
| 12 | POST | `/categories` | ADMIN | JSON |
| 13 | GET | `/categories` | USER / ADMIN | None |
| 14 | GET | `/categories/{categoryId}` | USER / ADMIN | None |
| 15 | PUT | `/categories/{categoryId}` | ADMIN | JSON |
| 16 | DELETE | `/categories/{categoryId}` | ADMIN | None |
| 17 | POST | `/cart/add` | Authenticated | JSON |
| 18 | GET | `/cart/{userId}` | Authenticated | None |
| 19 | PUT | `/cart/item/{cartItemId}` | Authenticated | JSON |
| 20 | DELETE | `/cart/items/{cartItemId}` | Authenticated | None |
| 21 | DELETE | `/cart/{userId}/items` | Authenticated | None |
| 22 | POST | `/orders/place` | Authenticated | None |
| 23 | GET | `/orders/my-orders` | Authenticated | None |
| 24 | GET | `/orders/{orderId}` | Authenticated | None |
| 25 | PUT | `/orders/{orderId}/cancel` | Authenticated | None |
| 26 | PUT | `/orders/{orderId}/status` | ADMIN | JSON |
| 27 | POST | `/payments/{orderId}` | Authenticated | JSON |

---

# 🧾 Order Processing Flow

The planned checkout flow is:

```text
Authenticated User
        ↓
Find User
        ↓
Find User's Cart
        ↓
Get CartItems
        ↓
Check Cart
        ↓
Create Order
        ↓
Create OrderItems
        ↓
Calculate Total Amount
        ↓
Save Order
        ↓
Clear Cart
        ↓
Return Order Response
```

The complete checkout operation will use:

```java
@Transactional
```

so that the database operations are handled as a single transaction.

---

# 💰 Money Handling

The project uses:

```java
BigDecimal
```

for monetary values instead of `double` or `float`.

Examples:

```java
private BigDecimal price;

private BigDecimal totalAmount;
```

This avoids floating-point precision problems when dealing with financial values.

---

# 🧩 DTO Architecture

DTOs are separated into request and response packages:

```text
dto/
├── request/
└── response/
```

This keeps the API contract separate from database entities.

For example:

```text
AddToCartRequestDTO
UpdateCartQuantityRequestDTO
```

are request DTOs.

While:

```text
CartResponseDTO
CartItemResponseDTO
OrderResponseDTO
OrderItemResponseDTO
```

are response DTOs.

---

# ⚠️ Global Exception Handling

The application uses:

```java
@ControllerAdvice
```

for centralized exception handling.

Validation errors are handled using:

```java
MethodArgumentNotValidException
```

and application-specific exceptions include:

```text
UserNotFoundException
ProductNotFoundException
CategoryNotFoundException
CartItemNotFoundException
InvalidCredentialsException
```

API errors are returned using the common:

```text
ApiError
```

response structure.

---

# 🔄 Git Branching Strategy

The project uses feature branches.

Example:

```text
main
 │
 ├── feature/user-module
 ├── feature/product-module
 ├── feature/category-module
 ├── feature/cart-module
 └── feature/order-module
```

Current development branch:

```text
feature/payment
```

Typical workflow:

```bash
git checkout main
git pull origin main

git checkout -b feature/<feature-name>

git add .
git commit -m "Implement <feature>"

git push -u origin feature/<feature-name>
```

---

# 🧪 API Testing

APIs are tested using **Postman**.

Testing includes:

* Successful requests
* Validation failures
* Authentication failures
* Authorization failures
* Not-found scenarios
* Cart operations
* Order workflow
* Payment validation and workflow

---

# 🔮 Future Enhancements

After completing the core e-commerce functionality, the following features are planned:

### MongoDB

MongoDB will be used alongside MySQL for use cases such as:

```text
Activity Logs
Audit Logs
Order Events
User Activity
```

The same transactional data will not simply be duplicated across both databases.

### Redis

Planned use:

```text
Caching
Product lookup optimization
Session-related use cases
```

### Messaging

Potential future integration:

```text
Kafka / RabbitMQ
```

for event-driven processing.

### Search

Potential integration:

```text
Elasticsearch
```

for advanced product search.

### DevOps

Planned:

```text
Docker
GitHub Actions
AWS Deployment
```

### Testing

Planned:

```text
JUnit
Mockito
Integration Testing
```

### API Documentation

Planned:

```text
Swagger / OpenAPI
```

---

# 🎯 Project Goals

The goal of this project is not only to implement CRUD APIs but to demonstrate practical backend development concepts including:

* REST API design
* Layered architecture
* Spring Security
* JWT authentication
* Role-based authorization
* DTO-based API design
* JPA entity relationships
* Transaction management
* Exception handling
* Database design
* Git feature-branch workflow
* Testing
* Cloud deployment
* Microservice-ready architecture

---

# 👨‍💻 Author

**Arpit Vishwakarma**

Backend Developer | Java | Spring Boot | REST APIs | SQL | Automation Testing

---

## 📌 Current Focus

> **Payment Module — Payment entity, DTOs, repository, service, and payment processing flow.**
