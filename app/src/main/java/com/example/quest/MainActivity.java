package com.example.quest;

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
    // Лог-тег для отладки
    private static final String TAG = "MainActivity";

    // Ключи для сохранения состояния активности
    private static final String KEY_INDEX = "currentQuestionIndex";
    private static final String KEY_GRID_STATES = "gridStates";

    // Кнопки интерфейса
    private Button nikitaBtn, maxBtn, sashaBtn;
    private Button prevBtn, nextBtn;

    // Текстовые элементы
    private TextView questionsTextView;
    private TextView[] gridCells = new TextView[9]; // Ячейки сетки

    // Текущее состояние
    private int currentQuestionIndex = 0; // Индекс текущего вопроса
    private int[] gridStates = new int[9]; // Состояния ячеек (0: default, 1: correct, 2: wrong)
    private int mineIndex; // Индекс случайной мины

    private Question[] questions; // Массив вопросов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация вопросов через контекст активности
        questions = Question.getQuestions(this);

        Log.d(TAG, "onCreate called");

        // Инициализация элементов интерфейса
        questionsTextView = findViewById(R.id.questions);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);

        // Инициализация кнопок разработчиков
        nikitaBtn = findViewById(R.id.developerNikita);
        maxBtn = findViewById(R.id.developerSasha);
        sashaBtn = findViewById(R.id.developerMax);

        // Добавляем уведомления для кнопок разработчиков
        nikitaBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer1)));
        maxBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer2)));
        sashaBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer3)));

        // Инициализация сетки ячеек
        initGrid();

        // Восстановление состояния активности, если оно было сохранено
        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            gridStates = savedInstanceState.getIntArray(KEY_GRID_STATES);
            restoreGridBackgrounds();
        }

        // Отображаем текущий вопрос и генерируем случайную мину
        displayQuestion();
        generateMine();

        // Устанавливаем обработчики для кнопок навигации
        setNavigationListeners();
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

        // Назначаем обработчики кликов для каждой ячейки
        for (int i = 0; i < gridCells.length; i++) {
            final int index = i;
            gridCells[i].setOnClickListener(v -> checkAnswer(index));
        }
    }

    // Отображает текст текущего вопроса
    private void displayQuestion() {
        questionsTextView.setText(questions[currentQuestionIndex].getQuestionText());
    }

    // Обрабатывает нажатия на кнопки "Назад" и "Вперед"
    private void setNavigationListeners() {
        prevBtn.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion();
                resetGridBackgrounds();
                generateMine(); // Пересчитываем случайную мину для нового вопроса
            } else {
                Toast.makeText(this, "Это первый вопрос", Toast.LENGTH_SHORT).show();
            }
        });

        nextBtn.setOnClickListener(v -> {
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                displayQuestion();
                resetGridBackgrounds();
                generateMine(); // Пересчитываем случайную мину для нового вопроса
            } else {
                Toast.makeText(this, "Это последний вопрос", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Сбрасывает фон всех ячеек на значения по умолчанию
    private void resetGridBackgrounds() {
        for (int i = 0; i < gridCells.length; i++) {
            gridStates[i] = 0; // Сбрасываем состояние
            gridCells[i].setBackgroundColor(i % 2 == 0 ? Color.parseColor("#9b5b9b") : Color.parseColor("#a46fa4"));
        }
    }

    // Восстанавливает фон ячеек из сохраненного состояния
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

    // Генерирует случайную мину для текущего вопроса
    private void generateMine() {
        int[] validIndexes = questions[currentQuestionIndex].getCorrectIndexes();
        Random random = new Random();
        mineIndex = validIndexes[random.nextInt(validIndexes.length)];
    }

    // Проверяет, была ли нажата правильная ячейка
    private void checkAnswer(int index) {
        boolean isCorrect = (index == mineIndex);

        gridStates[index] = isCorrect ? 1 : 2; // Сохраняем состояние ячейки
        gridCells[index].setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        Toast.makeText(this, isCorrect ? "Вы нашли мину!" : "Мимо!", Toast.LENGTH_SHORT).show();
    }

    // Показывает уведомление о выбранном разработчике
    private void showDeveloperToast(String developerName) {
        Toast.makeText(this, "Разработчик: " + developerName, Toast.LENGTH_SHORT).show();
    }

    // Сохраняет состояние активности при смене конфигурации
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentQuestionIndex);
        outState.putIntArray(KEY_GRID_STATES, gridStates);
        Log.d(TAG, "onSaveInstanceState called");
    }

    // Восстанавливает состояние активности
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState called");
    }

    // Логирование этапов жизненного цикла
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}
