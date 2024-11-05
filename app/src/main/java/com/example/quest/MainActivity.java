// MainActivity.java
package com.example.quest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quest.R;

public class MainActivity extends AppCompatActivity {
    private Button nikitaBtn, maxBtn, sashaBtn;
    private Button prevBtn, nextBtn;
    private TextView questionsTextView;
    private TextView[] gridCells = new TextView[9];
    private int currentQuestionIndex = 0;

    // Массив вопросов (Model)
    private Question[] questions = new Question[] {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionsTextView = findViewById(R.id.questions);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);

        nikitaBtn = findViewById(R.id.developerNikita);
        maxBtn = findViewById(R.id.developerSasha);
        sashaBtn = findViewById(R.id.developerMax);

        // Toast уведомления для каждой кнопки разработчика
        nikitaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Разработчик: Никита", Toast.LENGTH_SHORT).show();
                nikitaBtn.setBackgroundResource(R.drawable.round_green_stroke);
                maxBtn.setBackgroundResource(R.drawable.white_button_background);
                sashaBtn.setBackgroundResource(R.drawable.white_button_background);
                nikitaBtn.setBackgroundTintList(null);
            }
        });

        maxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Разработчик: Саша", Toast.LENGTH_SHORT).show();
                maxBtn.setBackgroundResource(R.drawable.round_green_stroke);
                nikitaBtn.setBackgroundResource(R.drawable.white_button_background);
                sashaBtn.setBackgroundResource(R.drawable.white_button_background);
                maxBtn.setBackgroundTintList(null);
            }
        });

        sashaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Разработчик: Макс", Toast.LENGTH_SHORT).show();
                sashaBtn.setBackgroundResource(R.drawable.round_green_stroke);
                maxBtn.setBackgroundResource(R.drawable.white_button_background);
                nikitaBtn.setBackgroundResource(R.drawable.white_button_background);
                sashaBtn.setBackgroundTintList(null);
            }
        });

        // Инициализация ячеек сетки и логика вопросов
        initGrid();
        displayQuestion();
        setNavigationListeners();
    }

    private void initGrid() {
        gridCells[0] = findViewById(R.id.btn1);
        gridCells[1] = findViewById(R.id.btn2);
        gridCells[2] = findViewById(R.id.btn3);
        gridCells[3] = findViewById(R.id.btn4);
        gridCells[4] = findViewById(R.id.btn5);
        gridCells[5] = findViewById(R.id.btn6);
        gridCells[6] = findViewById(R.id.btn7);
        gridCells[7] = findViewById(R.id.btn8);
        gridCells[8] = findViewById(R.id.btn9);
        for (int i = 0; i < gridCells.length; i++) {
            final int index = i;
            gridCells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(index);
                }
            });
        }
    }

    private void displayQuestion() {
        questionsTextView.setText(questions[currentQuestionIndex].getQuestionText());
    }

    private void setNavigationListeners() {
        prevBtn.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion();
                resetGridBackgrounds();
            } else {
                Toast.makeText(this, "Это первый вопрос", Toast.LENGTH_SHORT).show();
            }
        });

        nextBtn.setOnClickListener(v -> {
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                displayQuestion();
                resetGridBackgrounds();
            } else {
                Toast.makeText(this, "Это последний вопрос", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetGridBackgrounds() {
        for (int i = 0; i < gridCells.length; i++) {
            gridCells[i].setBackgroundColor(i % 2 == 0 ? Color.parseColor("#a46fa4") : Color.parseColor("#9b5b9b"));
        }
    }

    private void checkAnswer(int index) {
        int[] correctIndexes = questions[currentQuestionIndex].getCorrectIndexes();
        boolean isCorrect = false;
        for (int i : correctIndexes) {
            if (i == index) {
                isCorrect = true;
                break;
            }
        }

        gridCells[index].setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        Toast.makeText(this, isCorrect ? "Правильно!" : "Неправильно", Toast.LENGTH_SHORT).show();
    }
}
