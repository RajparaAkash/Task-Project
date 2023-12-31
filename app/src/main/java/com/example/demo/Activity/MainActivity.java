package com.example.demo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;

public class MainActivity extends AppCompatActivity {

    Button task1Btn;
    Button task2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idBinding();

        task1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditTextActivity.class));
            }
        });

        task2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListDataActivity.class));
            }
        });
    }

    private void idBinding() {
        task1Btn = findViewById(R.id.task1Btn);
        task2Btn = findViewById(R.id.task2Btn);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}