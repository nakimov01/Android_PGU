package com.example.quest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button nikitaBtn;
    private Button maxBtn;
    private Button sashaBtn;
    private Button prevBtn, nextBtn;
    private TextView questionsTextView;
    private String[] questions;
    private int currentQuestionIndex = 0;

    // TextView для ячеек GridLayout
    private TextView[] gridCells = new TextView[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nikitaBtn = (Button) findViewById(R.id.developerNikita);
        maxBtn = (Button) findViewById(R.id.developerSasha);
        sashaBtn = (Button) findViewById(R.id.developerMax);

        nikitaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.developer1,
                        Toast.LENGTH_SHORT).show();

                nikitaBtn.setBackgroundResource(R.drawable.round_green_stroke);
                maxBtn.setBackgroundResource(R.drawable.white_button_background);
                sashaBtn.setBackgroundResource(R.drawable.white_button_background);
                nikitaBtn.setBackgroundTintList(null);
            }
        });

        maxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.developer2,
                        Toast.LENGTH_SHORT).show();
                maxBtn.setBackgroundResource(R.drawable.round_green_stroke);
                sashaBtn.setBackgroundResource(R.drawable.white_button_background);
                nikitaBtn.setBackgroundResource(R.drawable.white_button_background);
                maxBtn.setBackgroundTintList(null);
            }
        });

        sashaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.developer3,
                        Toast.LENGTH_SHORT).show();
                sashaBtn.setBackgroundResource(R.drawable.round_green_stroke);
                maxBtn.setBackgroundResource(R.drawable.white_button_background);
                nikitaBtn.setBackgroundResource(R.drawable.white_button_background);
                sashaBtn.setBackgroundTintList(null);
            }
        });

        // Инициализация кнопок навигации
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);

        // Инициализация TextView для вопросов
        questionsTextView = findViewById(R.id.questions);

        // Инициализация массива вопросов
        questions = new String[]{
                getString(R.string.questions_text1),
                getString(R.string.questions_text2),
                getString(R.string.questions_text3),
                getString(R.string.questions_text4),
                getString(R.string.questions_text5),
                getString(R.string.questions_text6),
                getString(R.string.questions_text7),
                getString(R.string.questions_text8),
                getString(R.string.questions_text9)
        };

        // Отображаем первый вопрос
        questionsTextView.setText(questions[currentQuestionIndex]);

        // Инициализация ячеек сетки (TextView)
        gridCells[0] = findViewById(R.id.btn1);
        gridCells[1] = findViewById(R.id.btn2);
        gridCells[2] = findViewById(R.id.btn3);
        gridCells[3] = findViewById(R.id.btn4);
        gridCells[4] = findViewById(R.id.btn5);
        gridCells[5] = findViewById(R.id.btn6);
        gridCells[6] = findViewById(R.id.btn7);
        gridCells[7] = findViewById(R.id.btn8);
        gridCells[8] = findViewById(R.id.btn9);

        // Добавляем обработчики на ячейки сетки
        for (int i = 0; i < gridCells.length; i++) {
            final int index = i;
            gridCells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleGridClick(index);
                }
            });
        }

        // Обработка нажатий на кнопки "Назад" и "Вперед"
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    questionsTextView.setText(questions[currentQuestionIndex]);
                    resetGridBackgrounds(); // Сбрасываем фон ячеек
                } else {
                    Toast.makeText(MainActivity.this, "Это первый вопрос", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    questionsTextView.setText(questions[currentQuestionIndex]);
                    resetGridBackgrounds(); // Сбрасываем фон ячеек
                } else {
                    Toast.makeText(MainActivity.this, "Это последний вопрос", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Метод для сброса фона всех ячеек
    private void resetGridBackgrounds() {
        for (int i = 0; i < gridCells.length; i++) {
            if (i % 2 == 0) {
                // Четные индексы: устанавливаем серый фон
                gridCells[i].setBackgroundColor(Color.parseColor("#a46fa4"));
            } else {
                // Нечетные индексы: устанавливаем белый фон
                gridCells[i].setBackgroundColor(Color.parseColor("#9b5b9b"));
            }
        }
    }

    // Логика обработки кликов по ячейкам сетки
    private void handleGridClick(int index) {
        String currentQuestion = questions[currentQuestionIndex];

        // Проверка на соответствие текущему вопросу
        switch (currentQuestion) {
            case "В первом столбце":
                if (index == 0 || index == 3 || index == 6) {
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                } else {
                    Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.wrong_answer_background);
                }
                break;

            case "Во втором столбце":
                if (index == 1 || index == 4 || index == 7) {
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                } else {
                    Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.wrong_answer_background);
                }
                break;

            case "В третьем столбце":
                if (index == 2 || index == 5 || index == 8) {
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                } else {
                    Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.wrong_answer_background);
                }
                break;

            case "В первой строке":
                if (index == 0 || index == 1 || index == 2) {
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                } else {
                    Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.wrong_answer_background);
                }
                break;

            case "Во второй строке":
                if (index == 3 || index == 4 || index == 5) {
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                } else {
                    Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.wrong_answer_background);
                }
                break;

            case "В третьей строке":
                if (index == 6 || index == 7 || index == 8) {
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                } else {
                    Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.wrong_answer_background);
                }
                break;

            case "По диагонали снизу-вверх":
                if (index == 6 || index == 4 || index == 2) {
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                } else {
                    Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.wrong_answer_background);
                }
                break;

            case "По всем диагоналям":
                if (index == 0 || index == 4 || index == 8 || index == 2 || index == 6) {
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                } else {
                    Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show();
                    gridCells[index].setBackgroundResource(R.drawable.wrong_answer_background);
                }
                break;

            case "Среди всех ячеек":
                // Допустим, любое нажатие верное для этого случая
                Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
                gridCells[index].setBackgroundResource(R.drawable.correct_answer_background);
                break;

            default:
                Toast.makeText(this, "Ошибка!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
