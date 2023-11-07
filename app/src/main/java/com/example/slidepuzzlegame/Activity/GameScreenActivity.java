package com.example.slidepuzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slidepuzzlegame.R;
import com.example.slidepuzzlegame.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameScreenActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnShuffle;
    TextView txtMovesCount, txtTimeCount, txtUserNameGot;
    ImageView imgBack, imgSetting;
    CircleImageView imgUserImageGot;
    Tools tools;
    GridLayout gridGroup;


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
        btn9 = findViewById(R.id.btn9);
        gridGroup = findViewById(R.id.gridGroup);
        btnShuffle = findViewById(R.id.btnShuffle);
        imgBack = findViewById(R.id.imgBack);
        imgSetting = findViewById(R.id.imgSetting);
        imgUserImageGot = findViewById(R.id.imgUserImageGot);
        txtMovesCount = findViewById(R.id.txtMovesCount);
        txtTimeCount = findViewById(R.id.txtTimeCount);
        txtUserNameGot = findViewById(R.id.txtUserNameGot);

        shuffleButtons();

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
            new android.os.Handler().postDelayed(() -> {
                Tools.DisplayImage(GameScreenActivity.this, imgUserImageGot, ImagePath);
            }, 1000);
        }

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("SETTING");
                builder.setMessage("Do you Want to use it?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(GameScreenActivity.this, "YES CLICKED", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(GameScreenActivity.this, "NO CLICKED", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuffleButtons();
            }
        });

    }

    private void shuffleButtons() {
        List<Button> buttons = new ArrayList<>();
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8);
        buttons.add(btn9);

        Collections.shuffle(buttons);
        gridGroup.removeAllViews();
        for (Button button : buttons) {
            gridGroup.addView(button);
        }
    }
}