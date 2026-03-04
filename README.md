# Smart School Backend

**Smart School Backend** Smart School Backend is a secure, role-based School Management REST API built using Spring Boot and MySQL.
It is designed to manage a school’s core academic workflow in a simple, controlled, and secure way.

*This system focuses on three main areas:*

-Authentication & Role Management

-Leave Management

-Result Management

The goal of this project is to demonstrate secure backend architecture, role-based authorization, structured database design, and clean layered implementation.

---


## Roles

| Role           | Description                                           |
|----------------|-------------------------------------------------------|
| SUPER_ADMIN    | System administrator; can generate invite codes.     |
| PRINCIPAL      | Head of school; can assign classes to teachers.      |
| CLASS_TEACHER  | Handles leaves and students of their class/section. |
| TEACHER        | Subject teacher (optional features can be extended). |
| STUDENT        | Can apply for leave and view leave status.           |

---
##  Project Features

This Project is focus on these features.

***1.Authentication & Roles***

**The system supports Four roles:**

>Principal

>Class Teacher

>Teacher

>Student

**Features:**

>JWT-based secure authentication

>Role-based access control (RBAC)

>Invite-code based registration

>OTP verification before account activation

>Strict validation of:

>>Role

>>Class

>>Section

>>Intended person name

**Security Enforcement**

>Registration is only possible using a valid invite code.

>Invite codes are mapped to a specific role, class, and section.

>Users cannot modify assigned class, section, or role during registration.

>Unauthorized role access is strictly prevented.


***Leave Management System***

*Student can -*

>Apply for leave

>Provide from date, to date, and reason

>View leave status

*Class Teacher can-*

>View leave requests of their own class only

>Approve leave

>Reject leave

*Security Rules:*

>Leave requests are automatically routed by class and section 

    Example-
      {
      Student (ClassX Section A) -> Class_Teacher (ClassX Section A) 
      }

>Class Teachers cannot access other class leave data

>JWT required for every request

***Result Management System***

Class Teacher can-

>Add subject marks for students

>Provide total marks and exam type

>Supported Exam Types
>>MONTHLY
>>FIRST_TERM
>>QUARTERLY
>>SECOND_TERM
>>HALF_YEARLY
>>ANNUAL

*System automatically calculates:*

>Total marks obtained

>Percentage

>Pass / Fail status

Student can 

>View their own results only

---
# 🛠 Technology Stack

---

## 🚀 Backend

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)

---

## 🗄 Database

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

---

## 📄 API Documentation

![Swagger](https://img.shields.io/badge/Swagger_OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

---

## ⚙ Build Tool

![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
---

## 📁 Project Structure

```text
src/main/java/com/School/Smart/Backend

├── config
│   └── SwaggerConfig.java
│
├── Controller
│   ├── AuthController.java
│   ├── InviteCodeController.java
│   ├── RegistrationController.java
│   ├── ProfileController.java
│   ├── LeaveSystem
│   │   └── LeaveController.java
│   └── ResultSystemController
│       └── ResultController.java
│
├── DTO
│   ├── LoginRequest / LoginResponse
│   ├── RegisterRequest
│   ├── GenerateInviteRequest
│   ├── LeaveRequest / LeaveResponse
│   └── ResultRequest / ResultResponse
│
├── entity
│   ├── InviteCode.java
│   ├── Leave.java
│   ├── Result.java
│
├── model
│   ├── User.java
│   ├── Role.java
│   └── OtpVerification.java
│
├── repository
│   ├── UserRepository.java
│   ├── InviteCodeRepository.java
│   ├── LeaveRepository.java
│   └── ResultRepository.java
│
├── security
│   ├── SecurityConfiguration.java
│   ├── JwtService.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
│
└── Service
    ├── RegistrationService.java
    ├── InviteCodeService.java
    ├── LeaveService.java
    └── ResultService.java


```
---
## Table of Contents

- [Architecture](#architecture)
- [Installation & Setup](#installation--setup)
- [API Documentation](#api-documentation)
- [Sample API Requests & Responses](#sample-api-requests--responses)
- [Security Design](#security-design)
- [Future Improvements](#future-improvements) 

---

## Architecture

- **Spring Boot** backend with clean architecture principles.  
- **MySQL** database for persistence.  
- **Spring Security** for authentication and authorization.  
- **JWT** for stateless security.  
- Separate packages for:
  - Controllers (`Controller`)  
  - Services (`Service`)  
  - Repositories (`repository`)  
  - DTOs (`DTO`)  
  - Entities (`entity`)  
  - Security (`security`)  

---
## Installation & Setup

*Clone Repository*

    git clone <your-repository-url>
    cd smart-school-backend

*Create Database*

    CREATE DATABASE schooldatabase;

*Configure application.yaml*

    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/schooldatabase
        username: your_db_username
        password: your_db_password

      jpa:
        hibernate:
          ddl-auto: update

    jwt:
      secret: your_jwt_secret_key
      expiration: 86400000

*Run Application*

    mvn clean install
    mvn spring-boot:run

*Server runs at:*

    http://localhost:8080

---
## API Documentation

*Swagger UI:*

    http://localhost:8080/swagger-ui/index.html

*OpenAPI docs:*

    /v3/api-docs

-**Includes:**

  -Auth Controller

  -Registration Controller

  -Invite Code Controller

  -Profile Controller

  -Leave Controller

  -Result Controller


---
## Sample API Requests & Responses

*Register*

>POST /api/auth/register

  Request

    {
      "email": "student@gmail.com",
      "password": "password123",
      "role": "STUDENT",
      "inviteCode": "INV-12345",
      "fullname": "Rahul Sharma"
    }
    
Response

    "OTP Sent successfully"


*Verify OTP*

>POST /api/auth/verify-otp

  Request
  
    {
  
    "email": "student@gmail.com",
  
    "otp": "288839"

    }
  Response

    "OTP verified successfully"

*Login*

>POST /api/auth/login

  Request
    
    {
  
    "email": "student@gmail.com",
  
    "password": "password123"

    }
  Response

    {
  
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  
    "role": "STUDENT",
  
    "userId": 15

    }


*Apply Leave*

>POST /leave/Apply

  Request
    
    {
  
    "studentid": 15,
  
    "fromDate": "2026-03-10",
  
    "toDate": "2026-03-12",
  
    "reason": "Medical leave",
  
    "className": "Class X",
  
    "section": "A"

    }
  Response

    Leave applied successfully

*Get Requested Leaves (Class Teacher)*

>GET /leave/Requested-Leaves

  Response 

    [
      {
        "id": 5,
        "studentName": "Rahul Sharma",
        "studentId": 15,
        "className": "Class X",
        "section": "A",
        "gurdianNo": "9876543210",
        "teacherId": 3,
        "fromDate": "2026-03-10",
        "toDate": "2026-03-12",
        "reason": "Medical leave",
        "status": "PENDING"
      },
      {
        "id": 6,
        "studentName": "Anjali Verma",
        "studentId": 18,
        "className": "Class X",
        "section": "A",
        "gurdianNo": "9123456780",
        "teacherId": 3,
        "fromDate": "2026-03-15",
        "toDate": "2026-03-16",
        "reason": "Family function",
        "status": "PENDING"
      }
    ]

*Add Result*

>POST /Result/Add

  Request
    
    {
      "studentId": 15,
      "studentName": "Rahul Sharma",
      "subjects": {
        "Math": 90,
        "Science": 85,
        "English": 88
      },
      "totalMarks": 300,
      "examType": "MONTHLY"
    }

  Response
    
    {
      "id": 10,
      "subjects": {
        "Math": 90,
        "Science": 85,
        "English": 88
      },
      "totalMarksObtained": 263,
      "totalMarks": 300,
      "percentage": 87.6,
      "passStatus": "OUTSTANDING"
    }


*Get Student Results*

>GET /Result/student

  Response
    
    [
      {
        "id": 10,
        "subjects": {
          "Math": 90,
          "Science": 85,
          "English": 88
        },
        "totalMarksObtained": 263,
        "totalMarks": 300,
        "percentage": 87.6,
        "passStatus": "OUTSTANDING"
      }
    ]


---
## Security Design

>JWT-based stateless authentication

>Role-based endpoint authorization

>Invite-code controlled registration

>OTP verification before activation

>BCrypt password encryption

>Strict class & section validation

---
## Future Improvements

-*Result Management Enhancements*

>Add grade system (A, B, C, etc.) based on percentage

>Generate PDF report cards for students

>Provide result comparison analytics (term-wise performance tracking)

-*Notification System*

>Integrate email service (SMTP) for:

>>OTP delivery

>>Leave status updates

>>Result publication alerts

-*Testing Improvements*

>Increase unit and integration test coverage

>Add Testcontainers for database testing

>Implement API integration testing
