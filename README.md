# BookMovies – Backend

A RESTful backend for a movie ticket booking application, built with **Java and Spring Boot**. It provides APIs for managing movies, theaters, shows, users, and bookings, with JWT-based authentication and role-based authorization.

**Live Application:** [https://bookmovies-web.vercel.app](https://bookmovies-web.vercel.app)
**Frontend Repository:** [BookMovies-Frontend](https://github.com/bagadivenkatramesh/BookMovies-Frontend)
**Backend Repository:** [BookMovies](https://github.com/bagadivenkatramesh/BookMovies)

## Tech Stack

| Technology              | Purpose                                |
| ----------------------- | -------------------------------------- |
| Java 25                 | Backend programming language           |
| Spring Boot 4.1.1       | Application framework                  |
| Spring Web MVC          | REST API development                   |
| Spring Data JPA         | Database access and ORM                |
| Hibernate               | Object-relational mapping              |
| Spring Security         | Authentication and authorization       |
| JWT (JJWT 0.13.0)       | Token-based authentication             |
| MySQL 8                 | Relational database                    |
| Maven                   | Dependency and build management        |
| Lombok                  | Boilerplate code reduction             |
| Docker & Docker Compose | Containerization and local environment |
| Railway                 | Backend and database deployment        |
| Vercel                  | Frontend deployment                    |

## Features

- **User Authentication:** User registration and login.
- **JWT Authentication:** Stateless authentication using JSON Web Tokens.
- **Role-Based Authorization:** Separate access permissions for regular users and administrators.
- **Movie Management:** APIs for managing movie information.
- **Theater Management:** APIs for managing theater information.
- **Show Management:** APIs for managing movie shows and their scheduling.
- **Ticket Booking:** Booking functionality with seat selection and availability checks.
- **Input Validation:** Request validation using Jakarta Bean Validation.
- **Centralized Exception Handling:** Consistent API error responses through a global exception handler.

## Application Architecture

The backend follows a layered architecture:

```text
Client (React Frontend)
        |
        v
   REST Controllers
        |
        v
     Services
        |
        v
   Repositories
        |
        v
   MySQL Database
```

- **Controllers:** Handle incoming HTTP requests and return API responses.
- **Services:** Contain application and business logic.
- **Repositories:** Handle database operations using Spring Data JPA.
- **Entities:** Represent the application's database model.
- **DTOs:** Separate API request/response structures from persistence entities.

## Database Domain Model

The application uses the following primary entities:

| Entity  | Responsibility                           |
| ------- | ---------------------------------------- |
| User    | Stores user account and role information |
| Movie   | Stores movie details                     |
| Theater | Stores theater information               |
| Show    | Represents a scheduled movie show        |
| Booking | Stores ticket booking information        |

JPA and Hibernate handle object-relational mapping and database interactions.

## Authentication and Authorization

The application uses **Spring Security with JWT-based authentication**.

1. A user registers through the registration API.
2. The user logs in using their credentials.
3. Spring Security authenticates the credentials.
4. A JWT is generated and returned after successful authentication.
5. The client includes the token in the `Authorization` header for protected API requests.
6. A JWT authentication filter validates incoming tokens and establishes the authenticated security context.

### Authorization

- Regular users are assigned the `ROLE_USER` role.
- Administrators are assigned the `ROLE_ADMIN` role.
- Administrative endpoints under `/api/admin/**` require administrator authorization.
- Passwords are stored using BCrypt hashing.
- The application uses stateless session management.

## Key API Endpoints

The following are confirmed authentication endpoints:

| Method | Endpoint                         | Description                                             | Access        |
| ------ | -------------------------------- | ------------------------------------------------------- | ------------- |
| POST   | `/api/auth/register_normal_user` | Register a regular user                                 | Public        |
| POST   | `/api/auth/login`                | Authenticate and obtain a JWT                           | Public        |
| GET    | `/api/auth/me`                   | Retrieve the currently authenticated user's information | Authenticated |

Additional APIs cover movie, theater, show, and booking operations. Administrative operations are protected under `/api/admin/**`.

## Local Setup Using Docker

Docker Compose starts the MySQL database and Spring Boot backend together.

### Prerequisites

- Git
- Docker Desktop

### 1. Clone the repository

```bash
git clone https://github.com/bagadivenkatramesh/BookMovies.git
cd BookMovies
```

### 2. Configure environment variables

Create two files in the project root.

**`.env.compose`**

```dotenv
MYSQL_ROOT_PASSWORD=your_mysql_root_password
MYSQL_DATABASE=bookmovies
```

**`.env.backend`**

```dotenv
DB_USERNAME=root
DB_PASSWORD=your_mysql_root_password
JWT_SECRET=your_secure_jwt_secret
JWT_EXPIRATION=3600000
```

Use a secure JWT secret appropriate for your environment. Do not commit either environment file to Git.

### 3. Start the application

```bash
docker compose up -d --build
```

This starts:

- MySQL on `localhost:3307`
- Spring Boot backend on `localhost:8080`

The backend connects to MySQL internally through the Docker Compose service name.

### 4. Verify the containers

```bash
docker compose ps
```

To view backend logs:

```bash
docker compose logs -f backend
```

The backend APIs are accessible at:

```text
http://localhost:8080
```

### 5. Stop the application

```bash
docker compose down
```

The named MySQL volume preserves database data between restarts. Avoid `docker compose down -v` unless you intentionally want to delete that volume and its data.

## Environment Configuration

The application reads database and JWT configuration from environment variables.

| Variable         | Description                             |
| ---------------- | --------------------------------------- |
| `DB_URL`         | JDBC connection URL                     |
| `DB_USERNAME`    | Database username                       |
| `DB_PASSWORD`    | Database password                       |
| `JWT_SECRET`     | Secret used for JWT signing             |
| `JWT_EXPIRATION` | JWT expiration duration in milliseconds |

For production deployment, the application uses the `prod` Spring profile. Production-specific configuration disables SQL logging and validates the existing database schema rather than automatically updating it.

## Deployment

The application is deployed using the following setup:

| Component           | Platform |
| ------------------- | -------- |
| React frontend      | Vercel   |
| Spring Boot backend | Railway  |
| MySQL database      | Railway  |

The backend is packaged as a Docker image and deployed on Railway. Database credentials and JWT configuration are supplied through Railway environment variables rather than committed to the repository.

**Live frontend:** [https://bookmovies-web.vercel.app](https://bookmovies-web.vercel.app)

## Related Repository

[BookMovies – Frontend](https://github.com/bagadivenkatramesh/BookMovies-Frontend)

---