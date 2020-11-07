package com.example.myapplication.activities.other_fragment_activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.fragments.ClientViewModel;

import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkersActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed_name, ed_age, ed_district, ed_phonenumber, ed_password, ed_email;
    private ClientViewModel clientViewModel;
    private ProgressBar progressBar;
    private Button button_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workers_activity);
        Toolbar toolbar = findViewById(R.id.worker_appbar);
        toolbar.setTitle("Add a worker");
        setSupportActionBar(toolbar);
        clientViewModel = new ClientViewModel(getApplication());
        ed_name = findViewById(R.id.text_name);
        ed_age = findViewById(R.id.text_age);
        progressBar = findViewById(R.id.progressBar3);
        ed_district = findViewById(R.id.text_district);
        ed_phonenumber = findViewById(R.id.text_phone_number);
        ed_password = findViewById(R.id.text_password);
        ed_email = findViewById(R.id.text_email);
        button_add = findViewById(R.id.btn_add);
        button_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            button_add.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            if (isEmpty()) {
                if (ifSize(ed_phonenumber.getText().toString(), ed_age.getText().toString(), ed_district.getText().toString())) {
                    clientViewModel.register(ed_email.getText().toString().trim().toLowerCase(Locale.getDefault()), ed_password.getText().toString().trim(), this, getBaseContext());
                    clientViewModel.saveWorker(ed_email.getText().toString().trim().toLowerCase(Locale.getDefault()),
                            ed_name.getText().toString().trim(),
                            ed_age.getText().toString().trim(),
                            ed_district.getText().toString().trim(),
                            ed_phonenumber.getText().toString().trim()).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    progressBar.setVisibility(View.GONE);
                                    button_add.setEnabled(true);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }
                            });
                    ed_name.setText("");
                    ed_age.setText("");
                    ed_district.setText("");
                    ed_phonenumber.setText("");
                    ed_password.setText("");
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, "Some fields are too big, or empty", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private boolean isEmpty() {
        if (!TextUtils.isEmpty(ed_name.getText().toString().trim()) && !TextUtils.isEmpty(ed_password.getText().toString().trim()) && !TextUtils.isEmpty(ed_district.getText().toString().trim())
                && !TextUtils.isEmpty(ed_phonenumber.getText().toString().trim()) && !TextUtils.isEmpty(ed_age.getText().toString().trim())) {
            return true;
        }
        Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        return false;
    }

    private boolean ifSize(String s1, String s2, String s3) {
        return s1.length() < 14 && s2.length() < 3 && s3.length() < 17;
    }

}
