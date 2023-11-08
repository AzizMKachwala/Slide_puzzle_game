package com.example.slidepuzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import com.example.slidepuzzlegame.MyDatabaseHandler;
import com.example.slidepuzzlegame.R;
import com.example.slidepuzzlegame.Tools;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameScreenActivity extends AppCompatActivity {

    //    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnShuffle, btnSolve;
    TextView txtMovesCount, txtTimeCount, txtUserNameGot;
    ImageView imgBack, imgSetting;
    CircleImageView imgUserImageGot;
    Tools tools;
    GridLayout gridGroup;

    private Button[][] buttons;
    private int[] tiles;
    private int emptyX = 2;
    private int emptyY = 2;
    private int movesCount = 0;
    private int timeCount = 0;
    private Timer timer;
    private boolean isFirstMove = true;
    MyDatabaseHandler myDatabaseHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        tools = new Tools(GameScreenActivity.this);

//        btn1 = findViewById(R.id.btn1);
//        btn2 = findViewById(R.id.btn2);
//        btn3 = findViewById(R.id.btn3);
//        btn4 = findViewById(R.id.btn4);
//        btn5 = findViewById(R.id.btn5);
//        btn6 = findViewById(R.id.btn6);
//        btn7 = findViewById(R.id.btn7);
//        btn8 = findViewById(R.id.btn8);
//        btn9 = findViewById(R.id.btn9);

        gridGroup = findViewById(R.id.gridGroup);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnSolve = findViewById(R.id.btnSolve);
        imgBack = findViewById(R.id.imgBack);
        imgSetting = findViewById(R.id.imgSetting);
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

        btnSolve.setVisibility(View.GONE);
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                solvePuzzle();
                Toast.makeText(GameScreenActivity.this, "Solved", Toast.LENGTH_SHORT).show();
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

        loadViews();
        loadNumbers();
        generateNumbers();
        loadDataToView();

    }

//    private void solvePuzzle() {
//        for (int i = 0; i < tiles.length - 1; i++) {
//            tiles[i] = i + 1;
//        }
//
//        // Set the last element to 0 (empty block)
//        tiles[tiles.length - 1] = 0;
//
//        // Reload data to view
//        loadDataToView();
//
//        myDatabaseHandler = new MyDatabaseHandler(GameScreenActivity.this);
//
////        myDatabaseHandler.insertScore(userId, movesCount, timeCount);
//
//        Toast.makeText(this, "Congratulations...\nMoves : " + movesCount + "\nTime Taken : " + timeCount + " Second(s)", Toast.LENGTH_SHORT).show();
//
//        // Reset counters and timers
//        movesCount = 0;
//        txtMovesCount.setText("Moves: 0");
//        timeCount = 0;
//        txtTimeCount.setText("Time: 00:00");
//
//        // Cancel the timer if it's running
//        if (timer != null) {
//            timer.cancel();
//        }
//
//        // Reset the first move flag
//        isFirstMove = true;
//
//    }

    private void loadViews() {
        buttons = new Button[3][3];

        for (int i = 0; i < gridGroup.getChildCount(); i++) {
            int x = i / 3;
            int y = i % 3;
            buttons[x][y] = (Button) gridGroup.getChildAt(i);
        }
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNumbers();
                loadDataToView();
            }
        });
    }

    private void loadTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeCount++;
                setTime(timeCount);
            }
        }, 1000, 1000);
    }

    private void setTime(int timeCount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int second = timeCount % 60;
                int minute = timeCount / 60;

                txtTimeCount.setText(String.format("Time: %02d:%02d", minute, second));
            }
        });
    }

    private void loadNumbers() {
        tiles = new int[9];
        for (int i = 0; i < gridGroup.getChildCount() - 1; i++) {
            tiles[i] = i + 1;
        }
    }

    private void generateNumbers() {
        int n = 8;
        Random random = new Random();

        while (n > 1) {
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum] = tiles[n];
            tiles[n] = temp;
        }
        if (isSolvable())
            generateNumbers();
    }

    private boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i])
                    countInversions++;
            }
        }
        return countInversions % 2 == 0;
    }

    private void loadDataToView() {
        emptyX = 2;
        emptyY = 2;
        for (int i = 0; i < gridGroup.getChildCount() - 1; i++) {
            buttons[i / 3][i % 3].setText(String.valueOf(tiles[i]));
            buttons[i / 3][i % 3].setBackgroundResource(android.R.drawable.btn_default);
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        String tag = button.getTag().toString();
        int x = Character.getNumericValue(tag.charAt(0)); // Extract row from the tag
        int y = Character.getNumericValue(tag.charAt(1)); // Extract column from the tag

        if (isFirstMove) {
            isFirstMove = false;
            loadTimer();
        }

        if ((Math.abs(emptyX - x) == 1 && emptyY == y) || (Math.abs(emptyY - y) == 1 && emptyX == x)) {
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_background));
            emptyX = x;
            emptyY = y;
            movesCount++;
            txtMovesCount.setText("Moves: " + movesCount);
            checkWin();
        }
    }

    private void checkWin() {
        boolean isWin = false;
        if (emptyX == 2 && emptyY == 2) {
            for (int i = 0; i < gridGroup.getChildCount() - 1; i++) {
                if (buttons[i / 3][i % 3].getText().toString().equals(String.valueOf(i))) {
                    isWin = true;
                } else {
                    isWin = false;
                    break;
                }
            }
        }
        if (isWin) {
            Toast.makeText(this, "Win!!!/nMoves: " + movesCount, Toast.LENGTH_SHORT).show();
            for (int i = 0; i < gridGroup.getChildCount(); i++) {
                buttons[i / 3][i % 3].setClickable(false);
            }
            timer.cancel();
            btnShuffle.setClickable(false);
        }
    }

}