package com.example.myapplication.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.customViews.ProgressButton;

public class AssistantActivity extends AppCompatActivity {
private View view1;
private TextView currentTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_activity);
        view1 = findViewById(R.id.btn_start);
        currentTime = findViewById(R.id.currentTime);
        view1.setOnClickListener(view -> {
            ProgressButton button = new ProgressButton(getBaseContext(), view1);
            button.buttonActivated();
            Handler handler = new Handler();
            handler.postDelayed(() -> button.buttonFinished(AssistantActivity.this), 1500);
        });
    }
}
