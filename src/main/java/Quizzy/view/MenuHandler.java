package main.java.Quizzy.view;

import java.util.Scanner;

/**
 * Handles all menu display and navigation logic for the CLI.
 */
public class MenuHandler {
    private final Scanner scanner;

    public MenuHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showTeacherMenu() {
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

    public void showIntroMenu() {
        System.out.println("***************************************");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Please select an option: ");
        System.out.println("***************************************");
    }

    public void showStudentMenu() {
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

    // Add more menu display methods as needed
} 