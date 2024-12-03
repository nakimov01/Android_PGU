package com.example.quest;

import android.content.Context;

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
    public static Question[] getQuestions(Context context) {
        return new Question[]{
                new Question(context.getString(R.string.questions_text1), new int[]{0, 3, 6}),
                new Question(context.getString(R.string.questions_text2), new int[]{1, 4, 7}),
                new Question(context.getString(R.string.questions_text3), new int[]{2, 5, 8}),
                new Question(context.getString(R.string.questions_text4), new int[]{0, 1, 2}),
                new Question(context.getString(R.string.questions_text5), new int[]{3, 4, 5}),
                new Question(context.getString(R.string.questions_text6), new int[]{6, 7, 8}),
                new Question(context.getString(R.string.questions_text7), new int[]{6, 4, 2}),
                new Question(context.getString(R.string.questions_text8), new int[]{0, 4, 8, 2, 6}),
                new Question(context.getString(R.string.questions_text9), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}),
        };
    }
}
