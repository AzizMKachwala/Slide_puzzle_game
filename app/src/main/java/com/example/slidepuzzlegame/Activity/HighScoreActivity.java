package com.example.slidepuzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.slidepuzzlegame.R;

public class HighScoreActivity extends AppCompatActivity {

    RecyclerView highScoreRecyclerView;
    Button btnClose;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        btnClose = findViewById(R.id.btnClose);
        highScoreRecyclerView = findViewById(R.id.highScoreRecyclerView);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}