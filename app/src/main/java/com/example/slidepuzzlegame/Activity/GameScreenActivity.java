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
    Button btnShuffle, btnSolve, btnPause;
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
    private boolean isGamePaused = false;


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
        btnPause = findViewById(R.id.btnPause);
        imgBack = findViewById(R.id.imgBack);
        imgSetting = findViewById(R.id.imgSetting);
        imgUserImageGot = findViewById(R.id.imgUserImageGot);
        txtMovesCount = findViewById(R.id.txtMovesCount);
        txtTimeCount = findViewById(R.id.txtTimeCount);
        txtUserNameGot = findViewById(R.id.txtUserNameGot);

        btnPause.setEnabled(false);
        btnShuffle.setEnabled(false);

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

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGamePaused) {
                    isGamePaused = false;
                    btnPause.setText("PAUSE");
                    loadTimer();
                    for (int i = 0; i < gridGroup.getChildCount(); i++) {
                        buttons[i / 3][i % 3].setClickable(true);
                    }
                } else {
                    isGamePaused = true;
                    btnPause.setText("RESUME");
                    if (timer != null) {
                        timer.cancel();
                    }
                    for (int i = 0; i < gridGroup.getChildCount(); i++) {
                        buttons[i / 3][i % 3].setClickable(false);
                    }
                }
            }
        });

        String Name = getIntent().getStringExtra("Name");
        String ImagePath = getIntent().getStringExtra("ImagePath");

        txtUserNameGot.setText(Name);
        if (ImagePath != null && !ImagePath.isEmpty()) {
            Tools.DisplayImage(GameScreenActivity.this, imgUserImageGot, ImagePath);
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
//        if (timer != null) {
//            timer.cancel();
//        }
//
//        isFirstMove = true;
//        btnShuffle.setEnabled(false);
//        btnPause.setEnabled(false);
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

    private boolean isSolvable() {
        int countInversion = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i] && tiles[i] != 0 && tiles[j] != 0) {
                    countInversion++;
                }
            }
        }
        return countInversion % 2 == 0;
    }

    private void generateNumbers() {
        int n = 9;
        Random random = new Random();
        for (int i = 0; i < n - 1; i++) {
            tiles[i] = i + 1;
        }
        tiles[n - 1] = 0; // Set the last tile as empty
        int lastIndex = n - 1;

        for (int i = 0; i < n - 1; i++) {
            int randomIndex = random.nextInt(n - 1);
            int temp = tiles[i];
            tiles[i] = tiles[randomIndex];
            tiles[randomIndex] = temp;

            if (tiles[i] == 0) {
                emptyX = i / 3;
                emptyY = i % 3;
            }

            if (tiles[randomIndex] == 0) {
                emptyX = randomIndex / 3;
                emptyY = randomIndex % 3;
            }
        }

        if (!isSolvable()) {
            int temp = tiles[lastIndex - 1];
            tiles[lastIndex - 1] = tiles[lastIndex - 2];
            tiles[lastIndex - 2] = temp;
        }
    }

    private void loadDataToView() {
        for (int i = 0; i < gridGroup.getChildCount(); i++) {
            int x = i / 3;
            int y = i % 3;
            buttons[x][y].setText(String.valueOf(tiles[i]));
            buttons[x][y].setBackgroundColor(ContextCompat.getColor(this, android.R.color.background_light));
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
    }

    public void buttonClick(View view) {
        if (!isGamePaused) {
            Button button = (Button) view;
            String tag = button.getTag().toString();
            int x = Character.getNumericValue(tag.charAt(0)); // Extract row from the tag
            int y = Character.getNumericValue(tag.charAt(1)); // Extract column from the tag

            if (isFirstMove) {
                isFirstMove = false;
                btnPause.setEnabled(true);
                btnShuffle.setEnabled(true);
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
    }

    private void checkWin() {
        boolean isWin = false;
        if (emptyX == 2 && emptyY == 2) {
            for (int i = 0; i < gridGroup.getChildCount() - 1; i++) {
                if (!buttons[i / 3][i % 3].getText().toString().equals(String.valueOf(i + 1))) {
                    isWin = false;
                    break;
                } else {
                    isWin = true;
                }
            }
        }
        if (isWin) {
            Toast.makeText(this, "Win!!!/nMoves: " + movesCount, Toast.LENGTH_SHORT).show();
            for (int i = 0; i < gridGroup.getChildCount(); i++) {
                buttons[i / 3][i % 3].setClickable(false);
                timer.cancel();
                btnPause.setEnabled(false);
                btnShuffle.setEnabled(false);
            }
        }
    }

}