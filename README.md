# Certificate Request System

A Spring Boot REST API backend for managing digital certificate requests. Students can submit requests, admins/staff can approve or reject them, and approved requests automatically generate a PDF certificate.

---

## Tech Stack

- Java 17
- Spring Boot 2.7.14
- Spring Security + JWT
- Spring Data JPA + MySQL
- Apache PDFBox (PDF generation)
- Springdoc OpenAPI (Swagger UI)
- Lombok

---

## Getting Started

### Prerequisites
- Java 17
- Maven
- MySQL

### Setup

1. Clone the repository
```bash
git clone <repo-url>
cd certificate-request-system/springapp
```

2. Create a MySQL database
```sql
CREATE DATABASE appdb;
```

3. Update `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/appdb
spring.datasource.username=<your-db-username>
spring.datasource.password=<your-db-password>
jwt.jwtsecret=<your-secret-key>
```

4. Run the application
```bash
mvn spring-boot:run
```

5. Access Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

---

## API Endpoints

### Authentication
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Login and get JWT token | Public |

### Student
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/requests` | Submit a certificate request | STUDENT |
| GET | `/api/requests/dashboard` | View own requests | STUDENT |

### Admin - User Management
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/admin/users` | List all users | ADMIN |
| PUT | `/api/admin/users/{id}/role` | Update user role | ADMIN |

### Admin/Staff - Request Management
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/requests` | List all requests | ADMIN, STAFF |
| PUT | `/api/requests/{id}/approve` | Approve a request | ADMIN, STAFF |
| PUT | `/api/requests/{id}/reject` | Reject a request | ADMIN, STAFF |

### User Profile
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/users/me` | Get own profile | Authenticated |
| PUT | `/api/users/me` | Update own profile | Authenticated |

### Certificate
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/certificates/download/{fileName}` | Download certificate PDF | Authenticated |

---

## Roles

| Role | Permissions |
|------|-------------|
| `STUDENT` | Submit requests, view own dashboard, update own profile |
| `STAFF` | View all requests, approve/reject requests |
| `ADMIN` | All STAFF permissions + manage user roles |

---

## Project Structure

```
springapp/src/main/java/com/examly/springapp/
├── configuration/    # Security, JWT filter, CORS, Swagger config
├── controller/       # REST controllers
├── dto/              # Request/Response DTOs
├── exception/        # Custom exceptions and global handler
├── model/            # JPA entities
├── repository/       # Spring Data repositories
├── security/         # JWT utility, UserPrincipal, CurrentUserProvider
└── service/          # Business logic
```

---

## Features

- JWT-based stateless authentication
- Role-based access control with `@PreAuthorize`
- Automatic PDF certificate generation on request approval (Apache PDFBox)
- Paginated and sortable API responses
- Global exception handling
- Swagger UI for API documentation and testing
