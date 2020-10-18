package com.example.myapplication.activities.other_fragment_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.activities.SingInActivity;
import com.example.myapplication.fragments.ClientViewModel;

public class LogOutActivity extends AppCompatActivity {
    private ClientViewModel cViewModel;
    private AlertDialog.Builder dialog;
    private ProgressBar bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_activity);
        Button btn_logout = findViewById(R.id.btn_logout);
        bar = findViewById(R.id.progressBar_logout);
        cViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        btn_logout.setOnClickListener(view -> {
                dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("You will be returned back sing in");
                dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                    bar.setVisibility(View.VISIBLE);
                    cViewModel.logout(LogOutActivity.this);
                    startActivity(new Intent(LogOutActivity.this, SingInActivity.class));
                    bar.setVisibility(View.GONE);
                });
                dialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
        });
    }
}
