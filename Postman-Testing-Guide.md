# Postman Testing Guide for Hackathon Evaluator API

## 🚀 Quick Setup

### 1. Import Collection
1. Open Postman
2. Click "Import" in the top left
3. Select the `Postman-Collection.json` file
4. Choose "Import as collection"

### 2. Set Environment Variables
The collection uses environment variables that are automatically set during testing:
- `baseUrl`: http://localhost:8080
- `adminToken`: JWT token for admin user
- `judgeToken`: JWT token for judge user  
- `participantToken`: JWT token for participant user
- `hackathonId`: ID of created hackathon
- `teamId`: ID of created team
- `submissionId`: ID of created submission
- `evaluationId`: ID of created evaluation

### 3. Pre-configured Users
The application has these sample users (created by DataLoader):
- **Admin**: `admin@example.com` / `admin123`
- **Judge**: `judge@example.com` / `judge123`
- **Participant**: `participant@example.com` / `participant123`

---

## 📋 Testing Workflow

### **Phase 1: Authentication**
Run these requests in order to get JWT tokens:

1. **Login (Admin)**
   - Method: POST
   - URL: `/api/auth/login`
   - Body: `{"email":"admin@example.com","password":"admin123"}`
   - ✅ Sets `adminToken` variable

2. **Login (Judge)**
   - Method: POST  
   - URL: `/api/auth/login`
   - Body: `{"email":"judge@example.com","password":"judge123"}`
   - ✅ Sets `judgeToken` variable

3. **Login (Participant)**
   - Method: POST
   - URL: `/api/auth/login`
   - Body: `{"email":"participant@example.com","password":"participant123"}`
   - ✅ Sets `participantToken` variable

### **Phase 2: Create Hackathon**
4. **Create Hackathon**
   - Method: POST
   - Auth: Bearer `{{adminToken}}`
   - URL: `/api/hackathons`
   - ✅ Sets `hackathonId` variable

### **Phase 3: Create Team**
5. **Create Team**
   - Method: POST
   - Auth: Bearer `{{adminToken}}`
   - URL: `/api/teams`
   - ✅ Sets `teamId` variable

### **Phase 4: Create Submission**
6. **Create Submission**
   - Method: POST
   - Auth: Bearer `{{participantToken}}`
   - URL: `/api/submissions`
   - ✅ Sets `submissionId` variable

### **Phase 5: Add Evaluation**
7. **Add Evaluation**
   - Method: POST
   - Auth: Bearer `{{judgeToken}}`
   - URL: `/api/evaluations`
   - ✅ Sets `evaluationId` variable

---

## 🧪 Detailed Test Cases

### **Authentication Tests**

#### ✅ Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Test User",
  "email": "newuser@example.com", 
  "password": "password123",
  "role": "PARTICIPANT"
}
```
**Expected**: 200 OK with user creation message

#### ✅ Login with Valid Credentials
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "admin123"
}
```
**Expected**: 200 OK with JWT token in response

#### ❌ Login with Invalid Credentials
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "wrongpassword"
}
```
**Expected**: 401 Unauthorized

#### ❌ Register with Existing Email
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Another Admin",
  "email": "admin@example.com",
  "password": "password123", 
  "role": "ADMIN"
}
```
**Expected**: 400 Bad Request (Email already exists)

---

### **Hackathon Tests**

#### ✅ Create Hackathon (Admin)
```http
POST /api/hackathons
Authorization: Bearer {{adminToken}}
Content-Type: application/json

{
  "name": "Summer Hackathon 2024",
  "description": "Annual hackathon event",
  "startDate": "2024-06-01T09:00:00",
  "endDate": "2024-06-03T18:00:00",
  "createdByUserId": 1
}
```
**Expected**: 200 OK with hackathon details

#### ✅ Get All Hackathons (Public)
```http
GET /api/hackathons
```
**Expected**: 200 OK with list of hackathons

#### ✅ Get Hackathon by ID
```http
GET /api/hackathons/{{hackathonId}}
```
**Expected**: 200 OK with hackathon details

#### ❌ Create Hackathon (Non-Admin)
```http
POST /api/hackathons
Authorization: Bearer {{participantToken}}
Content-Type: application/json

{
  "name": "Unauthorized Hackathon",
  "description": "Should fail"
}
```
**Expected**: 403 Forbidden

#### ✅ Get Leaderboard
```http
GET /api/hackathons/{{hackathonId}}/leaderboard
```
**Expected**: 200 OK with team rankings

---

### **Team Tests**

#### ✅ Create Team (Admin)
```http
POST /api/teams
Authorization: Bearer {{adminToken}}
Content-Type: application/json

{
  "teamName": "Code Warriors",
  "hackathonId": {{hackathonId}}
}
```
**Expected**: 200 OK with team details

#### ✅ Get Teams by Hackathon
```http
GET /api/teams/hackathon/{{hackathonId}}
Authorization: Bearer {{adminToken}}
```
**Expected**: 200 OK with list of teams

#### ❌ Create Team (Non-Admin)
```http
POST /api/teams
Authorization: Bearer {{participantToken}}
Content-Type: application/json

{
  "teamName": "Unauthorized Team",
  "hackathonId": {{hackathonId}}
}
```
**Expected**: 403 Forbidden

---

### **Submission Tests**

#### ✅ Create Submission (Participant)
```http
POST /api/submissions
Authorization: Bearer {{participantToken}}
Content-Type: application/json

{
  "teamId": {{teamId}},
  "hackathonId": {{hackathonId}},
  "projectTitle": "AI Task Manager",
  "githubLink": "https://github.com/example/project",
  "description": "An AI-powered task management application"
}
```
**Expected**: 200 OK with submission details

#### ✅ Get Submissions by Team
```http
GET /api/submissions/team/{{teamId}}
Authorization: Bearer {{participantToken}}
```
**Expected**: 200 OK with team submissions

#### ❌ Create Submission (Non-Participant)
```http
POST /api/submissions
Authorization: Bearer {{judgeToken}}
Content-Type: application/json

{
  "teamId": {{teamId}},
  "projectTitle": "Judge Submission",
  "githubLink": "https://github.com/judge/project"
}
```
**Expected**: 403 Forbidden

---

### **Evaluation Tests**

#### ✅ Add Evaluation (Judge)
```http
POST /api/evaluations
Authorization: Bearer {{judgeToken}}
Content-Type: application/json

{
  "submissionId": {{submissionId}},
  "score": 85,
  "feedback": "Great project! Excellent implementation."
}
```
**Expected**: 200 OK with evaluation details

#### ✅ Get Evaluations by Submission
```http
GET /api/evaluations/submission/{{submissionId}}
Authorization: Bearer {{participantToken}}
```
**Expected**: 200 OK with list of evaluations

#### ❌ Add Evaluation (Non-Judge)
```http
POST /api/evaluations
Authorization: Bearer {{participantToken}}
Content-Type: application/json

{
  "submissionId": {{submissionId}},
  "score": 90,
  "feedback": "I'm a participant, not a judge"
}
```
**Expected**: 403 Forbidden

---

### **User Management Tests**

#### ✅ Get All Users (Admin)
```http
GET /api/users
Authorization: Bearer {{adminToken}}
```
**Expected**: 200 OK with list of users

#### ❌ Get All Users (Non-Admin)
```http
GET /api/users
Authorization: Bearer {{participantToken}}
```
**Expected**: 403 Forbidden

#### ✅ Get User Submissions
```http
GET /api/users/3/submissions
Authorization: Bearer {{participantToken}}
```
**Expected**: 200 OK with user's submissions

---

## 🔒 Security Tests

### **Test Unauthorized Access**
Try accessing protected endpoints without JWT token:

```http
GET /api/users
```
**Expected**: 401 Unauthorized

### **Test Invalid JWT Token**
```http
GET /api/users
Authorization: Bearer invalid-token
```
**Expected**: 401 Unauthorized

### **Test Role-Based Access**
Verify that users can only access endpoints appropriate for their role:
- Admin can access all endpoints
- Judge can evaluate and view submissions
- Participant can submit and view own data

---

## 📊 Expected Responses

### **Success Responses (200 OK)**
```json
{
  "hackathonId": 1,
  "name": "Summer Hackathon 2024",
  "description": "Annual hackathon event",
  "startDate": "2024-06-01T09:00:00",
  "endDate": "2024-06-03T18:00:00",
  "createdByUserId": 1,
  "createdAt": "2024-06-01T10:00:00"
}
```

### **Error Responses**
```json
{
  "timestamp": "2024-06-01T10:00:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Access denied",
  "path": "/api/users"
}
```

### **Login Response**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "userId": 1,
    "name": "Admin User",
    "email": "admin@example.com",
    "role": "ADMIN"
  }
}
```

---

## 🚨 Common Issues & Solutions

### **Issue: 401 Unauthorized**
- **Cause**: Missing or invalid JWT token
- **Solution**: Run login requests first to get tokens

### **Issue: 403 Forbidden**
- **Cause**: User doesn't have required role
- **Solution**: Use appropriate user role for the endpoint

### **Issue: 400 Bad Request**
- **Cause**: Invalid request body or missing required fields
- **Solution**: Check request body format and required fields

### **Issue: 404 Not Found**
- **Cause**: Resource doesn't exist
- **Solution**: Ensure IDs are valid (run create requests first)

### **Issue: 500 Internal Server Error**
- **Cause**: Server error or database issue
- **Solution**: Check backend logs and database connection

---

## 🎯 Testing Checklist

- [ ] All login requests return JWT tokens
- [ ] Admin can create hackathons and teams
- [ ] Participant can create submissions
- [ ] Judge can add evaluations
- [ ] Role-based access control works
- [ ] Unauthorized requests are blocked
- [ ] Leaderboard displays correct rankings
- [ ] CRUD operations work for all entities
- [ ] Error handling works properly

---

## 📝 Tips for Testing

1. **Run in Order**: Execute requests in the recommended order to set variables
2. **Check Variables**: Verify that variables are set after each successful request
3. **Test Negative Cases**: Try unauthorized access and invalid data
4. **Use Console**: Check Postman console for detailed error messages
5. **Save Responses**: Save important responses for reference
6. **Test Edge Cases**: Try empty strings, invalid dates, negative scores

---

## 🔗 Additional Resources

- **API Documentation**: Check the backend README for detailed API specs
- **Database Schema**: Refer to entity definitions for data structure
- **Error Codes**: Review GlobalExceptionHandler for error response format
- **Security**: JWT tokens expire after 24 hours

Happy Testing! 🚀
