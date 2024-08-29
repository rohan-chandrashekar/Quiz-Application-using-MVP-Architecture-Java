package main.java.Quizzy.model;

import java.io.Serializable;

/**
 * This class represents a Quiz object.
 * A Quiz object contains the following information:
 * - quizBoardID: The ID of the quiz board that the quiz belongs to
 * - quizID: The ID of the quiz
 * - quizQuestion: The question of the quiz
 * - correctAnswer: The correct answer of the quiz
 * - quizPoints: The number of points the quiz is worth
 */
public class Quiz implements Serializable {
    private int quizBoardID;

    private int quizID;

    private String quizQuestion;

    private String correctAnswer;

    private float quizPoints;

    public Quiz(int quizBoardID, int questionNumber, String question, String correctAnswer, float quizPoints) {
        this.quizBoardID = quizBoardID;
        this.quizID = questionNumber;
        this.quizQuestion = question;
        this.correctAnswer = correctAnswer;
        this.quizPoints = quizPoints;
    }

    public int getQuizBoardID() {
        return quizBoardID;
    }

    public void setQuizBoardID(int quizBoardID) {
        this.quizBoardID = quizBoardID;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public String getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(String quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public float getQuizPoints() {
        return quizPoints;
    }

    public void setQuizPoints(float quizPoints) {
        this.quizPoints = quizPoints;
    }
}
