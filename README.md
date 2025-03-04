# DVD Rental Store

## Project Overview

This is a full-stack DVD Rental Store application built with Spring Boot (Backend) and React (Frontend). The application allows users to manage DVD rentals, including film catalog, customer management, and rental tracking.

## 📸 Project Screenshot

![Project Screenshot](/docs/image.png)

## 🚀 Technology Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL

### Frontend
- React
- React Hooks
- Axios (for API calls)

## 📋 Database Schema

The application uses the following main tables:
- `actors`: Stores information about film actors
- `categories`: Manages film categories
- `customers`: Tracks customer details
- `films`: Contains film information
- `rentals`: Manages film rental transactions

## 🔑 Key Features

- Search films by title, category, actor, or description
- Create and manage films
- Track customer rentals
- Calculate late fees
- Manage film availability


### Prerequisites
- Java 17+
- Node.js
- PostgreSQL

### Backend Setup
1. Clone the repository
2. Configure database connection in `application.properties`
3. Run `./mvnw spring-boot:run`

### Frontend Setup
1. Navigate to frontend directory
2. Run `npm install`
3. Run `npm start`

## 📦 API Endpoints

### Film Controller Endpoints
- `POST /api/films`: Create a new film
- `GET /api/films/search`: Search films with optional filters

