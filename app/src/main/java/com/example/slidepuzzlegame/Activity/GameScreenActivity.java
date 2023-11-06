package com.example.slidepuzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slidepuzzlegame.R;
import com.example.slidepuzzlegame.Tools;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameScreenActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView txtMovesCount, txtTimeCount, txtUserNameGot;
    ImageView imgBack;
    CircleImageView imgUserImageGot;
    String Name,imagePath;
    Tools tools;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        tools = new Tools(GameScreenActivity.this);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        imgBack = findViewById(R.id.imgBack);
        imgUserImageGot = findViewById(R.id.imgUserImageGot);
        txtMovesCount = findViewById(R.id.txtMovesCount);
        txtTimeCount = findViewById(R.id.txtTimeCount);
        txtUserNameGot = findViewById(R.id.txtUserNameGot);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Name = getIntent().getStringExtra("Name");
        imagePath = getIntent().getStringExtra("ImagePath");

        txtUserNameGot.setText(Name);
        if (imagePath != null && !imagePath.isEmpty()) {
            Tools.DisplayImage(GameScreenActivity.this, imgUserImageGot, imagePath);
        }
    }
}