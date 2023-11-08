package com.example.slidepuzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slidepuzzlegame.Adapter.ScoreHistoryAdapter;
import com.example.slidepuzzlegame.MyDatabaseHandler;
import com.example.slidepuzzlegame.MyDatabaseScoreModel;
import com.example.slidepuzzlegame.R;
import com.example.slidepuzzlegame.Tools;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryActivity extends AppCompatActivity {

    CircleImageView imgUserImageGot;
    TextView txtUserNameGot;
    RecyclerView scoreHistoryRecyclerView;
    ImageView imgBack;
    ScoreHistoryAdapter scoreHistoryAdapter;
    ArrayList<MyDatabaseScoreModel> scoreModels;
    MyDatabaseScoreModel myDatabaseScoreModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        imgUserImageGot = findViewById(R.id.imgUserImageGot);
        txtUserNameGot = findViewById(R.id.txtUserNameGot);
        scoreHistoryRecyclerView = findViewById(R.id.scoreHistoryRecyclerView);
        imgBack = findViewById(R.id.imgBack);

        int UserId = getIntent().getIntExtra("userId",0);

        MyDatabaseHandler myDatabaseHandler = new MyDatabaseHandler(HistoryActivity.this);
        scoreModels = myDatabaseHandler.getUserHistory(UserId);

        scoreHistoryAdapter = new ScoreHistoryAdapter(HistoryActivity.this,scoreModels);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String Name = getIntent().getStringExtra("Name");
        String ImagePath = getIntent().getStringExtra("ImagePath");

        txtUserNameGot.setText(Name);
        if (ImagePath != null && !ImagePath.isEmpty()) {
            Tools.DisplayImage(HistoryActivity.this, imgUserImageGot, ImagePath);
        }

        scoreHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        scoreHistoryRecyclerView.setAdapter(scoreHistoryAdapter);
    }
}