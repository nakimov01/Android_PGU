package com.example.quest;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.quest.R;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button nikitaBtn;
    private Button maxBtn;
    private Button sashaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nikitaBtn = (Button)findViewById(R.id.developerNikita);
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

    }

}


