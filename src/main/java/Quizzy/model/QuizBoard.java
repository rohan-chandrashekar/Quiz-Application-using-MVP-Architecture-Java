package main.java.Quizzy.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * This class represents a Quiz board.
 * A quiz board is a collection of quizzes.
 * A quiz board contains the following information:
 * - quizID: The ID of the quiz board
 * - quizBoardName: The name of the quiz board
 * - courseName: The name of the course that the quiz board belongs to
 * - dateCreated: The date the quiz board was created
 * - createdByTeacher: The teacher who created the quiz board
 * - modifiedDate: The date the quiz board was last modified
 * - modifiedByTeacher: The teacher who last modified the quiz board
 * - totalScore: The total score of the quiz board
 * - studentScores: The scores of the students who have taken the quiz board
 * - numberOfQuestions: The number of questions in the quiz board
 */
public class QuizBoard implements Serializable {

    private int quizID;

    private String quizBoardName;

    private String courseName;

    private Date dateCreated;

    private String createdByTeacher;


    private Date modifiedDate;

    private String modifiedByTeacher;

    private float totalScore;

    private Map<String, Float> studentScores;

    private int numberOfQuestions;


    public QuizBoard(int quizID, String quizBoardName, String courseName, Date dateCreated, String createdByTeacher, Date modifiedDate, String modifiedByTeacher, float totalScore, Map<String, Float> studentScores, int numberOfQuestions) {
        this.quizID = quizID;
        this.quizBoardName = quizBoardName;
        this.courseName = courseName;
        this.dateCreated = dateCreated;
        this.createdByTeacher = createdByTeacher;
        this.modifiedDate = modifiedDate;
        this.modifiedByTeacher = modifiedByTeacher;
        this.totalScore = totalScore;
        this.studentScores = studentScores;
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public String getQuizBoardName() {
        return quizBoardName;
    }

    public void setQuizBoardName(String quizBoardName) {
        this.quizBoardName = quizBoardName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedByTeacher() {
        return createdByTeacher;
    }

    public void setCreatedByTeacher(String createdByTeacher) {
        this.createdByTeacher = createdByTeacher;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedByTeacher() {
        return modifiedByTeacher;
    }

    public void setModifiedByTeacher(String modifiedByTeacher) {
        this.modifiedByTeacher = modifiedByTeacher;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public Map<String, Float> getStudentScores() {
        return studentScores;
    }

    public void setStudentScores(Map<String, Float> studentScores) {
        this.studentScores = studentScores;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
