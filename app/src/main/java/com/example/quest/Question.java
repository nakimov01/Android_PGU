// Question.java
package com.example.quest;

public class Question {
    private String questionText;
    private int[] correctIndexes;

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

    // Статический метод для возврата массива вопросов
    public static Question[] getQuestions() {
        return new Question[]{
                new Question("В первом столбце", new int[]{0, 3, 6}),
                new Question("Во втором столбце", new int[]{1, 4, 7}),
                new Question("В третьем столбце", new int[]{2, 5, 8}),
                new Question("В первой строке", new int[]{0, 1, 2}),
                new Question("Во второй строке", new int[]{3, 4, 5}),
                new Question("В третьей строке", new int[]{6, 7, 8}),
                new Question("По диагонали снизу-вверх", new int[]{6, 4, 2}),
                new Question("По всем диагоналям", new int[]{0, 4, 8, 2, 6}),
                new Question("Среди всех ячеек", new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8})
        };
    }
}
