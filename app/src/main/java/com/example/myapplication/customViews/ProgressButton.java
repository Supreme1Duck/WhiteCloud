package com.example.myapplication.customViews;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;

public class ProgressButton {
    private ConstraintLayout layout;
    private ProgressBar progressBar;
    private TextView textView;
    Animation fade_in;

    public ProgressButton(Context ct, View view){
        fade_in = AnimationUtils.loadAnimation(ct, R.anim.fade_in);
        layout = view.findViewById(R.id.constraint_layout);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.textview_start);
    }

    public void buttonActivated() {
        progressBar.setAnimation(fade_in);
        progressBar.setVisibility(View.VISIBLE);
        textView.setAnimation(fade_in);
        textView.setText("Please wait...");
    }

    public void buttonFinishedWith(Context ct) {
        progressBar.setVisibility(View.GONE);
        layout.setBackgroundColor(Color.WHITE);
        textView.setTextColor(ct.getResources().getColor(R.color.colorGreen));
        textView.setText("Done");
    }

    public void buttonFinished(Context ct) {
        progressBar.setVisibility(View.GONE);
        layout.setBackgroundColor(Color.WHITE);
        textView.setTextColor(ct.getResources().getColor(R.color.colorGreen));
        textView.setText("Done");
    }

    public void setEnabled(int n) {
        if (n == 1) layout.setClickable(true);
        else layout.setClickable(false);
    }
}
