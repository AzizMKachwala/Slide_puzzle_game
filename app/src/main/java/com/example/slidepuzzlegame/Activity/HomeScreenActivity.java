package com.example.slidepuzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.slidepuzzlegame.Adapter.RegisteredUserAdapter;
import com.example.slidepuzzlegame.MyDatabaseHandler;
import com.example.slidepuzzlegame.MyDatabaseModel;
import com.example.slidepuzzlegame.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    Button btnHighScore;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton btnNewUser;
    RecyclerView registeredUserListRecyclerView;
    RegisteredUserAdapter registeredUserAdapter;
    ArrayList<MyDatabaseModel> databaseModelArrayList;
    MyDatabaseHandler myDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        btnHighScore = findViewById(R.id.btnHighScore);
        btnNewUser = findViewById(R.id.btnNewUser);
        registeredUserListRecyclerView = findViewById(R.id.registeredUserListRecyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe);

        myDatabaseHandler = new MyDatabaseHandler(HomeScreenActivity.this);
        databaseModelArrayList = myDatabaseHandler.getAllUsers();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                databaseModelArrayList = myDatabaseHandler.getAllUsers();
                registeredUserAdapter.updateData(databaseModelArrayList);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        registeredUserAdapter = new RegisteredUserAdapter(HomeScreenActivity.this, databaseModelArrayList);

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, HighScoreActivity.class));
            }
        });

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, RegisterNewUserActivity.class));
            }
        });

        registeredUserListRecyclerView.setLayoutManager(new LinearLayoutManager(HomeScreenActivity.this));
        registeredUserListRecyclerView.setAdapter(registeredUserAdapter);

    }
}