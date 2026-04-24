🔴 Student Productivity & Placement Tracker System

A complete Full-Stack Java Web Application built to help students track their daily study tasks, manage upcoming assignment deadlines, and monitor their placement readiness scores using a beautiful Notion-like dashboard.

 Features

1.Authentication System: Secure user registration and login functionality.
2.Smart Dashboard: A sleek, premium dashboard featuring an interactive UI built with pure CSS.
3.Task Manager: Add, update, and delete study tasks, set deadlines, and organize by priority (High, Medium, Low).
4.Placement Readiness Tracker: Keep track of Aptitude, Coding, and Interview scores visualized using dynamic Radar charts.
5.Smart AI Recommendation Engine: Automatically analyzes impending deadlines and low scores to suggest what you should focus on next.
6.Productivity Analytics: View visual breakdowns of completed vs. pending tasks.

▪️Tech Stack

1.Frontend: HTML5, CSS3 (Custom Premium Notion-like Design), Vanilla JavaScript, Chart.js
2.Backend: Java, Jakarta EE (Servlets)
3.Database: MySQL (JDBC)
4.Build Tool: Maven
5.Server: Apache Tomcat 9
6.Data Format: JSON (Google Gson)

▪️Setup Instructions (Eclipse & Tomcat)

1. Database Setup
   1. Open MySQL Workbench and log in with your root credentials.
   2. Open and execute the `sql/schema.sql` script to create the `student_tracker` database and its tables.

2. Import Project
   1.Open Eclipse IDE.
   2.Go to `File -> Import -> Maven -> Existing Maven Projects`.
   3.Select the `StudentTracker` folder containing the `pom.xml` and click Finish.

3. Run Application
   1.Right-click the `StudentTracker` project in the Package Explorer.
   2.Select `Run As -> Run on Server`.
   3.Choose your configured `Tomcat v9.0 Server` and hit Finish.
   4.Access the application at `http://localhost:8080/StudentTracker/login.html`.


 ▪️Contribution

This project was built as a full-stack Java demonstration. Feel free to fork it, add new features, and submit pull requests!
