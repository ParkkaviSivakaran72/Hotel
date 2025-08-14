# 🏨 The Royal Crest – Hotel Booking Platform

A full-stack hotel booking system built with **Spring Boot, React.js, PostgreSQL, and Cloudinary**.  
Users can search, view, and book hotel rooms, while admins manage listings and bookings through a secure panel.

---

## 🔗 Frontend
[![GitHub](https://img.shields.io/badge/GitHub-Frontend-blue?logo=github)](https://github.com/ParkkaviSivakaran72/Hotel)

## 🔗 Backend
[![GitHub](https://img.shields.io/badge/GitHub-Backend-blue?logo=github)](https://github.com/ParkkaviSivakaran72/Hotel)

---

## 🚀 Features
### 🏨 Hotel Room Booking
- 🔍 Real-time room availability
- 🎯 Search & filter by location, price, and amenities
- 🏠 Room detail pages with image gallery
- 📝 Booking form with date range picker
- 📄 Pagination for room listings

### 🖼️ Cloudinary Integration
- ☁️ Upload room images securely to Cloudinary
- 🌐 Serve static image URLs in frontend

### 👤 User Profile Management
- 🔐 Register/Login system
- ✏️ Edit profile (with image upload)
- 📋 View bookings history
- ❌ Cancel bookings

---

## ⚙️ Tech Stack

### Frontend
- React.js
- Tailwind CSS
- Axios for HTTP requests
- React Router DOM
- JWT storage in localStorage

### Backend
- Spring Boot
- PostgreSQL
- REST APIs
- JWT Authentication
- Cloudinary for image storage

---

### Notes
- **Frontend:** `components/` for reusable UI parts, `pages/` for route-level pages, `services/` for API calls and helper functions.  
- **Backend:** `controller/` for endpoints, `service/` for business logic, `repository/` for database interactions, `model/` for entity classes, and `config/` for security/JWT setup.  
- Keep **frontend and backend completely separate** for clarity and maintainability.


## 🔐 Token Management
- Upon login, the JWT is stored securely in localStorage and automatically added to Authorization headers in all protected routes.

---

## 🧪 Testing & Documentation
- ✅ Postman collections for API testing

