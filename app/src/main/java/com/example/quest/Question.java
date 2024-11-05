// Question.java
package com.example.quest;

public class Question {
    private String questionText;
    private int[] correctIndexes; // Массив с индексами правильных ячеек

    public Question(String questionText, int[] correctIndexes) {
        this.questionText = questionText;
        this.correctIndexes = correctIndexes;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int[] getCorrectIndexes() {
        return correctIndexes;
    }
}
