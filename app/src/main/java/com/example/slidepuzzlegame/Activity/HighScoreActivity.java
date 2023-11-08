package com.example.slidepuzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.slidepuzzlegame.Adapter.HighScoreAdapter;
import com.example.slidepuzzlegame.Adapter.RegisteredUserAdapter;
import com.example.slidepuzzlegame.MyDatabaseHandler;
import com.example.slidepuzzlegame.MyDatabaseModel;
import com.example.slidepuzzlegame.MyDatabaseScoreModel;
import com.example.slidepuzzlegame.R;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    RecyclerView highScoreRecyclerView;
    Button btnClose;
    HighScoreAdapter highScoreAdapter;
    ArrayList<MyDatabaseScoreModel> scoreModels;
    MyDatabaseHandler myDatabaseHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        btnClose = findViewById(R.id.btnClose);
        highScoreRecyclerView = findViewById(R.id.highScoreRecyclerView);

        myDatabaseHandler = new MyDatabaseHandler(HighScoreActivity.this);

        scoreModels = myDatabaseHandler.getAllScores();
        highScoreAdapter = new HighScoreAdapter(HighScoreActivity.this, scoreModels);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        highScoreRecyclerView.setLayoutManager(new LinearLayoutManager(HighScoreActivity.this));
        highScoreRecyclerView.setAdapter(highScoreAdapter);
    }
}