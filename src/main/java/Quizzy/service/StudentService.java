package main.java.Quizzy.service;

import main.java.Quizzy.model.AccountType;
import main.java.Quizzy.model.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a student service.
 * A student service contains the following information:
 * students: A map of students
 */
public class StudentService implements Serializable {

    private Map<String, Student> students = new HashMap<>();

    /**
     * This method registers a student.
     * @param fullName The full name of the student
     * @param email The email of the student
     * @param username The username of the student
     * @param sha256 The password of the student
     * @param accountType The account type of the student
     * @param coursesEnrolled The courses the student is enrolled in
     * @param dateCreated The date the student account was created
     */
    public void register(String fullName, String email, String username, String sha256, AccountType accountType, ArrayList<String> coursesEnrolled, Date dateCreated) {
        // Check for empty string in fullName
        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        // Check for duplicate username
        if (students.containsKey(username.toLowerCase())) {
            throw new IllegalArgumentException("Username already exists");
        }
        // Create a new HashMap for quizScores for each student
        Map<Integer, Float> quizScores = new HashMap<>();
        Student student = new Student(fullName.toLowerCase(), email.toLowerCase(), sha256, username.toLowerCase(), accountType, coursesEnrolled, dateCreated, quizScores);
        students.put(username.toLowerCase(), student);

    }

    /**
     * This method validates the login of a student.
     * @param userName The username of the student
     * @param password The password of the student
     * @return A map of the student's username and student object
     */
    public Map<String, Student> validateLogin(String userName, String password) {
        if (students.isEmpty()) {
            throw new IllegalArgumentException("No users found");

        }
        Map<String, Student> studentInfo = new HashMap<>();
        // CHECK IF the USER-NAME OR PASSWORD IS EMPTY
        if (userName.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username or password cannot be empty");
        }
        //Check if userName and password match
        if (students.containsKey(userName.toLowerCase())) {
            if (students.get(userName.toLowerCase()).getPassword().equals(password)) {
                studentInfo.put(userName.toLowerCase(), students.get(userName.toLowerCase()));
            }
        } else {
            throw new IllegalArgumentException("Username or password is incorrect");
        }
        return studentInfo;
    }

    /**
     * This method is used for saving student data.
     * @throws IOException This method saves the student data.
     */
    public void saveData() throws IOException {
        FileOutputStream f = new FileOutputStream("src/resources/studentData.ser");
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(students);
        o.close();
        f.close();
    }

    /**
     * This method is used for loading student data.
     * @throws IOException throws an exception if the file is not found
     * @throws ClassNotFoundException throws an exception if the class is not found
     */
    public void loadData() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/resources/studentData.ser");
        ObjectInputStream oi = new ObjectInputStream(fi);
        students = (Map<String, Student>) oi.readObject();
        oi.close();
        fi.close();
    }

    /**
     * This method is used for hashing the password.
     * @param password The password of the student
     * @return The hashed password
     */
    public String hashPassword(String password) {
        //Check for empty string
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        String hashedPassword = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            hashedPassword = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm");
        }
        return hashedPassword;
    }

    /**
     * This method validates the password of a student when changing the password.
     * @param userName The username of the student
     * @param currentPassword The current password of the student
     */
    public void validatePassword(String userName, String currentPassword) {
        if(students.isEmpty()){
            throw new IllegalArgumentException("No users found");
        }
        if (currentPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        String hashedPassword = hashPassword(currentPassword);
        if (!students.get(userName.toLowerCase()).getPassword().equals(hashedPassword)) {
            throw new IllegalArgumentException("Password is incorrect");
        }
    }

    /**
     * This method changes the password of a student.
     * @param userName The username of the student
     * @param newPassword The new password of the student
     */
    public void changePassword(String userName, String newPassword) {
        String hashedPassword = hashPassword(newPassword);
        if (newPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        // Check if old password and new password are the same
        if (students.get(userName.toLowerCase()).getPassword().equals(hashedPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        } else {
            students.get(userName.toLowerCase()).setPassword(hashedPassword);
        }
    }

    /**
     * This method deletes a student account.
     * @param userName The username of the student
     */
    public void deleteAccount(String userName) {
        if(students.isEmpty()){
            throw new IllegalArgumentException("No users found");
        }
        if (students.containsKey(userName.toLowerCase())) {
            students.remove(userName.toLowerCase());
        } else {
            throw new IllegalArgumentException("Username does not exist");
        }

    }

    /**
     * This method gets the student object of a student.
     * @param courseName The name of the course
     * @return A list of students enrolled in the course
     */
    public ArrayList<String> getStudentsByCourse(String courseName) {
        ArrayList<String> students = new ArrayList<>();
        for (Map.Entry<String, Student> entry : this.students.entrySet()) {
            if (entry.getValue().getCoursesEnrolled().contains(courseName.toLowerCase())) {
                students.add(entry.getKey());
            }
        }
        return students;
    }

    /**
     * This method is used for updating the score of a student after a quiz.
     * @param studentName The name of the student
     * @param quizBoardID The ID of the quiz board
     * @param studentScore The score of the student
     */
    public void updateStudentScore(String studentName, int quizBoardID, float studentScore) {
        if (students.containsKey(studentName.toLowerCase())) {
            students.get(studentName.toLowerCase()).getQuizScores().put(quizBoardID, studentScore);
        } else {
            throw new IllegalArgumentException("Student does not exist");
        }
    }

    /**
     * This method is used for getting the score of a student by quiz board ID.
     * @param quizBoardID The ID of the quiz board
     * @return A map of the student's username and score
     */
    public Map<Integer, Float> getStudentScore(int quizBoardID) {
        Map<Integer, Float> studentScores = new HashMap<>();
        for (Map.Entry<String, Student> entry : this.students.entrySet()) {
            if (entry.getValue().getQuizScores().containsKey(quizBoardID)) {
                studentScores.put(quizBoardID, entry.getValue().getQuizScores().get(quizBoardID));
            }
        }
        return studentScores;
    }
}
