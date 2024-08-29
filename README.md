# Quizzy-App - Java-Based Quiz Management System

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)

## Overview

**Quizzy-App** is a sophisticated Java-based Quiz Management System, meticulously crafted to streamline the processes of quiz creation, administration, and evaluation in educational environments. The system is architected using the Model-View-Presenter (MVP) pattern, ensuring a modular, maintainable, and scalable codebase. By supporting distinct user roles—students and teachers—Quizzy-App enables comprehensive data handling and interaction, from quiz management to student performance tracking.

## Features

- **Role-Based Access Control**: Implements secure and distinct functionality for `Student` and `Teacher` roles.
- **Dynamic Quiz Management**: Teachers can create, update, and delete quizzes, manage questions, and assign point values.
- **Persistent Data Storage**: Efficiently stores user data, quiz content, and results using serialized objects.
- **Student Performance Analytics**: Tracks individual student progress, scores, and quiz completion metrics.
- **Command-Line Interface (CLI)**: A user-friendly CLI enables seamless interaction with the system.
- **Data Integrity**: Ensures secure handling of user credentials and quiz data through encryption and validation.

## Architecture

### Model-View-Presenter (MVP)

The application adheres to the MVP architectural pattern, promoting a clear separation of concerns:

- **Model**: Encapsulates core business logic and data structures, including `Student`, `Teacher`, `Quiz`, and `QuizBoard` classes.
- **View**: The `CommandLineInterface` class acts as the user interface, capturing user inputs and displaying outputs via the command line.
- **Presenter (Controller)**: The `QuizzyController` manages the flow of data between the Model and View, executing business logic in response to user inputs.

### Detailed Workflow

1. **User Interaction**: Users interact with the system through the `CommandLineInterface`, selecting actions such as quiz creation, participation, or score viewing.
2. **Data Flow**: The `CommandLineInterface` forwards user requests to the `QuizzyController`, which processes the requests by interacting with the appropriate services and models.
3. **Business Logic**: The `QuizzyController` utilizes services such as `TeacherService`, `StudentService`, `QuizService`, and `QuizBoardService` to execute core functionalities like authentication, quiz management, and score tracking.
4. **Data Persistence**: All data, including quiz questions, user credentials, and results, is serialized and stored persistently using the Java I/O framework.

## Technologies Used

- **Java SE 11**: The primary programming language used to develop the application.
- **JUnit 5**: Employed for unit testing to ensure code reliability and correctness.
- **Git**: Version control system to manage and track changes in the codebase.
- **IntelliJ IDEA**: The Integrated Development Environment (IDE) used for development.

## Project Structure

The project is organized into a clear package structure:

```
Quizzy-App/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Quizzy/
│   │   │   │   ├── controller/
│   │   │   │   │   └── QuizzyController.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── AccountType.java
│   │   │   │   │   ├── Quiz.java
│   │   │   │   │   ├── QuizBoard.java
│   │   │   │   │   ├── Student.java
│   │   │   │   │   └── Teacher.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── QuizBoardService.java
│   │   │   │   │   ├── QuizService.java
│   │   │   │   │   ├── StudentService.java
│   │   │   │   │   └── TeacherService.java
│   │   │   │   ├── view/
│   │   │   │   │   └── CommandLineInterface.java
│   │   │   │   └── MainApp.java
│   └── test/
│       └── java/
│           └── Quizzy/
│               └── controller/
│                   └── QuizzyControllerTest.java
└── README.md
```

- **`controller`**: Contains the `QuizzyController`, the mediator between the View and Model layers.
- **`model`**: Includes the data structures representing the core entities such as `Student`, `Teacher`, `Quiz`, and `QuizBoard`.
- **`service`**: Houses the business logic that operates on the models, encapsulating operations related to quizzes, students, teachers, and quiz boards.
- **`view`**: The command-line interface for user interaction.
- **`test`**: Contains unit tests for the controller to ensure the correct implementation of business logic.

## Installation

### Prerequisites

Ensure the following are installed on your system:

- **Java Development Kit (JDK) 11 or higher**
- **Git**
- **IntelliJ IDEA** or another Java IDE

### Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/rohan-chandrashekar/Quizzy-App.git
   cd Quizzy-App
   ```

2. **Compile the Code**:
   Navigate to the `src` directory and compile the Java classes:
   ```bash
   cd src
   javac main/java/Quizzy/MainApp.java main/java/Quizzy/view/CommandLineInterface.java
   ```

3. **Run the Application**:
   Execute the main class to start the application:
   ```bash
   java -cp . main.java.Quizzy.MainApp
   ```

## Usage

### Teacher Role

- **Creating a Quiz**: Teachers can initiate the creation of a new quiz, adding questions, setting correct answers, and assigning points.
- **Managing Quizzes**: Modify existing quizzes or remove outdated ones.
- **Tracking Student Performance**: Review student scores and quiz completion records.

### Student Role

- **Taking Quizzes**: Participate in quizzes available under the courses enrolled.
- **Viewing Results**: Access scores and review quiz outcomes.

### Data Storage

The application utilizes serialized objects for persistent data storage. This approach ensures that all user data and quiz content are preserved across sessions.
