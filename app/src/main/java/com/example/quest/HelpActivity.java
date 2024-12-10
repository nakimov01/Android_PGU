package com.example.quest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

public class HelpActivity extends AppCompatActivity {
    public static final String EXTRA_INDEX_OF_QUESTION = "com.example.quest.index";
    private static final String KEY_HELP_TEXT = "help_text";
    private static final String TAG = "HelpActivity";

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

        // Восстановление mineIndex
        if (savedInstanceState != null) {
            mineIndex = savedInstanceState.getInt(EXTRA_INDEX_OF_QUESTION, 0);
        } else {
            mineIndex = getIntent().getIntExtra(EXTRA_INDEX_OF_QUESTION, 0);
        }
        Log.d(TAG, "Получен mineIndex: " + mineIndex);

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

        mHelpButton.setOnClickListener(v -> {
            mHelpText.setText(getString(mHelpArray[mineIndex]));
        });

        mHelpButtonExit.setOnClickListener(v -> finish());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState вызван");
        savedInstanceState.putInt(EXTRA_INDEX_OF_QUESTION, mineIndex); // Сохранение индекса
        if (mHelpText != null) {
            savedInstanceState.putString(KEY_HELP_TEXT, mHelpText.getText().toString());
        }
    }
}
