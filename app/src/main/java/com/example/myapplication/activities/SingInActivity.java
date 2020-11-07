package com.example.myapplication.activities;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.fragments.ClientViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SingInActivity extends AppCompatActivity implements View.OnClickListener {
    private AnimationDrawable animationDrawable;
    private EditText edLogin, edPassword;
    private Button button_sign;
    private ClientViewModel cViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_activity);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        edLogin = findViewById(R.id.ed_login);
        edPassword = findViewById(R.id.ed_password);
        progressBar = findViewById(R.id.progressBar2);
        cViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        cViewModel.isLogged(SingInActivity.this, this);
        button_sign = findViewById(R.id.buttonSingIn);
        button_sign.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }

    private boolean isEmpty() {
        if (!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())) {
            return true;
        }
        Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSingIn && isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            button_sign.setEnabled(false);
            cViewModel.singIn(edLogin.getText().toString(), edPassword.getText().toString(), this, getApplicationContext())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        progressBar.setVisibility(View.GONE);
                        button_sign.setEnabled(true);
                    });
        }
    }

}

