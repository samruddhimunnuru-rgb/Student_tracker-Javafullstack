# Student Productivity & Placement Tracker System

A complete Full-Stack Java Web Application built to help students track their daily study tasks, manage upcoming assignment deadlines, and monitor their placement readiness scores using a beautiful Notion-like dashboard.

## 🚀 Features

* **Authentication System**: Secure user registration and login functionality.
* **Smart Dashboard**: A sleek, premium dashboard featuring an interactive UI built with pure CSS.
* **Task Manager**: Add, update, and delete study tasks, set deadlines, and organize by priority (High, Medium, Low).
* **Placement Readiness Tracker**: Keep track of Aptitude, Coding, and Interview scores visualized using dynamic Radar charts.
* **Smart AI Recommendation Engine**: Automatically analyzes impending deadlines and low scores to suggest what you should focus on next.
* **Productivity Analytics**: View visual breakdowns of completed vs. pending tasks.

## 💻 Tech Stack

* **Frontend**: HTML5, CSS3 (Custom Premium Notion-like Design), Vanilla JavaScript, Chart.js
* **Backend**: Java, Jakarta EE (Servlets)
* **Database**: MySQL (JDBC)
* **Build Tool**: Maven
* **Server**: Apache Tomcat 9
* **Data Format**: JSON (Google Gson)

## 🛠️ Setup Instructions (Eclipse & Tomcat)

1. **Database Setup**
   * Open MySQL Workbench and log in with your root credentials.
   * Open and execute the `sql/schema.sql` script to create the `student_tracker` database and its tables.

2. **Import Project**
   * Open Eclipse IDE.
   * Go to `File -> Import -> Maven -> Existing Maven Projects`.
   * Select the `StudentTracker` folder containing the `pom.xml` and click Finish.

3. **Run Application**
   * Right-click the `StudentTracker` project in the Package Explorer.
   * Select `Run As -> Run on Server`.
   * Choose your configured `Tomcat v9.0 Server` and hit Finish.
   * Access the application at `http://localhost:8080/StudentTracker/login.html`.

## 📸 Screenshots

*(You can add screenshots of your dashboard here!)*

## 🤝 Contribution

This project was built as a full-stack Java demonstration. Feel free to fork it, add new features, and submit pull requests!
