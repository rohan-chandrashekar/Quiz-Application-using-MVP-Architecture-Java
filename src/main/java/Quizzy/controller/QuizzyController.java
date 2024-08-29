package main.java.Quizzy.controller;

import main.java.Quizzy.model.*;
import main.java.Quizzy.service.QuizBoardService;
import main.java.Quizzy.service.QuizService;
import main.java.Quizzy.service.StudentService;
import main.java.Quizzy.service.TeacherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class QuizzyController {

    TeacherService teacherService = new TeacherService();

    QuizBoardService quizBoardService = new QuizBoardService();

    QuizService quizService = new QuizService();

    StudentService studentService = new StudentService();

    public String hashPassword(String password) {
        return teacherService.hashPassword(password);
    }

    public void validateEmail(String email) {
        teacherService.validateEmail(email);
    }

    public void validateUsername(String username) {
        teacherService.validateUsername(username);
    }

    public void teacherRegister(String fullName, String email, String username, String sha256, AccountType accountType, ArrayList<String> coursesEnrolled, Date dateCreated) {
        teacherService.register(fullName, email, username, sha256, accountType, coursesEnrolled, dateCreated);
    }

    public void saveData() throws IOException {
        teacherService.saveData();
        quizBoardService.saveData();
        quizService.saveData();
        studentService.saveData();
    }

    public void loadData() throws IOException, ClassNotFoundException {
        teacherService.loadData();
        quizBoardService.loadData();
        quizService.loadData();
        studentService.loadData();
    }

    public Map<String, Teacher> validateTeacherLogin(String userName, String password) {
        return teacherService.validateLogin(userName, password);
    }

    public void deleteAccount(String userName) {
        teacherService.deleteAccount(userName);
    }

    public void validatePassword(String userName, String currentPassword) {
        teacherService.validatePassword(userName, currentPassword);
    }

    public void changePassword(String userName, String newPassword) {
        teacherService.changePassword(userName, newPassword);
    }

    public ArrayList<String> getStudentsByCourse(String courseName) {
        return studentService.getStudentsByCourse(courseName);
    }

    public void validateIfTeacherCourse(String courseName, String userName) {
        teacherService.validateIfTeacherCourse(courseName, userName);
    }

    public void addCourse(String courseName, String userName) {
        teacherService.addCourse(courseName, userName);
    }

    public void removeCourse(String courseName, String userName) {
        teacherService.removeCourse(courseName, userName);
    }

    public int createQuizBoard(String quizBoardName, String courseName, Date dateCreated, String createdBy) {
        return quizBoardService.createQuizBoard(quizBoardName, courseName, dateCreated, createdBy);
    }

    public Map<Integer, QuizBoard> viewQuizBoardsByCourse(String courseName, String createdBy) {
        return quizBoardService.viewQuizBoardsByCourse(courseName, createdBy);
    }

    public Map<Integer, QuizBoard> viewAllQuizBoards(String createdBy) {
        return quizBoardService.viewAllQuizBoards(createdBy);
    }

    public void deleteQuizBoard(String quizBoardID) {
        quizBoardService.deleteQuizBoard(quizBoardID);
    }

    public Map<Integer, QuizBoard> viewQuizBoardByQuizBoardID(String teacherUserName, String quizBoardID) {
        return quizBoardService.viewQuizBoardByQuizBoardID(teacherUserName, quizBoardID);
    }


    public Map<Integer, QuizBoard> editQuizBoard(String userName, String quizBoardID) {
        return quizBoardService.editQuizBoard(userName, quizBoardID);
    }

    public void addQuestion(int quizBoardID, String question, String correctAnswer, float marks) {
        quizService.addQuestion(quizBoardID, question, correctAnswer, marks);
    }

    public Map<Integer, Quiz> viewAllQuestions(int quizBoardID) {
        return quizService.viewAllQuestions(quizBoardID);
    }

    public void updateQuizBoardQuestion(int quizBoardID) {
        quizBoardService.updateQuizBoardQuestion(quizBoardID);
    }

    public void deleteQuestion(int quizBoardID, int questionNo) {
        quizService.deleteQuestion(quizBoardID, questionNo);
    }

    public void deleteQuizBoardQuestion(int quizBoardID) {
        quizBoardService.deleteQuizBoardQuestion(quizBoardID);
    }

    public void studentRegister(String fullName, String email, String username, String sha256, AccountType accountType, ArrayList<String> coursesEnrolled, Date dateCreated) {
        studentService.register(fullName, email, username, sha256, accountType, coursesEnrolled, dateCreated);
    }

    public Map<String, Student> validateStudentLogin(String userName, String hashedPassword) {
        return studentService.validateLogin(userName, hashedPassword);
    }

    public void validateStudentPassword(String userName, String currentPassword) {
        studentService.validatePassword(userName, currentPassword);
    }

    public void changeStudentPassword(String userName, String newPassword) {
        studentService.changePassword(userName, newPassword);
    }

    public void deleteStudentAccount(String userName) {
        studentService.deleteAccount(userName);
    }

    public Map<Integer, Quiz> takeQuiz(int quizBoardID) {
        return quizService.takeQuiz(quizBoardID);
    }

    public String getQuizBoardCourse(int quizBoardID) {
        return quizBoardService.getQuizBoardCourse(quizBoardID);
    }

    public String getQuizBoardName(int quizBoardID) {
        return quizBoardService.getQuizBoardName(quizBoardID);
    }

    public void updateStudentScore(String studentName, int quizBoardID, float studentScore) {
        studentService.updateStudentScore(studentName, quizBoardID, studentScore);
    }

    public Map<Integer, Float> getQuizResults(int quizBoardID) {
        return studentService.getStudentScore(quizBoardID);
    }

    public float getQuizTotalScore(int quizBoardID) {
        return quizService.getQuizTotalScore(quizBoardID);
    }

    public void updateStudentScoreBoard(String studentName, int quizBoard, float studentScore) {
        quizBoardService.updateStudentScoreBoard(studentName, quizBoard, studentScore);
    }

    public void checkIfQuizTaken(String userName, int quizBoardID) {
        quizBoardService.checkIfQuizTaken(userName, quizBoardID);
    }

    public Map<String, Float> viewTeacherQuizResults(int quizBoardID) {
        return quizBoardService.viewTeacherQuizResults(quizBoardID);
    }
}
