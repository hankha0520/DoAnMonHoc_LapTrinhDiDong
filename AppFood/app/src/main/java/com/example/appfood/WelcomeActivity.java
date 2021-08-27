package com.example.appfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.classs.DataLocalManger;

public class WelcomeActivity extends AppCompatActivity {

    Animation top, bottom;
    ImageView image, img;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Animation
        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image = findViewById(R.id.logo);
        textView = findViewById(R.id.bannertext);

        image.setAnimation(top);
        textView.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Check first install
                if (!DataLocalManger.getFirstInstall()){
                    DataLocalManger.setFirstInstall(true);
                    Intent intent1 = new Intent(WelcomeActivity.this, OnboardingActivity.class);
                    startActivity(intent1);
                }
                else{
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 4000);
    }


}