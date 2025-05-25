package main.java.Quizzy.view;

import main.java.Quizzy.model.Student;
import main.java.Quizzy.model.Teacher;
import main.java.Quizzy.model.Quiz;
import java.util.*;

/**
 * Manages user session state and caches for the CLI.
 */
public class UserSession {
    private Map<String, Teacher> teacherInfo = new HashMap<>();
    private Map<String, Student> studentInfo = new HashMap<>();
    private Map<Integer, Quiz> quizData = new HashMap<>();
    private boolean loginStatus = false;
    private ArrayList<String> coursesEnrolled = new ArrayList<>();

    public Map<String, Teacher> getTeacherInfo() { return teacherInfo; }
    public void setTeacherInfo(Map<String, Teacher> info) { this.teacherInfo = info; }
    public Map<String, Student> getStudentInfo() { return studentInfo; }
    public void setStudentInfo(Map<String, Student> info) { this.studentInfo = info; }
    public Map<Integer, Quiz> getQuizData() { return quizData; }
    public void setQuizData(Map<Integer, Quiz> data) { this.quizData = data; }
    public boolean isLoginStatus() { return loginStatus; }
    public void setLoginStatus(boolean status) { this.loginStatus = status; }
    public ArrayList<String> getCoursesEnrolled() { return coursesEnrolled; }
    public void setCoursesEnrolled(ArrayList<String> courses) { this.coursesEnrolled = courses; }

    public void clearCache() {
        teacherInfo.clear();
        studentInfo.clear();
        quizData.clear();
    }
} 