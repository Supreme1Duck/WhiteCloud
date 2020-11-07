package com.example.myapplication.animation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activities.MainActivity;

public class motion_activity extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motion_layout);
        ImageView image = findViewById(R.id.icon1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startTheActivity();
            }
        }, 500);
    }

    private void startTheActivity() {
        Intent intent = new Intent(motion_activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
