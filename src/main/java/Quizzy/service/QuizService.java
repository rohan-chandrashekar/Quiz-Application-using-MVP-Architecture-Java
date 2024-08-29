package main.java.Quizzy.service;

import main.java.Quizzy.model.Quiz;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for storing the questions and answers for a quiz
 * A quizService contains the following information:
 * - quizMap: A map that stores the quiz objects
 * - quizIDS: An array list that stores the IDs of the quizzes
 * - questionNumber: The number of the question
 */
public class QuizService implements Serializable {

    private Map<Integer, Quiz> quizMap = new HashMap<>();

    private ArrayList<Integer> quizIDS = new ArrayList<>();

    /**
     * This method adds a question to the quiz board
     * @param quizBoardID The ID of the quiz board that the quiz belongs to
     * @param question The question of the quiz
     * @param correctAnswer The correct answer of the quiz
     * @param marks The number of marks the quiz is worth
     */
    public void addQuestion(int quizBoardID, String question, String correctAnswer, float marks) {
        // Initialise the question number to 1
        int questionNumber = 1;
        while (quizIDS.contains(questionNumber)) {
            questionNumber += 1;
        }
        quizIDS.add(questionNumber);
        Quiz quiz = new Quiz(quizBoardID, questionNumber, question, correctAnswer, marks);
        quizMap.put(questionNumber, quiz);
    }


    /**
     * This method deletes a question in the quiz board
     * @param quizBoardID The ID of the quiz board that the quiz belongs to
     * @param quizID The ID of the quiz
     */
    public void deleteQuestion(int quizBoardID, int quizID) {
        if (quizID == 0) {
            throw new IllegalArgumentException("Quiz ID cannot be 0");
        } else if (!quizIDS.contains(quizID)) {
            throw new IllegalArgumentException("Quiz ID does not exist");
        }
        if (quizMap.get(quizID).getQuizBoardID() != quizBoardID) {
            throw new IllegalArgumentException("Quiz ID does not exist");
        }
        quizMap.remove(quizID);
        // Remove the question number from the list of question numbers in the array list
        quizIDS.remove((Integer) quizID);

    }


    /**
     * This method views all questions in the quiz board
     * @param quizBoardID The ID of the quiz board that the quiz belongs to
     * @return A map that contains the questions and answers of the quiz board
     */
    public Map<Integer, Quiz> viewAllQuestions(int quizBoardID) {
        Map<Integer, Quiz> localData = new HashMap<>();
        // Check for questions with the same quizBoardID
        for (Map.Entry<Integer, Quiz> entry : quizMap.entrySet()) {
            if (entry.getValue().getQuizBoardID() == quizBoardID) {
                localData.put(entry.getKey(), entry.getValue());
            }
        }

        return localData;
    }

    /**
     * This method saves the data of the quiz board.
     * @throws IOException If the file is not found
     */
    public void saveData() throws IOException {
        FileOutputStream f = new FileOutputStream("src/resources/QuestionData.ser");
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(quizMap);
        o.close();
        f.close();
    }

    /**
     * This method loads the data of the quiz board.
     * @throws IOException If the file is not found
     * @throws ClassNotFoundException If the class is not found
     */
    public void loadData() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/resources/QuestionData.ser");
        ObjectInputStream oi = new ObjectInputStream(fi);
        quizMap = (Map<Integer, Quiz>) oi.readObject();
        quizIDS = new ArrayList<>(quizMap.keySet());
        oi.close();
        fi.close();
    }


    /**
     * This method is called when the student takes the quiz and returns the questions and answers to the view Layer.
     * @param quizBoardID The ID of the quiz board that the quiz belongs to
     * @return A map that contains the questions and answers of the quiz board
     */
    public Map<Integer, Quiz> takeQuiz(int quizBoardID) {
        Map<Integer, Quiz> localData = new HashMap<>();
        // Check for questions with the same quizBoardID
        for (Map.Entry<Integer, Quiz> entry : quizMap.entrySet()) {
            if (entry.getValue().getQuizBoardID() == quizBoardID) {
                localData.put(entry.getKey(), entry.getValue());
            }
        }

        return localData;
    }

    /**
     * This method is called when the student submits the quiz and returns the total score of the quiz board.
     * @param quizBoardID The ID of the quiz board that the quiz belongs to
     * @return The total score of the quiz board
     */
    public float getQuizTotalScore(int quizBoardID) {
        float totalScore = 0;
        // Check for questions with the same quizBoardID
        for (Map.Entry<Integer, Quiz> entry : quizMap.entrySet()) {
            if (entry.getValue().getQuizBoardID() == quizBoardID) {
                totalScore += entry.getValue().getQuizPoints();
            }
        }

        return totalScore;
    }
}
