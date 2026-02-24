# Spring Boot + MongoDB E‑Commerce Backend – Project Outline

> Goal: You build everything. This file only gives structure, flows, and checkpoints.

---

## 1. High‑Level Scope

You are building a monolithic REST API for an e‑commerce backend using:

- Spring Boot 3.x, Java 17+
- Spring Web, Spring Data MongoDB, Validation
- MongoDB (local or Atlas)

Core features you should implement:

- User registration/login (simplified auth)
- Product catalog (categories, products, search, pagination)
- Cart management per user
- Order creation from cart, basic order status

Use this as a checklist while designing your models and APIs. [web:21][web:27][web:29]

---

## 2. Project & Folder Structure

### 2.1 Create Project

Use Spring Initializr:

- Group: `com.example`
- Artifact: `ecommerce-service`
- Dependencies:
  - Spring Web
  - Spring Data MongoDB
  - Spring Boot Starter Validation
  - Lombok (optional)

Generate and import into your IDE. [web:18][web:40]

### 2.2 Package Structure

Under `src/main/java/com/example/ecommerce`:

```text
com/example/ecommerce
├── EcommerceApplication.java
├── config/
├── controller/
│   ├── auth/
│   ├── product/
│   ├── cart/
│   └── order/
├── dto/
│   ├── auth/
│   ├── product/
│   ├── cart/
│   └── order/
├── model/
│   ├── user/
│   ├── product/
│   └── order/
├── repository/
├── service/
│   ├── impl/
│   └── util/        # optional helpers
└── exception/
This follows Spring Boot’s “main class in root package + feature‑oriented subpackages” best practices. web:6web:12web:18
```
3. Configuration Outline
3.1 MongoDB Config
Create `src/main/resources/application.yml`:

Example shape (you fill values):
```
server:
  port: 8080

spring:
  application:
    name: ecommerce-service

  
    mongodb:
      # OPTION 1: local dev
      host: localhost
      port: 27017
      database: ecommerce_db

      # OPTION 2: Atlas (comment local, use uri)
      # uri: mongodb+srv://<user>:<password>@<cluster-host>/ecommerce_db?retryWrites=true&w=majority
```
Ensure the property prefix is `spring.data.mongodb` so auto‑configuration works. web:37web:38web:40

3.2 Profiles (Optional)
Add `application-dev.yml`, `application-prod.yml` later to practice environment‑specific Mongo URIs and settings. web:18web:38

4. Domain & Collections Design (You Model the Fields)
Design MongoDB documents (collections) for:
	•	`users`
	•	`products`
	•	`carts`
	•	`orders`

Use Mongo’s document‑oriented style: embed line items inside cart and order documents, not separate tables. web:28web:1

4.1 Suggested Aggregates
You decide exact fields, types and indexes, but stick to these aggregates:
	•	User (collection: `users`)
	•	Identity, contact (email), auth data (password hash), role (CUSTOMER/ADMIN), audit fields.
	•	Category (collection: `categories`)
	•	Category name, description, maybe parent for hierarchy.
	•	Product (collection: `products`)
	•	Name, categoryId, description, price, stock, images, tags, active flag, audit fields.
	•	Cart (collection: `carts`)
	•	userId, list of cart items (productId, name, price snapshot, quantity), total amount.
	•	Order (collection: `orders`)
	•	userId, order items (from cart items), totals, payment info, shipping address, status (PENDING/PAID/SHIPPED/DELIVERED), audit fields.
Check InfoQ’s e‑commerce modeling article for ideas on embedded vs referenced documents. web:28
5. Layered Architecture
You implement everything following this pattern for each feature (auth, product, cart, order). web:6web:12web:36

5.1 Model Layer (`model/`)
	•	`model/user`
	•	`User`, `UserRole` enum.
	•	`model/product`
	•	`Category`, `Product`.
	•	`model/order`
	•	`Cart`, `CartItem`, `Order`, `OrderItem`, `OrderStatus`, `PaymentInfo`.
Add Spring Data annotations only here (e.g. `@Document`, `@Id`, indexes). web:1web:40
5.2 Repository Layer (`repository/`)
Create interfaces extending `MongoRepository` for each aggregate:
	•	`UserRepository`
	•	`CategoryRepository`
	•	`ProductRepository`
	•	`CartRepository`
	•	`OrderRepository`
Define query methods and `@Query` where needed (e.g. list active products, find by userId, search by name). web:2web:30
5.3 DTO Layer (`dto/`)
Create dedicated DTO packages:
	•	`dto/auth`: register, login, auth response (token + userId).
	•	`dto/product`: product create/update/list response.
	•	`dto/cart`: add/remove cart items.
	•	`dto/order`: place order request, order response.
Use `jakarta.validation` annotations in request DTOs to enforce input constraints. web:24web:29
5.4 Service Layer (`service/` + `service/impl/`)
Define interfaces:
	•	`AuthService`
	•	`ProductService`
	•	`CartService`
•	`OrderService`
Implement them in `impl/` using repositories. Responsibilities per service:
	•	`AuthService`
	•	Register user, hash password, check duplicate email.
	•	Login user, verify password.
	•	Issue simple token (you decide scheme) and validate token → userId.
	•	`ProductService`
	•	CRUD for products.
	•	Pagination & filtering by category, search keyword.
	•	Soft delete via active flag.
	•	`CartService`
	•	Get cart by userId (create empty if not exist).
	•	Add/update/remove items and recalculate total.
	•	Clear cart.
	•	`OrderService`
	•	Convert cart to order.
	•	Set initial status, payment info.
	•	Clear cart after order placement.
	•	Fetch user’s orders (optionally filter by status). web:21web:22web:29
Use services as the only place where business rules live (e.g. “cannot order with empty cart”, “product must be active”, etc.).
5.5 Controller Layer (`controller/`)
Feature‑grouped controllers:
	•	`controller/auth/AuthController`
	•	`controller/product/ProductController`
	•	`controller/cart/CartController`
	•	`controller/order/OrderController`
Guidelines:
	•	Map endpoints under `/api/v1/**`.
	•	Inject services.
	•	Accept DTOs with `@RequestBody` and `@Valid`.
	•	Only map HTTP → service calls → HTTP, no business logic here.
Use header `X-Auth-Token` (or similar) to obtain the userId via `AuthService` for cart and order endpoints. web:21web:27
6. API Design (You Implement the Endpoints)
Define REST endpoints for each module.
6.1 Auth
Under `/api/v1/auth`:
	•	`POST /register`
	•	Body: registration DTO.
	•	Response: token + user info.
	•	`POST /login`
	•	Body: login DTO.
	•	Response: token + user info.
You choose hashing mechanism and token format, but keep them consistent.
6.2 Product Catalog
Under `/api/v1/products` and `/api/v1/categories`:
	•	`GET /products`
	•	Query params: `page`, `size`, `categoryId`, maybe `search`.
	•	Returns paginated product list.
	•	`GET /products/{id}`
	•	Returns single product.
	•	`POST /products` (admin path for practice)
	•	`PUT /products/{id}`
	•	`DELETE /products/{id}` (soft delete).
	•	`GET /categories`
	•	`POST /categories` (for seeding categories initially).
Use Spring Data MongoDB pagination support (`Pageable`). web:24web:40
6.3 Cart
Under `/api/v1/cart`:
	•	`GET /`
	•	Header: `X-Auth-Token`.
	•	Returns current user’s cart.
	•	`POST /items`
	•	Header: `X-Auth-Token`.
	•	Body: productId, quantity.
•	`DELETE /items/{productId}`
	•	`DELETE /`
	•	Clear entire cart.
Map userId from token once in controller, then call `CartService`. web:27web:29
6.4 Orders
Under `/api/v1/orders`:
	•	`POST /`
	•	Header: `X-Auth-Token`.
	•	Body: shipping address, payment method.
	•	Creates order from cart.
	•	`GET /`
	•	Header: `X-Auth-Token`.
	•	Returns user’s orders.
You can later extend with `PATCH /{id}/status` for admin to update order status.
7. Global Concerns
7.1 Exceptions
In `exception/`:
	•	Custom exceptions: `ResourceNotFoundException`, `BadRequestException` etc.
	•	`GlobalExceptionHandler` with `@RestControllerAdvice`:
	•	Map exceptions → HTTP status codes + JSON error structures.
	•	Handle validation errors via `MethodArgumentNotValidException`.
7.2 Audit & Utility
Add simple audit fields (createdAt, updatedAt) in main entities and helper methods to update timestamps.
Optionally, create:
	•	`BaseEntity` with audit fields (if you want to reuse).
	•	Utility components in `service/util/` for mapping DTO ↔ entity.
8. Implementation Flow (How You Should Build)
Recommended order to implement:
	1.	Config & bootstrap
	•	`EcommerceApplication`, `application.yml`, Mongo running. web:40
	2.	User/Auth
	•	User model, repo, DTOs, `AuthService`, `AuthController`.
	•	Test register/login manually with Postman.
	3.	Categories & Products
	•	Category & Product models, repos.
	•	Product DTOs, service, controller.
	•	Test CRUD + pagination.
	4.	Cart
	•	Cart models, repo.
	•	Cart service + controller (using `X-Auth-Token`).
	•	Test add/remove items and total calculation.
	5.	Orders
	•	Order models, repo.
	•	Order service + controller.
	•	Test place order flow.
	6.	Global exception handling
	•	Replace ad‑hoc error responses with centralized handler.
	7.	Refine
	•	Add indexes where needed.
	•	Add search endpoints.
	•	Add profiles and environment configs.
This order helps you grow the project incrementally and keeps your “Spring Boot game” focused on real e‑commerce flows. web:21web:22web:39
9. Minimal Run Instructions
	•	Start MongoDB (local or Atlas).
	•	From project root:
./mvnw spring-boot:run

•	Hit a basic endpoint (e.g. `GET /actuator/health` if you enable actuator, or `GET /api/v1/products`) to verify it runs.
	•	Use Postman/Insomnia to exercise each module as you implement it. web:37web:40
