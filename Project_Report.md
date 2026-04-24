# Project Report: Student Productivity & Placement Tracker System

## 1. Introduction
Managing academics alongside placement preparation is one of the biggest challenges students face today. With assignments piling up and technical interviews around the corner, it is very easy to lose track of what needs to be done. To solve this problem, I developed the **Student Productivity & Placement Tracker System**. 

This is a full-stack web application designed to help students organize their daily study tasks, keep track of assignment deadlines, and continuously monitor their placement readiness. The goal of this project was to create a centralized, easy-to-use dashboard that acts like a personal academic assistant.

## 2. Objectives
The main objectives of this project were:
* To build a secure platform where students can register and maintain their own private study data.
* To create a task manager that allows users to easily add, update, and prioritize their academic subjects and deadlines.
* To provide a placement readiness tracker where students can input their scores in Aptitude, Coding, and Interviews, and see a visual representation of their strengths and weaknesses.
* To implement a Smart Recommendation Engine that acts as an advisor, suggesting exactly what the student should focus on next based on their impending deadlines and current placement scores.

## 3. Technology Stack Used
I decided to use a robust, industry-standard technology stack for this project:
* **Frontend (Client-side):** HTML5, CSS3, and Vanilla JavaScript. I focused heavily on building a clean, modern, "Notion-like" UI using custom CSS to make the dashboard feel premium and intuitive. I also used **Chart.js** to render dynamic visual graphs.
* **Backend (Server-side):** Java with Jakarta EE (Servlets). Servlets act as the bridge between the frontend UI and the database, processing requests and sending back JSON data.
* **Database:** MySQL. Used to persistently store user credentials, task lists, and placement scores.
* **Tools & Environment:** Eclipse IDE, Maven (for dependency management), and Apache Tomcat 9 (as the web server).

## 4. System Architecture
The application follows a standard **Model-View-Controller (MVC) architecture pattern**:
1. **View (Frontend):** The user interacts with the web pages (Dashboard, Task Manager). JavaScript makes asynchronous (AJAX/Fetch) calls to the backend.
2. **Controller (Servlets):** The Java Servlets (`AuthServlet`, `TaskServlet`, `PlacementServlet`) receive the HTTP requests, process the logic, and communicate with the database.
3. **Model & DAO (Database layer):** Data Access Objects (`UserDAO`, `TaskDAO`) use JDBC to execute SQL queries. The data is retrieved, packaged into Java objects, converted to JSON using Google Gson, and sent back to the frontend.

## 5. Module Descriptions
The system is divided into four primary modules:

### 5.1. Authentication Module
Handles secure user registration and login. A user must create an account to access the dashboard. Their session is maintained using HTTP Sessions, ensuring their data remains private.

### 5.2. Task Management Module
This is where the user spends most of their time. They can add tasks by specifying the subject, topic, deadline, and priority. They can also mark tasks as "Completed" or delete them when no longer needed. 

### 5.3. Placement Readiness Module
Students input their latest practice scores out of 100 in three key areas: Aptitude, Coding, and Interviews. The system takes these numbers and instantly generates a Radar Chart, giving the student a clear visual indication of which area needs more practice.

### 5.4. Smart Recommendation Engine
This is the standout feature of the application. The system runs an algorithm in the backend that analyzes all the user's data. If a task deadline is less than 3 days away, it aggressively recommends focusing on that subject. If no deadlines are urgent, it checks the placement scores. If the coding score drops below 50%, it advises the user to practice Data Structures and Algorithms. It effectively acts as a personalized study guide.

## 6. Database Schema Design
The MySQL database consists of three main tables:
* **users:** Stores `id`, `name`, `email`, and `password`.
* **tasks:** Stores `id`, `user_id` (Foreign Key), `subject`, `topic`, `deadline`, `status` (Pending/Completed), and `priority`.
* **placement_scores:** Stores `id`, `user_id` (Foreign Key), `aptitude_score`, `coding_score`, and `interview_score`.

## 7. Challenges & Learnings
While building this project, one of the main challenges was connecting the Java backend with the JavaScript frontend using JSON. Learning how to properly format and parse data using the `Gson` library was a great learning experience. Additionally, designing a database schema with proper Foreign Key constraints helped me understand relational databases much better.

## 8. Conclusion
The Student Productivity & Placement Tracker System successfully meets its goal of providing a structured, visual, and intelligent environment for students. It eliminates the need for messy physical planners or multiple different apps by combining academic tracking and placement preparation into one cohesive dashboard.
