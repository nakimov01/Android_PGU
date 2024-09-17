package com.example.quest;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import com.example.quest.R;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button developer1;
    private Button developer2;
    private Button developer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        developer1 = (Button)findViewById(R.id.developerNikita);


        developer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.developer1,
                        Toast.LENGTH_SHORT).show();
            }
        });


        developer2 = (Button) findViewById(R.id.developerSasha);


        developer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.developer2,
                        Toast.LENGTH_SHORT).show();
            }
        });

        developer3 = (Button) findViewById(R.id.developerMax);

        developer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.developer3,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

}


