package com.example.quest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class HelpActivity extends AppCompatActivity {
    public static final String EXTRA_INDEX_OF_QUESTION = "com.example.quest.index";
    private static final String INDEX = "index";
    private static final String KEY_HELP_TEXT = "help_text"; // Ключ для сохранения текста

    private int mineIndex;
    private TextView mHelpText;
    private AppCompatButton mHelpButton, mHelpButtonExit;
    private int[] mHelpArray = {
            R.string.help_1, R.string.help_2, R.string.help_3,
            R.string.help_4, R.string.help_5, R.string.help_6,
            R.string.help_7, R.string.help_8, R.string.help_9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mineIndex = getIntent().getIntExtra(EXTRA_INDEX_OF_QUESTION, 0);

        mHelpText = findViewById(R.id.help_text);
        mHelpButton = findViewById(R.id.helpButton2);
        mHelpButtonExit = findViewById(R.id.helpButtonExit);

        // Восстановление текста при повороте экрана
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_HELP_TEXT);
            if (savedText != null) {
                mHelpText.setText(savedText);
            }
        }

        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Отображаем текст помощи для переданного индекса
                mHelpText.setText(getString(mHelpArray[mineIndex]));
            }
        });

        mHelpButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Закрываем активность
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Сохраняем индекс и текущий текст
        savedInstanceState.putInt(EXTRA_INDEX_OF_QUESTION, mineIndex);
        if (mHelpText != null) {
            savedInstanceState.putString(KEY_HELP_TEXT, mHelpText.getText().toString());
        }
    }
}
