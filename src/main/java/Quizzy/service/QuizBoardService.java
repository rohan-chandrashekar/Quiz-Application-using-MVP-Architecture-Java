package main.java.Quizzy.service;

import main.java.Quizzy.model.QuizBoard;

import java.io.*;
import java.util.*;

/**
 * QuizBoardService class.
 * This class is responsible for storing the quiz board data
 * and providing the necessary methods to access and modify the data.
 */
public class QuizBoardService implements Serializable {

    // This map stores the quiz board data.

    private Map<Integer, QuizBoard> quizBoardMap = new HashMap<>();

    // This list stores the quiz IDs of the quiz boards.

    private ArrayList<Integer> quizIDS = new ArrayList<>();


    /**
     * This method saves the quiz board data to a file.
     * @throws IOException if an I/O error occurs
     */
    public void saveData() throws IOException {
        FileOutputStream f = new FileOutputStream("src/resources/QuizBoardData.ser");
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(quizBoardMap);
        o.close();
        f.close();
    }

    /**
     * This method loads the quiz board data from a file.
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public void loadData() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/resources/QuizBoardData.ser");
        ObjectInputStream oi = new ObjectInputStream(fi);
        quizBoardMap = (Map<Integer, QuizBoard>) oi.readObject();
        quizIDS = new ArrayList<>(quizBoardMap.keySet());
        oi.close();
        fi.close();
    }


    /**
     * This method creates a quiz board.
     * @param quizBoardName The name of the quiz board.
     * @param courseName The name, of course.
     * @param dateCreated The date the quiz board was created.
     * @param createdBy The teacher who created the quiz board.
     * @return The ID of the quiz board.
     */
    public int createQuizBoard(String quizBoardName, String courseName, Date dateCreated, String createdBy) {
        // Check for empty string and null
        if (quizBoardName == null || quizBoardName.isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        // Generate a random quiz ID and checking if it is unique or not.
        Random random = new Random();
        int quizID = random.nextInt(1000000);
        // CHECK IF QUIZ ID IS UNIQUE
        while (quizIDS.contains(quizID)) {
            quizID = random.nextInt(1000000);
        }
        quizIDS.add(quizID);
        // For the initial creation of the quiz board,the values of some parameters
        // that are passed in the constructor are zero or null.
        Map<String, Float> studentScores = new HashMap<>(); // The student scores are initially zero.
        float totalScore = 0; // The total score of the quiz board is zero initially.
        int numberOfQuestions = 0; // The number of questions in the quiz board is zero initially.
        Date modifiedDate = dateCreated; // The date the quiz board was created is the date it was modified initially.
        String modifiedBy = createdBy; // The teacher who created the quiz board is the one who modified it initially.
        QuizBoard quizBoard = new QuizBoard(quizID, quizBoardName.toLowerCase(), courseName.toLowerCase(), dateCreated, createdBy.toLowerCase(), modifiedDate, modifiedBy.toLowerCase(), totalScore, studentScores, numberOfQuestions);
        quizBoardMap.put(quizID, quizBoard);

        return quizID;
    }


    /**
     * This method views the quiz board by course.
     * @param courseName The name, of course.
     * @param createdBy The teacher who created the quiz board.
     * @return A map of quiz boards that belong to the course.
     */
    public Map<Integer, QuizBoard> viewQuizBoardsByCourse(String courseName, String createdBy) {
        // Check for empty string and null
        if (courseName == null || courseName.isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        Map<Integer, QuizBoard> quizBoardMapByCourse = new HashMap<>();
        for (Map.Entry<Integer, QuizBoard> entry : quizBoardMap.entrySet()) {
            if (entry.getValue().getCourseName().equals(courseName.toLowerCase()) && entry.getValue().getCreatedByTeacher().equals(createdBy.toLowerCase())) {
                quizBoardMapByCourse.put(entry.getKey(), entry.getValue());
            }
        }
        return quizBoardMapByCourse;
    }

    /**
     * This method views all the quiz boards created by the teacher.
     * @param createdBy The teacher who created the quiz board.
     * @return A map of all the quiz boards created by the teacher.
     */
    public Map<Integer, QuizBoard> viewAllQuizBoards(String createdBy) {
        Map<Integer, QuizBoard> quizBoardMapByTeacher = new HashMap<>();
        for (Map.Entry<Integer, QuizBoard> entry : quizBoardMap.entrySet()) {
            if (entry.getValue().getCreatedByTeacher().equals(createdBy.toLowerCase())) {
                quizBoardMapByTeacher.put(entry.getKey(), entry.getValue());
            }
        }
        return quizBoardMapByTeacher;
    }

    /**
     * This method deletes a quiz board.
     * @param quizBoardID The ID of the quiz board.
     */
    public void deleteQuizBoard(String quizBoardID) {
        if (quizBoardMap.isEmpty() && quizIDS.isEmpty()) {
            throw new IllegalArgumentException("No Quiz Boards exist");
        }
        if (quizBoardMap.containsKey(Integer.parseInt(quizBoardID))) {
            quizBoardMap.remove(Integer.parseInt(quizBoardID));
            for (Integer number : quizIDS) {
                if (number == Integer.parseInt(quizBoardID)) {
                    quizIDS.remove(number);
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
    }

    /**
     * This method is used for viewing a quiz board by its ID.
     * @param teacherUserName The teacher who created the quiz board.
     * @param quizBoardID The ID of the quiz board.
     * @return A map of the quiz board.
     */
    public Map<Integer, QuizBoard> viewQuizBoardByQuizBoardID(String teacherUserName, String quizBoardID) {
        Map<Integer, QuizBoard> quizBoardMapByQuizBoardID = new HashMap<>();
        if (quizBoardMap.containsKey(Integer.parseInt(quizBoardID))) {
            if (quizBoardMap.get(Integer.parseInt(quizBoardID)).getCreatedByTeacher().equals(teacherUserName.toLowerCase())) {
                quizBoardMapByQuizBoardID.put(Integer.parseInt(quizBoardID), quizBoardMap.get(Integer.parseInt(quizBoardID)));
            } else {
                throw new IllegalArgumentException("Quiz Board does not exist");
            }
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
        return quizBoardMapByQuizBoardID;
    }

    /**
     * This method is used for editing (Add/delete questions) to a quiz board by its ID.
     * @param userName The teacher who created the quiz board.
     * @param quizBoardID The ID of the quiz board.
     * @return A map of the quiz board.
     */
    public Map<Integer, QuizBoard> editQuizBoard(String userName, String quizBoardID) {
        Map<Integer, QuizBoard> quizBoardMapByQuizBoardID = new HashMap<>();
        if (quizBoardMap.containsKey(Integer.parseInt(quizBoardID))) {
            if (quizBoardMap.get(Integer.parseInt(quizBoardID)).getCreatedByTeacher().equals(userName.toLowerCase())) {
                quizBoardMapByQuizBoardID.put(Integer.parseInt(quizBoardID), quizBoardMap.get(Integer.parseInt(quizBoardID)));
            } else {
                throw new IllegalArgumentException("Quiz Board does not exist");
            }
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
        return quizBoardMapByQuizBoardID;
    }


    /**
     * This method is used for updating the number of questions in a quiz board.
     * @param quizBoardID The ID of the quiz board.
     */
    public void updateQuizBoardQuestion(int quizBoardID) {
        if (quizBoardMap.containsKey(quizBoardID)) {
            quizBoardMap.get(quizBoardID).setNumberOfQuestions(quizBoardMap.get(quizBoardID).getNumberOfQuestions() + 1);
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
    }

    /**
     * This method is used for deleting a question in a quiz board.
     * @param quizBoardID The ID of the quiz board.
     */
    public void deleteQuizBoardQuestion(int quizBoardID) {
        if (quizBoardMap.containsKey(quizBoardID)) {
            quizBoardMap.get(quizBoardID).setNumberOfQuestions(quizBoardMap.get(quizBoardID).getNumberOfQuestions() - 1);
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
    }

    /**
     * This method is used for getting the course name of a quiz board.
     * @param quizBoardID The ID of the quiz board.
     * @return The course name of the quiz board.
     */
    public String getQuizBoardCourse(int quizBoardID) {
        if (quizBoardMap.containsKey(quizBoardID)) {
            return quizBoardMap.get(quizBoardID).getCourseName();
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
    }

    /**
     * This method is used for getting the name of a quiz board.
     * @param quizBoardID The ID of the quiz board.
     * @return The name of the quiz board.
     */
    public String getQuizBoardName(int quizBoardID) {
        if (quizBoardMap.containsKey(quizBoardID)) {
            return quizBoardMap.get(quizBoardID).getQuizBoardName();
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
    }

    /**
     * This method is used for updating the student score to the quiz board.
     * @param studentName The name of the student.
     * @param quizBoardID The ID of the quiz board.
     * @param studentScore The score of the student.
     */
    public void updateStudentScoreBoard(String studentName, int quizBoardID, float studentScore) {
        if (quizBoardMap.containsKey(quizBoardID)) {
            quizBoardMap.get(quizBoardID).getStudentScores().put(studentName.toLowerCase(), studentScore);
            quizBoardMap.get(quizBoardID).setTotalScore(quizBoardMap.get(quizBoardID).getTotalScore() + studentScore);
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }

    }

    /**
     * This method is used for checking if a quiz has been taken by a student.
     * @param userName The name of the student.
     * @param quizBoardID   Quiz Board ID.
     */
    public void checkIfQuizTaken(String userName, int quizBoardID) {
        if (quizBoardMap.containsKey(quizBoardID)) {
            if (quizBoardMap.get(quizBoardID).getStudentScores().containsKey(userName.toLowerCase())) {
                throw new IllegalArgumentException("Quiz already taken");
            }
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
    }

    /**
     * This method is used for getting the total score of a quiz board.
     * @param quizBoardID  The ID of the quiz board.
     * @return The students' score who has taken the quiz.
     */
    public Map<String, Float> viewTeacherQuizResults(int quizBoardID) {
        if (quizBoardMap.containsKey(quizBoardID)) {
            return quizBoardMap.get(quizBoardID).getStudentScores();
        } else {
            throw new IllegalArgumentException("Quiz Board does not exist");
        }
    }
}
