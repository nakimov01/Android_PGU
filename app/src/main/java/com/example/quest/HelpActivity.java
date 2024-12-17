package com.example.quest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class HelpActivity extends AppCompatActivity {
    // Ключи
    public static final String EXTRA_INDEX_OF_QUESTION = "com.example.quest.index"; // Переданный индекс мины
    public static final String EXTRA_HELP_WAS_USED = "com.example.quest.help_was_used"; // Флаг использования подсказки
    private static final String KEY_HELP_TEXT = "help_text"; // Ключ для сохранения текста
    private static final String HELP_WAS_USED = "help_used"; // Ключ для сохранения флага использования подсказки

    // поля
    private int mineIndex; // Индекс ячейки с миной
    private boolean isHelp; // Флаг использования подсказки

    // эл интерфейса
    private TextView mHelpText;
    private AppCompatButton mHelpButton, mHelpButtonExit;

    // массив текстов
    private final int[] mHelpArray = {
            R.string.help_1, R.string.help_2, R.string.help_3,
            R.string.help_4, R.string.help_5, R.string.help_6,
            R.string.help_7, R.string.help_8, R.string.help_9
    };


    private void setHelpUsedResult(boolean isHelpUsed) {
        Intent data = new Intent();
        data.putExtra(EXTRA_HELP_WAS_USED, isHelpUsed);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // инициализация состояния  (по умолчанию)
        setHelpUsedResult(false);

        // Получаем индекс мины
        mineIndex = getIntent().getIntExtra(EXTRA_INDEX_OF_QUESTION, 0);

        // элементы интерфейса
        mHelpText = findViewById(R.id.help_text);
        mHelpButton = findViewById(R.id.helpButton2);
        mHelpButtonExit = findViewById(R.id.helpButtonExit);

        // восстановление данных при повороте экрана
        if (savedInstanceState != null) {
            // восстанавливаем текст и состояние подсказки
            String savedText = savedInstanceState.getString(KEY_HELP_TEXT);
            isHelp = savedInstanceState.getBoolean(HELP_WAS_USED, false);
            if (savedText != null) {
                mHelpText.setText(savedText);
            }
        }

        // если подсказка уже использовалась до поворота экрана, устанавливаем текст
        if (isHelp) {
            mHelpText.setText(getString(mHelpArray[mineIndex]));
            setHelpUsedResult(true);
        }


        // установка обработчика для кнопки показать подсказку
        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // устанавливаем текст помощи на основе переданного индекса
                mHelpText.setText(getString(mHelpArray[mineIndex]));
                isHelp = true;
                setHelpUsedResult(true); // Уведомляем что подсказка была использована
            }
        });


        // установка обработчика для кнопки выход
        mHelpButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Сохраняем текущий текст подсказки и флаг использования
        if (mHelpText != null) {
            savedInstanceState.putString(KEY_HELP_TEXT, mHelpText.getText().toString());
        }
        savedInstanceState.putBoolean(HELP_WAS_USED, isHelp);
    }
}
