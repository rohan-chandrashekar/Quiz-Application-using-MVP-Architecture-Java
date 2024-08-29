package main.java.Quizzy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is used to store teachers' information
 * It contains the following fields:
 * - fullName: The full name of the teacher
 * - email: The email of the teacher
 * - password: The password of the teacher
 * - username: The username of the teacher
 * - accountType: The account type of the teacher
 * - coursesEnrolled: The courses the teacher is enrolled in
 * - dateCreated: The date the teacher account was created
 */
public class Teacher implements Serializable {
    private String fullName;

    private String email;

    private String password;

    private String username;

    private AccountType accountType;

    private ArrayList<String> coursesEnrolled;

    private Date dateCreated;

    public Teacher(String fullName, String email, String username, String password, AccountType accountType, ArrayList<String> coursesEnrolled, Date dateCreated) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.accountType = accountType;
        this.coursesEnrolled = coursesEnrolled;
        this.dateCreated = dateCreated;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public ArrayList<String> getCoursesEnrolled() {
        return coursesEnrolled;
    }

    public void setCoursesEnrolled(ArrayList<String> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
