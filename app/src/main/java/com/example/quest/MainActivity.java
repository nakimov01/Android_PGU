package com.example.quest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // Тег для логирования (используется для отладки и мониторинга жизненного цикла)
    private static final String TAG = "MainActivity";

    // Ключи для сохранения состояния активности
    private static final String KEY_INDEX = "currentQuestionIndex"; // Текущий индекс вопроса
    private static final String KEY_GRID_STATES = "gridStates"; // Состояния ячеек сетки
    private static final String HELP_IS_USED = "help_used"; // Флаг использования помощи

    // Элементы пользовательского интерфейса
    private Button mHelpButton;
    private Button prevBtn, nextBtn;
    private TextView questionsTextView;
    private TextView[] gridCells = new TextView[9];

    // Переменные для состояния
    private int currentQuestionIndex = 0; // Индекс текущего вопроса
    private int[] gridStates = new int[9]; // Массив для хранения состояния каждой ячейки
    private int mineIndex; // Индекс случайной "мины" (правильный ответ)

    private boolean isHelp = false; // Флаг, указывающий, использовалась ли помощь

    // Массив вопросов
    private Question[] questions;

    //------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate вызван");

        // Инициализация массива вопросов с использованием текущего контекста
        questions = Question.getQuestions(this);

        questionsTextView = findViewById(R.id.questions);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        mHelpButton = findViewById(R.id.helpButton);

        Button nikitaBtn = findViewById(R.id.developerNikita);
        Button maxBtn = findViewById(R.id.developerSasha);
        Button sashaBtn = findViewById(R.id.developerMax);
        // Обработчики для кнопок с именами разработчиков
        nikitaBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer1)));
        maxBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer2)));
        sashaBtn.setOnClickListener(v -> showDeveloperToast(getString(R.string.developer3)));

        // Инициализация сетки (привязка ячеек)
        initGrid();

        // Восстановление состояния активности, если оно было сохранено
        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0); // Индекс текущего вопроса
            gridStates = savedInstanceState.getIntArray(KEY_GRID_STATES); // состояниея ячеек
            isHelp = savedInstanceState.getBoolean(HELP_IS_USED, false); // флаг помощи
            mineIndex = savedInstanceState.getInt("mineIndex"); // индекс мины
            restoreGridBackgrounds(); // востановление фонов ячеек
        } else {
            generateMine(); // если состояние не сохранено генерируем новую мину
        }

        displayQuestion(); // отображение текущего вопроса
        setNavigationListeners(); // настройка кнопок "Назад" и "Вперед"

        // Настройка кнопки "Помощь"
        mHelpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            intent.putExtra(HelpActivity.EXTRA_INDEX_OF_QUESTION, mineIndex); // передача индекс мины
            startActivityForResult(intent, 1); // результат из HelpActivity
        });
    }

    //------------------------------------------------------------------------------------
    // тосты
    private void showDeveloperToast(String developerName) {
        Toast.makeText(this, "Разработчик: " + developerName, Toast.LENGTH_SHORT).show();
    }
    //------------------------------------------------------------------------------------

    // инициализация сетки (привязка ячеек)
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

        // устанавливаем обработчики кликов для каждой ячейки
        for (int i = 0; i < gridCells.length; i++) {
            final int index = i;
            gridCells[i].setOnClickListener(v -> checkAnswer(index));
        }
    }

    //------------------------------------------------------------------------------------

    // отображение текста текущего вопроса
    private void displayQuestion() {
        questionsTextView.setText(questions[currentQuestionIndex].getQuestionText());
    }

    // настройка кнопок назад и вперед
    private void setNavigationListeners() {
        prevBtn.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion(); // Показываем новый вопрос
                resetGridBackgrounds(); // Сбрасываем фоны ячеек
                generateMine(); // Генерируем новую мину
            } else {
                Toast.makeText(this, "Это первый вопрос", Toast.LENGTH_SHORT).show();
            }
        });

        nextBtn.setOnClickListener(v -> {
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                displayQuestion(); // Показываем новый вопрос
                resetGridBackgrounds(); // Сбрасываем фоны ячеек
                generateMine(); // Генерируем новую мину
            } else {
                Toast.makeText(this, "Это последний вопрос", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //------------------------------------------------------------------------------------

    // сброс фонов ячеек
    private void resetGridBackgrounds() {
        for (int i = 0; i < gridCells.length; i++) {
            gridStates[i] = 0; // Обнуляем состояния
            gridCells[i].setBackgroundColor(i % 2 == 0 ? Color.parseColor("#9b5b9b") : Color.parseColor("#a46fa4"));
        }
    }

    // восстановление фонов ячеек из сохраненного состояния
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

    //------------------------------------------------------------------------------------

    // Генерация случайного индекса "мины"
    private void generateMine() {
        int[] validIndexes = questions[currentQuestionIndex].getCorrectIndexes();
        Random random = new Random();
        mineIndex = validIndexes[random.nextInt(validIndexes.length)];
    }

    //------------------------------------------------------------------------------------

    // Проверка ответа
    private void checkAnswer(int index) {
        boolean isCorrect = (index == mineIndex);

        gridStates[index] = isCorrect ? 1 : 2; // Сохраняем состояние ячейки
        gridCells[index].setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);

        String message = isCorrect ? "Вы нашли мину!" : "Попробуйте еще раз.";

        // Если использовалась помощь
        if (isHelp && isCorrect) {
            message += " В следующий раз попробуйте выбрать мину самостоятельно.";
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        isHelp = false; // Сбрасываем флаг
    }

    //------------------------------------------------------------------------------------

    // Сохранение состояния активности
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentQuestionIndex); // Сохраняем индекс вопроса
        outState.putIntArray(KEY_GRID_STATES, gridStates); // Сохраняем состояния ячеек
        outState.putInt("mineIndex", mineIndex); // Сохраняем индекс мины
        outState.putBoolean(HELP_IS_USED, isHelp); // Сохраняем флаг помощи
    }

    // Обработка результата из HelpActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            isHelp = data.getBooleanExtra(HelpActivity.EXTRA_HELP_WAS_USED, false);
        }
    }

    // жизненный цикл
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
