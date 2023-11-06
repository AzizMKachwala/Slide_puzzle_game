package com.example.slidepuzzlegame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.slidepuzzlegame.R;

public class SplashActivity extends AppCompatActivity {

    ImageView splash_image;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_image = findViewById(R.id.splash_image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        splash_image.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}