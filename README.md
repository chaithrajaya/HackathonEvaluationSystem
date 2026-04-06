# Hackathon Evaluator Backend

A complete Spring Boot backend for evaluating hackathon projects with MySQL database, JWT authentication, and role-based authorization.

## Features

- **User Management**: Registration, login, and role-based access (ADMIN, JUDGE, PARTICIPANT)
- **Hackathon Management**: Create, update, and manage hackathons
- **Team Management**: Create and manage teams for hackathons
- **Project Submissions**: Teams can submit their projects
- **Evaluation System**: Judges can evaluate submissions with scores and feedback
- **Leaderboard**: Real-time ranking of teams based on average scores
- **Security**: JWT-based authentication with role-based authorization
- **Validation**: Comprehensive input validation using Jakarta Validation

## Technology Stack

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA / Hibernate**
- **Spring Security with JWT**
- **MySQL Database**
- **Lombok**
- **Maven**

## Database Schema

### Users
- `userId` (Primary Key)
- `name`
- `email` (Unique)
- `password` (Hashed with BCrypt)
- `role` (ADMIN, JUDGE, PARTICIPANT)
- `teamId` (Foreign Key)
- `isActive`
- `createdAt`

### Teams
- `teamId` (Primary Key)
- `teamName`
- `hackathonId` (Foreign Key)
- `createdAt`

### Hackathons
- `hackathonId` (Primary Key)
- `name`
- `description`
- `startDate`
- `endDate`
- `createdByUserId` (Foreign Key)
- `createdAt`

### Submissions
- `submissionId` (Primary Key)
- `teamId` (Foreign Key)
- `hackathonId` (Foreign Key)
- `projectTitle`
- `githubLink`
- `description`
- `createdAt`

### Evaluations
- `evaluationId` (Primary Key)
- `submissionId` (Foreign Key)
- `judgeUserId` (Foreign Key)
- `score` (0-100)
- `feedback`
- `createdAt`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login (returns JWT token)

### Users
- `POST /api/users/register` - Register new user
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users` - Get all users (ADMIN only)
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user (ADMIN only)
- `GET /api/users/{id}/submissions` - Get user's submissions

### Hackathons
- `POST /api/hackathons` - Create hackathon (ADMIN only)
- `GET /api/hackathons/{id}` - Get hackathon by ID
- `GET /api/hackathons` - Get all hackathons
- `PUT /api/hackathons/{id}` - Update hackathon (ADMIN only)
- `DELETE /api/hackathons/{id}` - Delete hackathon (ADMIN only)
- `GET /api/hackathons/{id}/leaderboard` - Get hackathon leaderboard

### Teams
- `POST /api/teams` - Create team (ADMIN only)
- `GET /api/teams/{id}` - Get team by ID
- `GET /api/teams/hackathon/{hackathonId}` - Get teams by hackathon
- `PUT /api/teams/{id}` - Update team (ADMIN only)
- `DELETE /api/teams/{id}` - Delete team (ADMIN only)

### Submissions
- `POST /api/submissions` - Create submission (PARTICIPANT only)
- `GET /api/submissions/{id}` - Get submission by ID
- `GET /api/submissions/team/{teamId}` - Get submissions by team
- `GET /api/submissions/hackathon/{hackathonId}` - Get submissions by hackathon
- `PUT /api/submissions/{id}` - Update submission
- `DELETE /api/submissions/{id}` - Delete submission
- `GET /api/submissions/{id}/evaluations` - Get evaluations for submission

### Evaluations
- `POST /api/evaluations` - Add evaluation (JUDGE only)
- `GET /api/evaluations/{id}` - Get evaluation by ID
- `GET /api/evaluations/submission/{submissionId}` - Get evaluations of a submission
- `GET /api/evaluations/judge/{judgeUserId}` - Get evaluations done by a judge
- `PUT /api/evaluations/{id}` - Update evaluation
- `DELETE /api/evaluations/{id}` - Delete evaluation (ADMIN only)

## Role-Based Access Control

### ADMIN
- Can create and manage hackathons
- Can create and manage users
- Can create and manage teams
- Can delete any evaluation
- Full access to all endpoints

### JUDGE
- Can evaluate submissions
- Can view all submissions and evaluations
- Can update their own evaluations
- Cannot create or manage hackathons/teams

### PARTICIPANT
- Can submit projects
- Can view their own submissions and evaluations
- Can update their own submissions
- Limited access to hackathon information

## Setup Instructions

### Prerequisites
- Java 21
- Maven 3.6+
- MySQL 8.0+

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE hackathon_evaluator;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hackathon_evaluator
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
1. Clone the repository
2. Navigate to project directory
3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Testing
Run the tests:
```bash
mvn test
```

## Sample API Usage

### Register a User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "PARTICIPANT"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Create Hackathon (Admin)
```bash
curl -X POST http://localhost:8080/api/hackathons \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "name": "Summer Hackathon 2024",
    "description": "Annual hackathon event",
    "startDate": "2024-06-01T09:00:00",
    "endDate": "2024-06-03T18:00:00",
    "createdByUserId": 1
  }'
```

## Configuration

### JWT Settings
- JWT Secret: Configured in `application.properties`
- JWT Expiration: 24 hours (86400000 ms)

### Database Connection
- Uses HikariCP connection pool
- Maximum pool size: 10
- Connection timeout: 20 seconds

## Error Handling

The application provides comprehensive error handling with proper HTTP status codes and error messages:

- **400 Bad Request**: Validation errors, invalid input
- **401 Unauthorized**: Authentication required, invalid credentials
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server errors

## Security Features

- Password hashing with BCrypt
- JWT token-based authentication
- Role-based authorization
- CORS configuration
- Input validation and sanitization

## Future Enhancements

- Email notifications for hackathon updates
- File upload for project submissions
- Real-time evaluation updates with WebSockets
- Advanced analytics and reporting
- Multi-tenancy support
- Integration with external services (GitHub, etc.)
