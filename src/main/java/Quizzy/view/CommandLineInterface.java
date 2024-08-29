package main.java.Quizzy.view;

import main.java.Quizzy.controller.QuizzyController;
import main.java.Quizzy.model.*;

import java.io.Serializable;
import java.util.*;


public class CommandLineInterface implements Serializable {

    QuizzyController quizzyController = new QuizzyController();
    ArrayList<String> coursesEnrolled = new ArrayList<>();
    private Scanner scanner;

    private boolean loginStatus = false;


    // Caching the user Data

    private Map<String, Teacher> teacherInfo = new HashMap<>();

    private Map<String, Student> studentInfo = new HashMap<>();

    private Map<Integer, Quiz> quizData = new HashMap<>();


    // End of Caching the user Data

    private static void teacherMenuMessage() {
        System.out.println("***************************************");
        System.out.println("1. QuizBoard Menu");
        System.out.println("2. view Students");
        System.out.println("3. view Courses");
        System.out.println("4. view My Details");
        System.out.println("5. Delete my Account");
        System.out.println("6. Change Password");
        System.out.println("7. Change Course Details");
        System.out.println("8. Logout");
        System.out.println("Please select an option: ");
        System.out.println("***************************************");
    }

    public void run() {
        load();
        start();
    }

    private void load() {
        System.out.println("Loading Data...");
        try {
            quizzyController.loadData();
            System.out.println("Data Loaded Successfully!");
            System.out.println("***************************************");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void logo() {
        String logo = """

        LOST-SURVEY

                """;
        System.out.println(logo);
    }

    private void introMessage() {
        System.out.println("***************************************");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        
        System.out.println("Please select an option: ");
        System.out.println("***************************************");
    }

    private void start() {
        logo();
        introMessage();
        String line;
        scanner = new Scanner(System.in);
        try {
            do {
                line = scanner.nextLine();
                if (line.length() == 1) {
                    switch (line.charAt(0)) {
                        case '1' -> {
                            login();
                        }
                        case '2' -> {
                            register();
                        }
                        case '3' -> {
                            System.out.println("Thank you for using Lost-Survey!");
                            loginStatus = true;
                            saveData();
                            // Clearing the cache
                            teacherInfo.clear();
                            studentInfo.clear();
                            quizData.clear();
                            // End of Clearing the cache
                            System.exit(0);
                        }
                        case '4' -> {
                            changelog();
                            start();
                        }
                        default -> {
                            System.out.println("Please select a valid option: ");
                            start();
                        }
                    }
                } else {
                    System.out.println("Please select a valid option: ");

                    start();
                }

            } while (line.charAt(0) != '4' || line.length() != 1);
        } catch (Exception e) {
            System.out.println("Please select a valid option: ");
            start();
        }

    }

    private void changelog() {
        System.out.println("***************************************");
        System.out.println("Change Log of the Project");
        System.out.println("***************************************");
        System.out.println("V 1.0 - Initial Release");
        System.out.println("Upcoming Features: - Multiple Choice Questions");
        System.out.println("                   - GUI Interface");
        System.out.println("***************************************");
        start();
    }

    private void login() {
        System.out.println("***************************************");
        System.out.println("*** Login Page ***");
        System.out.println("***************************************");
        System.out.println("1. Admin Login");
        System.out.println("2. User Login");
        System.out.println("3. Back");
        System.out.println("Please select an option: ");
        System.out.println("***************************************");
        String line = scanner.nextLine();
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case '1' -> {
                    teacherLogin();
                }
                case '2' -> {
                    studentLogin();
                }
                case '3' -> {
                    start();
                }
                default -> {
                    System.out.println("Please select a valid option: ");
                    login();
                }

            }
        } else {
            System.out.println("Please select a valid option: ");
            start();
        }
    }

    private void studentLogin() {
        System.out.println("***************************************");
        System.out.println("Please enter your Username: ");
        String userName = scanner.nextLine().trim();
        System.out.println("Please enter your Password: ");
        String password = scanner.nextLine().trim();
        try {
            String hashedPassword = quizzyController.hashPassword(password);
            Map<String, Student> studentData = quizzyController.validateStudentLogin(userName, hashedPassword);
            if (studentData.isEmpty()) {
                throw new IllegalArgumentException("Invalid Username or Password!");
            }
            studentInfo = studentData;
            System.out.println("Login Successful!");
            loginStatus = true;
            System.out.println("***************************************");
            System.out.println("Welcome " + userName + "!");
            studentMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again!");
            start();
        }
    }

    private void studentMenu() {
        studentMenuMessage();
        String line = scanner.nextLine();
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case '1' -> {
                    takeQuiz();
                }
                case '2' -> {
                    viewQuizResults();
                }
                case '3' -> {
                    viewStudentCourses();
                }
                case '4' -> {
                    viewMyProfile();
                }
                case '5' -> {
                    changeStudentPassword();
                }
                case '6' -> {
                    deleteStudentAccount();
                }
                case '7' -> {
                    System.out.println("Logging out...");
                    loginStatus = false;
                    saveData();
                    start();
                }
                case '8' -> {
                    System.out.println("Thank you for using Quizzy!");
                    loginStatus = true;
                    saveData();
                    // Clearing the cache
                    teacherInfo.clear();
                    studentInfo.clear();
                    quizData.clear();
                    // End of Clearing the cache
                    System.exit(0);
                }
                default -> {
                    System.out.println("Please select a valid option: ");
                    studentMenu();
                }
            }
        } else {
            System.out.println("Please select a valid option: ");
            studentMenu();
        }
    }

    private void viewQuizResults() {
        boolean courseEnrolled = false;
        System.out.println("***************************************");
        System.out.println("Quiz Results: ");
        System.out.println("***************************************");
        System.out.println("Enter the Quiz ID to view the results: ");
        int quizBoardID = 0;
        try {
            quizBoardID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Quiz ID!");
            viewQuizResults();
        }
        try {
            ArrayList<String> courseName = new ArrayList<>();
            for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
                courseName = entry.getValue().getCoursesEnrolled();
            }
            String quizAllocatedCourse;
            quizAllocatedCourse = quizzyController.getQuizBoardCourse(quizBoardID);
            String quizBoardName = quizzyController.getQuizBoardName(quizBoardID);
            for (String course : courseName) {
                if (course.equalsIgnoreCase(quizAllocatedCourse)) {
                    courseEnrolled = true;
                    break;
                }
            }
            if (!courseEnrolled) {
                throw new IllegalArgumentException("You are not enrolled in this course!");
            }
            Map<Integer, Float> quizResults = quizzyController.getQuizResults(quizBoardID);
            if (quizResults.isEmpty()) {
                throw new IllegalArgumentException("No results found!");
            }
            System.out.println("***************************************");
            System.out.println("Quiz Results for " + quizBoardName.toUpperCase() + ": ");
            System.out.println("***************************************");
            for (Map.Entry<Integer, Float> entry : quizResults.entrySet()) {
                System.out.println("Your Score: " + entry.getValue());
            }
            System.out.println("Total Score: " + quizzyController.getQuizTotalScore(quizBoardID));
            System.out.println("***************************************");
            studentMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again!");
            studentMenu();
        }

    }

    private void takeQuiz() {
        boolean courseEnrolled = false;
        String userName = "";
        for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
            userName = entry.getValue().getUsername();
        }
        int quizBoardID = 0;
        System.out.println("***************************************");
        System.out.println("Please enter the QUIZ-BOARD - ID: ");
        try {
            quizBoardID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Quiz ID!");
            takeQuiz();
        }

        System.out.println("***************************************");
        System.out.println("Instructions: ");
        System.out.println("1. You cannot leave the quiz once started!");
        System.out.println("2. You can only take a quiz once!");
        System.out.println("3. Click enter after typing your answer!");
        System.out.println("Are you sure you want to take this quiz? (Y/N)");
        String line = scanner.nextLine().trim().toLowerCase();
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case 'y' -> {
                    try {
                        quizzyController.checkIfQuizTaken(userName, quizBoardID);
                        quizData = quizzyController.takeQuiz(quizBoardID);
                        // Student course and quiz validation
                        ArrayList<String> courseName = new ArrayList<>();
                        for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
                            courseName = entry.getValue().getCoursesEnrolled();
                        }
                        String quizAllocatedCourse;
                        quizAllocatedCourse = quizzyController.getQuizBoardCourse(quizBoardID);
                        String quizBoardName = quizzyController.getQuizBoardName(quizBoardID);
                        for (String course : courseName) {
                            if (course.equalsIgnoreCase(quizAllocatedCourse)) {
                                System.out.println("Quiz Started!");
                                quizStart(quizAllocatedCourse, quizBoardID, quizBoardName);
                                courseEnrolled = true;
                                break;
                            }
                        }
                        if (!courseEnrolled) {
                            throw new IllegalArgumentException("You are not enrolled in this course!");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("Please try again!");
                        studentMenu();
                    }
                }
                case 'n' -> {
                    studentMenu();
                }
                default -> {
                    System.out.println("Please select a valid option: ");
                    takeQuiz();
                }
            }
        } else {
            System.out.println("Please select a valid option: ");
            takeQuiz();
        }

    }

    private void quizStart(String quizAllocatedCourse, int quizBoardID, String quizBoardName) {

        int totalQuestions = 0;
        int totalMarks = 0;
        float studentScore = 0;
        String studentName = "";
        String studentAnswer;
        String correctAnswer;
        for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
            studentName = entry.getKey();
        }
        totalQuestions = quizData.size();
        for (Map.Entry<Integer, Quiz> entry : quizData.entrySet()) {
            totalMarks += entry.getValue().getQuizPoints();
        }
        System.out.println("***************************************");
        System.out.println("Quiz Board: " + quizBoardName.toUpperCase());
        System.out.println("Quiz-Board ID: " + quizBoardID);
        System.out.println("Course: " + quizAllocatedCourse.toUpperCase());
        System.out.println("Total Questions: " + totalQuestions);
        System.out.println("Total Marks: " + totalMarks);
        System.out.println("***************************************");
        int questionNumber = 1;
        for (Map.Entry<Integer, Quiz> entry : quizData.entrySet()) {
            System.out.println("Question " + questionNumber + ": " + entry.getValue().getQuizQuestion());
            correctAnswer = entry.getValue().getCorrectAnswer();
            studentAnswer = scanner.nextLine().trim().toLowerCase();
            if (studentAnswer.equals(correctAnswer.toLowerCase())) {
                studentScore += entry.getValue().getQuizPoints();
            }
            questionNumber++;
        }
        System.out.println("***************************************");
        System.out.println("Quiz Completed!");
        System.out.println("Your Score: " + studentScore + " / " + totalMarks);
        System.out.println("Your Percentage: " + (studentScore / totalMarks) * 100 + "%");
        System.out.println("***************************************");

        // Updating Services after quiz completion.
        quizzyController.updateStudentScore(studentName, quizBoardID, studentScore);
        quizzyController.updateStudentScoreBoard(studentName, quizBoardID, studentScore);

        // Final Call
        saveData();
        studentMenu();
    }

    private void deleteStudentAccount() {
        System.out.println("Are you sure you want to delete your account? (Y/N)");
        String line = scanner.nextLine().trim().toLowerCase();
        String userName = "";
        for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
            userName = entry.getKey();
        }
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case 'y' -> {
                    quizzyController.deleteStudentAccount(userName);
                    System.out.println("Account Deleted!");
                    loginStatus = false;
                    saveData();
                    start();
                }
                case 'n' -> {
                    studentMenu();
                }

                default -> {
                    System.out.println("Please select a valid option: ");
                    deleteStudentAccount();
                }
            }
        } else {
            System.out.println("Please select a valid option: ");
            studentMenu();
        }
    }

    private void changeStudentPassword() {
        System.out.println("***************************************");
        System.out.println("*** Changing Password ***");
        System.out.println("***************************************");
        System.out.println("Please enter your current password: ");
        String currentPassword = scanner.nextLine().trim();
        String userName = "";
        for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
            userName = entry.getKey();
        }
        try {
            quizzyController.validateStudentPassword(userName, currentPassword);
            System.out.println("Please enter your new password: ");
            String newPassword = scanner.nextLine().trim();
            quizzyController.changeStudentPassword(userName, newPassword);
            System.out.println("Password changed successfully!");
            saveData();
            studentMenu();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            studentMenu();
        }
    }

    private void viewStudentCourses() {
        System.out.println("***************************************");
        System.out.println("*** Your Courses ***");
        System.out.println("***************************************");
        ArrayList<String> yourCourses = new ArrayList<>();
        for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
            yourCourses = entry.getValue().getCoursesEnrolled();
        }
        int i = 0;
        System.out.println("The courses you are teaching are:");
        for (String course : yourCourses) {
            System.out.println(i + 1 + ". " + course.toUpperCase());
            ++i;

        }
        studentMenu();
    }

    private void viewMyProfile() {
        String studentName = "";
        for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
            studentName = entry.getKey();
        }
        System.out.println("***************************************");
        System.out.println("User Name: " + studentName);
        System.out.println("***************************************");
        for (Map.Entry<String, Student> entry : studentInfo.entrySet()) {
            System.out.println("Username: " + entry.getValue().getUsername());
            System.out.println("Email: " + entry.getValue().getEmail());
            System.out.println("Account Type: " + entry.getValue().getAccountType());
            System.out.println("Date Created: " + entry.getValue().getDateCreated());
            System.out.println("***************************************");

        }

    }

    private void studentMenuMessage() {
        String Message = """
                ***************************************
                *** User Menu ***
                ***************************************
                1. Take a Quiz
                2. view Quiz Results
                3. view My Courses
                4. view My Profile
                5. Change Password
                6. Delete Account
                7. Logout
                8. Exit the Application
                Please select an option:
                ***************************************""";
        System.out.println(Message);
    }


    private void teacherLogin() {
        System.out.println("***************************************");
        System.out.println("Please enter your Username: ");
        String userName = scanner.nextLine().trim();
        System.out.println("Please enter your Password: ");
        String password = scanner.nextLine().trim();
        try {
            String hashedPassword = quizzyController.hashPassword(password);
            Map<String, Teacher> teacherData = quizzyController.validateTeacherLogin(userName, hashedPassword);
            if (teacherData.isEmpty()) {
                throw new IllegalArgumentException("Invalid Username or Password!");
            }
            teacherInfo = teacherData;
            System.out.println("Login Successful!");
            loginStatus = true;
            System.out.println("***************************************");
            System.out.println("Welcome " + userName + "!");
            teacherMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again!");
            start();
        }

    }

    private void teacherMenu() {
        teacherMenuMessage();
        String line = scanner.nextLine();
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case '1' -> {
                    quizMenu();
                }
                case '2' -> {
                    viewStudents();
                }
                case '3' -> {
                    viewTeacherCourses();
                }
                case '4' -> {
                    viewTeacherDetails();
                }
                case '5' -> {
                    deleteTeacherAccount();
                }
                case '6' -> {
                    changeTeacherPassword();
                }
                case '7' -> {
                    changeCourseDetails();
                }
                case '8' -> {
                    System.out.println("Logging out...");
                    loginStatus = false;
                    saveData();
                    start();
                }
                default -> {
                    System.out.println("Please select a valid option: ");
                    teacherMenu();
                }
            }
        } else {
            System.out.println("Please select a valid option: ");
            teacherMenu();
        }

    }

    private void quizMenu() {
        System.out.println("***************************************");
        System.out.println("Quiz Menu");
        System.out.println("***************************************");
        System.out.println("1. QuizBoard");
        System.out.println("2. view Quiz Results");
        System.out.println("3. Return to Admin Menu");
        System.out.println("4. Logout");
        System.out.println("5. Exit the Application");
        System.out.println("***************************************");
        System.out.println("Please enter your choice :");
        String line = scanner.nextLine().trim();
        if (line.length() == 1 && Character.isDigit(line.charAt(0))) {
            switch (line.charAt(0)) {
                case '1' -> {
                    createAQuiz();
                }
                case '2' -> {
                    viewTeacherQuizResults();
                }
                case '3' -> {
                    System.out.println("Returning to Main Menu...");
                    System.out.println("***************************************");
                    teacherMenu();
                }
                case '4' -> {
                    System.out.println("Logging out of the Account...");
                    loginStatus = false;
                    saveData();
                }
                case '5' -> {
                    System.out.println("Exiting the application...");
                    loginStatus = true;
                    saveData();
                    //Clear the cache
                    teacherInfo.clear();
                    studentInfo.clear();
                    quizData.clear();
                    System.exit(0);
                }
            }
        } else {
            System.out.println("Please enter a valid option !");
            quizMenu();
        }
    }

    private void viewTeacherQuizResults() {
        int quizBoardID = 0;
        String quizBoardName;
        System.out.println("***************************************");
        System.out.println("Quiz Results");
        System.out.println("***************************************");
        System.out.println("Enter the QuizBoard ID:");
        try {
            quizBoardID = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid QuizBoard ID!");
            viewTeacherQuizResults();
        }
        try {
            Map<String, Float> quizResults = quizzyController.viewTeacherQuizResults(quizBoardID);
            quizBoardName = quizzyController.getQuizBoardName(quizBoardID);
            if (quizResults.isEmpty()) {
                throw new IllegalArgumentException("No Quiz Results Found!");
            }
            System.out.println("QuizBoard Name: " + quizBoardName);
            System.out.println("***************************************");
            for (Map.Entry<String, Float> entry : quizResults.entrySet()) {
                System.out.println("User Name: " + entry.getKey() + " \nScore: " + entry.getValue());
            }
            System.out.println("***************************************");
            quizMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again!");
            quizMenu();
        }


    }


    private void createAQuiz() {
        System.out.println("***************************************");
        System.out.println("QuizBoard");
        System.out.println("***************************************");
        System.out.println("1. Create a QuizBoard Board");
        System.out.println("2. Edit a QuizBoard Board");
        System.out.println("3. Delete a QuizBoard Board");
        System.out.println("4. view QuizBoard Boards");
        System.out.println("5. Back to Main Menu");
        System.out.println("6. Log out");
        System.out.println("7. Exit the Application");
        System.out.println("***************************************");
        System.out.println("Please enter your choice :");
        String line = scanner.nextLine().trim();
        if (line.length() == 1 && Character.isDigit(line.charAt(0))) {
            switch (line.charAt(0)) {
                case '1' -> {
                    createQuizBoard();
                }
                case '2' -> {
                    editQuizBoard();
                }
                case '3' -> {
                    deleteQuizBoard();
                }
                case '4' -> {
                    viewQuizBoards();
                }
                case '5' -> {
                    System.out.println("Returning to Main Menu...");
                    System.out.println("***************************************");
                    teacherMenu();
                }
                case '6' -> {
                    System.out.println("Logging out of the Account...");
                    loginStatus = false;
                    saveData();
                }
                case '7' -> {
                    System.out.println("Exiting the application...");
                    loginStatus = true;
                    saveData();
                    // Clear the cache
                    teacherInfo.clear();
                    studentInfo.clear();
                    quizData.clear();
                    // End of cache
                    System.exit(0);
                }
            }
        } else {
            System.out.println("Please enter a valid option !");
            quizMenu();
        }

    }

    private void editQuizBoard() {
        System.out.println("***************************************");
        System.out.println("QuizBoard - Edit QuizBoard");
        System.out.println("***************************************");
        System.out.println("Please enter the QuizBoard ID :");
        String quizBoardID = scanner.nextLine().trim();
        if (quizBoardID.length() == 0) {
            System.out.println("QuizBoard ID cannot be empty!");
            editQuizBoard();
        }
        String userName = "";
        for (Map.Entry<String, Teacher> entry : teacherInfo.entrySet()) {
            userName = entry.getKey();
        }
        try {
            Map<Integer, QuizBoard> quizBoard = quizzyController.editQuizBoard(userName, quizBoardID);
            if (!quizBoard.isEmpty()) {
                editingQuiz(quizBoard);
            } else {
                System.out.println("QuizBoard not found!");
                System.out.println("Returning to QuizBoard Menu...");
                quizMenu();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            quizMenu();
        }
    }

    private void editingQuiz(Map<Integer, QuizBoard> quizBoard) {
        String quizBoardName = "";
        for (Map.Entry<Integer, QuizBoard> entry : quizBoard.entrySet()) {
            quizBoardName = entry.getValue().getQuizBoardName();
        }
        System.out.println("***************************************");
        System.out.println("QuizBoard - " + quizBoardName.toUpperCase());
        System.out.println("***************************************");
        System.out.println("1. Add a Question");
        System.out.println("2. Delete a Question");
        System.out.println("3. view all Questions");
        System.out.println("4. Back to QuizBoard Menu");
        System.out.println("***************************************");
        System.out.println("Please enter your choice :");
        String line = scanner.nextLine().trim();
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case '1' -> {
                    addQuestion(quizBoard);
                }
                case '2' -> {
                    deleteQuestion(quizBoard);
                }
                case '3' -> {
                    viewQuestions(quizBoard);
                }
                case '4' -> {
                    System.out.println("Returning to QuizBoard Menu...");
                    quizMenu();
                }
            }
        } else {
            System.out.println("Please enter a valid option !");
            createAQuiz();
        }
    }

    private void deleteQuestion(Map<Integer, QuizBoard> quizBoard) {
        int quizBoardID = 0;
        String quizBoardName = "";
        int questionNo = 0;
        for (Map.Entry<Integer, QuizBoard> entry : quizBoard.entrySet()) {
            quizBoardID = entry.getValue().getQuizID();
            quizBoardName = entry.getValue().getQuizBoardName();
        }
        System.out.println("***************************************");
        System.out.println("QuizBoard -" + quizBoardName.toUpperCase() + " Delete Question");
        System.out.println("***************************************");
        System.out.println("Please enter the Question No :");
        try {
            questionNo = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Question No cannot be empty!");
            editingQuiz(quizBoard);
        }
        String questionNoToString = String.valueOf(questionNo);
        if (questionNo == 0 | questionNoToString.isEmpty()) {
            throw new IllegalArgumentException("Question No cannot be empty!");
        }
        try {
            quizzyController.deleteQuestion(quizBoardID, questionNo);
            quizzyController.deleteQuizBoardQuestion(quizBoardID);
            System.out.println("Question deleted successfully!");
            saveData();
            editingQuiz(quizBoard);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            editingQuiz(quizBoard);
        }
    }

    private void viewQuestions(Map<Integer, QuizBoard> quizBoard) {
        int quizBoardID = 0;
        String quizBoardName = "";
        for (Map.Entry<Integer, QuizBoard> entry : quizBoard.entrySet()) {
            quizBoardID = entry.getValue().getQuizID();
            quizBoardName = entry.getValue().getQuizBoardName();
        }
        System.out.println("***************************************");
        System.out.println("QuizBoard -" + quizBoardName.toUpperCase() + " view Questions");
        System.out.println("***************************************");
        try {
            Map<Integer, Quiz> localData = quizzyController.viewAllQuestions(quizBoardID);
            if (localData.isEmpty()) {
                throw new IllegalArgumentException("No Questions found!");
            }
            for (Map.Entry<Integer, Quiz> entry : localData.entrySet()) {
                System.out.println("Question No : " + entry.getKey());
                System.out.println("Question : " + entry.getValue().getQuizQuestion());
                System.out.println("Answer : " + entry.getValue().getCorrectAnswer());
                System.out.println("Marks Allocated : " + entry.getValue().getQuizPoints());
                System.out.println("***************************************");
            }
            editingQuiz(quizBoard);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            quizMenu();
        }
    }

    private void addQuestion(Map<Integer, QuizBoard> quizBoard) {
        String quizBoardName = "";
        for (Map.Entry<Integer, QuizBoard> entry : quizBoard.entrySet()) {
            quizBoardName = entry.getValue().getQuizBoardName();
        }
        int quizBoardID = 0;
        for (Map.Entry<Integer, QuizBoard> entry : quizBoard.entrySet()) {
            quizBoardID = entry.getValue().getQuizID();
        }
        System.out.println("***************************************");
        System.out.println("QuizBoard -" + quizBoardName.toUpperCase() + " \n Add Question");
        System.out.println("***************************************");
        System.out.println("Please enter the Question :");
        String question = scanner.nextLine().trim();
        if (question.length() == 0) {
            System.out.println("Question cannot be empty!");
        }
        System.out.println("Please enter the Correct Answer :");
        String correctAnswer = scanner.nextLine().trim();
        if (correctAnswer.length() == 0) {
            System.out.println("Correct Answer cannot be empty!");
        }
        System.out.println("Enter the marks for the question :");
        float marks = Float.parseFloat(scanner.nextLine().trim());
        if (marks == 0) {
            System.out.println("Marks cannot be empty!");
        }
        try {
            quizzyController.addQuestion(quizBoardID, question, correctAnswer, marks);
            quizzyController.updateQuizBoardQuestion(quizBoardID);
            System.out.println("Question added successfully!");
            saveData();
            editingQuiz(quizBoard);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteQuizBoard() {
        System.out.println("***************************************");
        System.out.println("QuizBoard - Delete QuizBoard");
        System.out.println("***************************************");
        System.out.println("Please enter the QuizBoard ID :");
        String quizBoardID = scanner.nextLine().trim();
        if (quizBoardID.length() == 0) {
            System.out.println("QuizBoard ID cannot be empty!");
            deleteQuizBoard();
        }
        try {
            quizzyController.deleteQuizBoard(quizBoardID);
            System.out.println("QuizBoard deleted successfully!");
            saveData();
            createAQuiz();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Returning to QuizBoard Menu...");
            createAQuiz();
        }

    }

    private void viewQuizBoards() {
        System.out.println("***************************************");
        System.out.println("QuizBoard - view QuizBoards");
        System.out.println("***************************************");
        System.out.println("1. view QuizBoards by Course");
        System.out.println("2. view QuizBoards by QuizBoard ID");
        System.out.println("3. view All QuizBoards");
        System.out.println("4. Back to QuizBoard Menu");
        System.out.println("***************************************");
        System.out.println("Please enter your choice :");
        String line = scanner.nextLine().trim();
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case '1' -> {
                    viewQuizBoardsByCourse();
                }
                case '2' -> {
                    viewQuizBoardsByQuizBoardID();
                }
                case '3' -> {
                    viewAllQuizBoards();
                }
                case '4' -> {
                    System.out.println("Returning to QuizBoard Menu...");
                    System.out.println("***************************************");
                    quizMenu();
                }
                default -> {
                    System.out.println("Please enter a valid option !");
                    viewQuizBoards();
                }
            }
        } else {
            System.out.println("Please enter a valid option !");
            quizMenu();
        }
    }

    private void viewQuizBoardsByQuizBoardID() {
        System.out.println("***************************************");
        System.out.println("QuizBoard - view QuizBoards by QuizBoard ID");
        System.out.println("***************************************");
        System.out.println("Please enter the QuizBoard ID :");
        String quizBoardID = scanner.nextLine().trim();
        if (quizBoardID.length() == 0) {
            System.out.println("QuizBoard ID cannot be empty!");
            viewQuizBoardsByQuizBoardID();
        }
        String teacherUserName = "";
        for (Map.Entry<String, Teacher> Entry : teacherInfo.entrySet()) {
            teacherUserName = Entry.getValue().getUsername();
        }
        try {
            Map<Integer, QuizBoard> quizBoard = quizzyController.viewQuizBoardByQuizBoardID(teacherUserName, quizBoardID);
            if (quizBoard.isEmpty()) {
                System.out.println("No QuizBoards found for QuizBoard ID: " + quizBoardID);
                System.out.println("***************************************");
                viewQuizBoards();
            }
            System.out.println("QuizBoard for QuizBoard ID: " + quizBoardID);
            System.out.println("***************************************");
            for (Map.Entry<Integer, QuizBoard> Entry : quizBoard.entrySet()) {
                System.out.println("QuizBoard ID: " + Entry.getKey());
                System.out.println("QuizBoard Name: " + Entry.getValue().getQuizBoardName());
                System.out.println("QuizBoard Created By: " + Entry.getValue().getCreatedByTeacher());
                System.out.println("QuizBoard Created On: " + Entry.getValue().getDateCreated());
                System.out.println("QuizBoard Allocated to Course: " + Entry.getValue().getCourseName());
                System.out.println("No of Quizzes in QuizBoard: " + Entry.getValue().getNumberOfQuestions());
                System.out.println("No of Users Attempted QuizBoard: " + Entry.getValue().getStudentScores().size());
                System.out.println("***************************************");
            }
            System.out.println("Returning to QuizBoard Menu...");
            createAQuiz();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            quizMenu();
        }
    }

    private void viewAllQuizBoards() {
        System.out.println("***************************************");
        System.out.println("QuizBoard - view All QuizBoards");
        System.out.println("***************************************");
        String createdBy = "";
        for (Map.Entry<String, Teacher> Entry : teacherInfo.entrySet()) {
            createdBy = Entry.getKey();
        }
        try {
            Map<Integer, QuizBoard> quizBoards = quizzyController.viewAllQuizBoards(createdBy);
            if (quizBoards.isEmpty()) {
                System.out.println("No QuizBoards Created by " + createdBy);
                System.out.println("***************************************");
                createAQuiz();
            }
            System.out.println("All QuizBoards for Admin: " + createdBy);
            System.out.println("***************************************");
            for (Map.Entry<Integer, QuizBoard> Entry : quizBoards.entrySet()) {
                System.out.println("QuizBoard ID: " + Entry.getKey());
                System.out.println("QuizBoard Name: " + Entry.getValue().getQuizBoardName());
                System.out.println("QuizBoard Created By: " + Entry.getValue().getCreatedByTeacher());
                System.out.println("QuizBoard Created On: " + Entry.getValue().getDateCreated());
                System.out.println("QuizBoard Allocated to Course: " + Entry.getValue().getCourseName());
                System.out.println("No of Quizzes in QuizBoard: " + Entry.getValue().getNumberOfQuestions());
                System.out.println("No of Students Attempted QuizBoard: " + Entry.getValue().getStudentScores().size());
                System.out.println("***************************************");
            }
            createAQuiz();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            quizMenu();
        }
    }

    private void viewQuizBoardsByCourse() {
        System.out.println("***************************************");
        System.out.println("QuizBoard - view QuizBoards by Course");
        System.out.println("***************************************");
        System.out.println("Please enter the Course Name :");
        String courseName = scanner.nextLine().trim();
        String createdBy = "";
        for (Map.Entry<String, Teacher> Entry : teacherInfo.entrySet()) {
            createdBy = Entry.getKey();
        }
        try {
            quizzyController.validateIfTeacherCourse(courseName, createdBy);
            Map<Integer, QuizBoard> quizBoards = quizzyController.viewQuizBoardsByCourse(courseName, createdBy);
            System.out.println("QuizBoards for Course: " + courseName);
            System.out.println("***************************************");
            for (Map.Entry<Integer, QuizBoard> Entry : quizBoards.entrySet()) {
                System.out.println("QuizBoard ID: " + Entry.getKey());
                System.out.println("QuizBoard Name: " + Entry.getValue().getQuizBoardName());
                System.out.println("QuizBoard Created By: " + Entry.getValue().getCreatedByTeacher());
                System.out.println("QuizBoard Created On: " + Entry.getValue().getDateCreated());
                System.out.println("QuizBoard Allocated to Course: " + Entry.getValue().getCourseName());
                System.out.println("No of Quizzes in QuizBoard: " + Entry.getValue().getNumberOfQuestions());
                System.out.println("No of Students Attempted QuizBoard: " + Entry.getValue().getStudentScores().size());
                System.out.println("***************************************");
                quizMenu();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            quizMenu();
        }
    }

    private void createQuizBoard() {
        System.out.println("***************************************");
        System.out.println("QuizBoard - Create a QuizBoard");
        System.out.println("***************************************");
        System.out.println("Please enter the QuizBoard name :");
        String quizBoardName = scanner.nextLine().trim();
        System.out.println("Please enter the Course which the QuizBoard is allocated to:");
        String courseName = scanner.nextLine().trim();
        String createdBy = "";
        for (Map.Entry<String, Teacher> Entry : teacherInfo.entrySet()) {
            createdBy = Entry.getKey();
        }
        Date dateCreated = new Date();
        try {
            quizzyController.validateIfTeacherCourse(courseName, createdBy);
            int allocatedQuizBoardID = quizzyController.createQuizBoard(quizBoardName, courseName, dateCreated, createdBy);
            System.out.println("QuizBoard created successfully with ID: " + allocatedQuizBoardID);
            System.out.println("***************************************");
            saveData();
            quizMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            createQuizBoard();
        }

    }


    private void changeCourseDetails() {
        System.out.println("***************************************");
        System.out.println("*** Change Course Details ***");
        System.out.println("***************************************");
        System.out.println("1. Do you want to add a Course ! ");
        System.out.println("2. Do you want to remove a Course ! ");
        System.out.println("Please select an option: ");
        String line = scanner.nextLine().trim();
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case '1' -> {
                    addCourse();
                }
                case '2' -> {
                    removeCourse();
                }
                default -> {
                    System.out.println("Please select a valid option: ");
                    changeCourseDetails();
                }
            }
        } else {
            System.out.println("Please select a valid option: ");
            teacherMenu();
        }
    }

    private void removeCourse() {
        System.out.println("***************************************");
        System.out.println("*** Remove Course ***");
        System.out.println("***************************************");
        System.out.println("Please enter the course name: ");
        String courseName = scanner.nextLine().trim().toLowerCase();
        String userName = "";
        for (Map.Entry<String, Teacher> entry : teacherInfo.entrySet()) {
            userName = entry.getKey();
        }
        try {
            quizzyController.removeCourse(courseName, userName);
            System.out.println("Course removed successfully!");
            saveData();
            teacherMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again!");
            teacherMenu();
        }
    }

    private void addCourse() {
        System.out.println("***************************************");
        System.out.println("*** Add Course ***");
        System.out.println("***************************************");
        System.out.println("Please enter the course name: ");
        String courseName = scanner.nextLine().trim().toLowerCase();
        String userName = "";
        for (Map.Entry<String, Teacher> entry : teacherInfo.entrySet()) {
            userName = entry.getKey();
        }
        try {
            quizzyController.addCourse(courseName, userName);
            System.out.println("Course added successfully!");
            saveData();
            teacherMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again!");
            teacherMenu();
        }

    }

    private void viewStudents() {
        System.out.println("***************************************");
        System.out.println("*** view Students By Course ***");
        System.out.println("***************************************");
        System.out.println("Please enter the course name: ");
        String courseName = scanner.nextLine().trim();
        String userName = "";
        for (Map.Entry<String, Teacher> entry : teacherInfo.entrySet()) {
            userName = entry.getKey();
        }
        try {
            quizzyController.validateIfTeacherCourse(courseName, userName);

            ArrayList<String> students = quizzyController.getStudentsByCourse(courseName);
            System.out.println("The students enrolled in " + courseName + " are: ");
            int i = 0;
            for (String student : students) {
                System.out.println(i + 1 + ". " + student);
                ++i;
            }
            if (students.isEmpty()) {
                System.out.println("No students currently are enrolled in this course!");
            }
            System.out.println("***************************************");
            teacherMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            teacherMenu();
        }

    }

    private void viewTeacherCourses() {
        System.out.println("***************************************");
        System.out.println("*** Your Courses ***");
        System.out.println("***************************************");
        ArrayList<String> yourCourses = new ArrayList<>();
        for (Map.Entry<String, Teacher> entry : teacherInfo.entrySet()) {
            yourCourses = entry.getValue().getCoursesEnrolled();
        }
        int i = 0;
        System.out.println("The courses you are teaching are:");
        for (String course : yourCourses) {
            System.out.println(i + 1 + ". " + course.toUpperCase());
            ++i;

        }
        teacherMenu();
    }

    private void changeTeacherPassword() {
        System.out.println("***************************************");
        System.out.println("*** Changing Password ***");
        System.out.println("***************************************");
        System.out.println("Please enter your current password: ");
        String currentPassword = scanner.nextLine().trim();
        String userName = "";
        for (Map.Entry<String, Teacher> entry : teacherInfo.entrySet()) {
            userName = entry.getKey();
        }
        try {
            quizzyController.validatePassword(userName, currentPassword);
            System.out.println("Please enter your new password: ");
            String newPassword = scanner.nextLine().trim();
            quizzyController.changePassword(userName, newPassword);
            System.out.println("Password changed successfully!");
            saveData();
            teacherMenu();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            teacherMenu();
        }
    }

    private void viewTeacherDetails() {
        System.out.println("***************************************");
        System.out.println("*** My Details ***");
        System.out.println("***************************************");
        String userName = "";
        String fullName = "";
        String email = "";
        Date dateCreated = null;
        String accountType = "";
        for (Map.Entry<String, Teacher> entry : teacherInfo.entrySet()) {
            userName = entry.getKey();
            fullName = entry.getValue().getFullName();
            email = entry.getValue().getEmail();
            accountType = entry.getValue().getAccountType().toString();
            dateCreated = entry.getValue().getDateCreated();
        }
        System.out.println("Username: " + userName);
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Account Type: " + accountType);
        System.out.println("Date Created:" + dateCreated);
        System.out.println("***************************************");
        teacherMenu();
    }

    private void deleteTeacherAccount() {
        String userName = "";
        for (Map.Entry<String, Teacher> entry : teacherInfo.entrySet()) {
            userName = entry.getKey();
        }
        System.out.println("Deleting Account...");
        System.out.println("Are you sure you want to delete your account? (Y/N)");
        String line = scanner.nextLine().trim().toLowerCase();
        if (line.length() == 1) {
            switch (line.charAt(0)) {
                case 'y' -> {
                    quizzyController.deleteAccount(userName);
                    System.out.println("Account Deleted Successfully!");
                    loginStatus = false;
                    saveData();
                    start();
                }
                case 'n' -> {
                    teacherMenu();
                }
                default -> {
                    System.out.println("Please select a valid option: ");
                    deleteTeacherAccount();
                }
            }
        } else {
            System.out.println("Please select a valid option: ");
            deleteTeacherAccount();
        }
    }

    private void saveData() {
    
        try {
            quizzyController.saveData();

            if (!loginStatus) {
                start();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void register() {
        String sha256;
        AccountType accountType = null;
        try {
            System.out.println("***************************************");
            System.out.println("*** Registration Page ***");
            System.out.println("***************************************");
            System.out.println("Please enter your Full Name: ");
            String fullName = scanner.nextLine().trim();
            System.out.println("Please enter your Email - ID");
            String email = scanner.nextLine().trim();
            quizzyController.validateEmail(email);
            System.out.println("Please enter your Username: ");
            String username = scanner.nextLine().trim();
            quizzyController.validateUsername(username);
            System.out.println("Please enter your Password: ");
            String password = scanner.nextLine().trim();
            sha256 = quizzyController.hashPassword(password);
            System.out.println("1.Are you a Student?");
            System.out.println("2.Are you a Teacher?");
            System.out.println("Please select an option: ");
            String choice = scanner.nextLine().trim();
            if (choice.length() == 1) {
                switch (choice.charAt(0)) {
                    case '1' -> {
                        accountType = AccountType.STUDENT;
                    }
                    case '2' -> {
                        accountType = AccountType.TEACHER;
                    }
                    default -> {
                        System.out.println("Please select a valid option: ");
                        register();
                    }
                }
            } else {
                System.out.println("Please select a valid option: ");
                register();
            }
            System.out.println("Enter the Courses you are enrolled in: (If Multiple, separate by comma)");
            String courses = scanner.nextLine().trim().toLowerCase();
            if (courses.isEmpty() || courses.isBlank()) {
                throw new IllegalArgumentException("Courses cannot be empty!");
            }
            // Add courses to a list
            Collections.addAll(coursesEnrolled, courses.split(","));

            // Courses should not be repeated in the arrayList
            for (int i = 0; i < coursesEnrolled.size(); i++) {
                for (int j = i + 1; j < coursesEnrolled.size(); j++) {
                    if (coursesEnrolled.get(i).equals(coursesEnrolled.get(j))) {
                        coursesEnrolled.remove(j);
                    }
                }
            }
            Date dateCreated = new Date();
            if (accountType == AccountType.STUDENT) {
                quizzyController.studentRegister(fullName, email, username, sha256, accountType, coursesEnrolled, dateCreated);
            } else if (accountType == AccountType.TEACHER) {
                quizzyController.teacherRegister(fullName, email, username, sha256, accountType, coursesEnrolled, dateCreated);
            }
            System.out.println("Registration Successful!");
            System.out.println("Please Login to continue...");
            saveData();
            start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            start();
        }
    }


}
