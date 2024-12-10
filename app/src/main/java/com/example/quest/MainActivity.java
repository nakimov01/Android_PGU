// MainActivity.java
package com.example.quest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // Тег для логирования событий жизненного цикла и отладки
    private static final String TAG = "MainActivity";

    // Ключи для сохранения и восстановления состояния активности
    private static final String KEY_INDEX = "currentQuestionIndex";
    private static final String KEY_GRID_STATES = "gridStates";

    // Элементы пользовательского интерфейса
    private Button nikitaBtn, maxBtn, sashaBtn, mHelpButton;
    private Button prevBtn, nextBtn;
    private TextView questionsTextView;
    private TextView[] gridCells = new TextView[9]; // Массив для представления ячеек сетки

    // Переменные состояния
    private int currentQuestionIndex = 0; // Индекс текущего вопроса
    private int[] gridStates = new int[9]; // Состояния ячеек (0: по умолчанию, 1: правильно, 2: неправильно)
    private int mineIndex; // Индекс случайно выбранной мины

    // Массив вопросов
    private Question[] questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация массива вопросов
        questions = Question.getQuestions(this);

        Log.d(TAG, "onCreate вызван");

        // Инициализация элементов пользовательского интерфейса
        questionsTextView = findViewById(R.id.questions);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);

        // Инициализация кнопок разработчиков
        nikitaBtn = findViewById(R.id.developerNikita);
        maxBtn = findViewById(R.id.developerSasha);
        sashaBtn = findViewById(R.id.developerMax);

        // Добавление уведомлений для кнопок разработчиков
        nikitaBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer1)));
        maxBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer2)));
        sashaBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer3)));

        // Инициализация кнопки помощи
        mHelpButton = findViewById(R.id.helpButton);

        // Инициализация сетки ячеек
        initGrid();

        // Восстановление состояния активности, если оно было сохранено
        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            gridStates = savedInstanceState.getIntArray(KEY_GRID_STATES);
            mineIndex = savedInstanceState.getInt("mineIndex"); // Восстановление индекса мины
            restoreGridBackgrounds(); // Восстановление фоновых цветов ячеек
        } else {
            generateMine(); // Если данных нет, генерируем заново
        }

        // Отображение текущего вопроса
        displayQuestion();

        // Настройка слушателей для кнопок навигации
        setNavigationListeners();

        // Настройка кнопки помощи
        mHelpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            intent.putExtra(HelpActivity.EXTRA_INDEX_OF_QUESTION, mineIndex); // Передача случайного индекса
            startActivityForResult(intent, 0);
        });
    }


    // Инициализация сетки с ячейками
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

        // Назначение обработчиков нажатий для каждой ячейки
        for (int i = 0; i < gridCells.length; i++) {
            final int index = i;
            gridCells[i].setOnClickListener(v -> checkAnswer(index));
        }
    }

    // Отображение текста текущего вопроса
    private void displayQuestion() {
        questionsTextView.setText(questions[currentQuestionIndex].getQuestionText());
    }

    // Настройка слушателей для кнопок "Назад" и "Вперед"
    private void setNavigationListeners() {
        prevBtn.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion();
                resetGridBackgrounds(); // Сброс фона ячеек
                generateMine(); // Генерация новой мины
            } else {
                Toast.makeText(this, "Это первый вопрос", Toast.LENGTH_SHORT).show();
            }
        });

        nextBtn.setOnClickListener(v -> {
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                displayQuestion();
                resetGridBackgrounds(); // Сброс фона ячеек
                generateMine(); // Генерация новой мины
            } else {
                Toast.makeText(this, "Это последний вопрос", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Сброс фоновых цветов ячеек на значения по умолчанию
    private void resetGridBackgrounds() {
        for (int i = 0; i < gridCells.length; i++) {
            gridStates[i] = 0; // Сброс состояния
            gridCells[i].setBackgroundColor(i % 2 == 0 ? Color.parseColor("#9b5b9b") : Color.parseColor("#a46fa4"));
        }
    }

    // Восстановление фоновых цветов ячеек из сохраненного состояния
    private void restoreGridBackgrounds() {
        for (int i = 0; i < gridCells.length; i++) {
            if (gridStates[i] == 1) {
                gridCells[i].setBackgroundColor(Color.GREEN);
            } else if (gridStates[i] == 2) {
                gridCells[i].setBackgroundColor(Color.RED);
            } else {
                gridCells[i].setBackgroundColor(i % 2 == 0 ? Color.parseColor("#9b5b9b") : Color.parseColor("#a46fa4"));
            }
        }
    }

    // Генерация случайной мины для текущего вопроса
    private void generateMine() {
        int[] validIndexes = questions[currentQuestionIndex].getCorrectIndexes();
        Random random = new Random();
        mineIndex = validIndexes[random.nextInt(validIndexes.length)];
    }

    // Проверка, правильная ли ячейка была нажата
    private void checkAnswer(int index) {
        boolean isCorrect = (index == mineIndex);

        gridStates[index] = isCorrect ? 1 : 2; // Сохранение состояния ячейки
        gridCells[index].setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        Toast.makeText(this, isCorrect ? "Вы нашли мину!" : "Мимо!", Toast.LENGTH_SHORT).show();
    }

    // Показ уведомления с именем разработчика
    private void showDeveloperToast(String developerName) {
        Toast.makeText(this, "Разработчик: " + developerName, Toast.LENGTH_SHORT).show();
    }

    // Сохранение состояния активности перед сменой конфигурации
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentQuestionIndex);
        outState.putIntArray(KEY_GRID_STATES, gridStates);
        outState.putInt("mineIndex", mineIndex);
        Log.d(TAG, "onSaveInstanceState вызван");
    }

    // Восстановление состояния активности
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState вызван");
    }

    // Методы жизненного цикла для логирования
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart вызван");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume вызван");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause вызван");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop вызван");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy вызван");
    }
}
