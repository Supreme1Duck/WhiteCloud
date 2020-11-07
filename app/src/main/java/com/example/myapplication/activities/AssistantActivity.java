package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MapboxMap;
import com.example.myapplication.R;
import com.example.myapplication.customViews.ProgressButton;
import com.example.myapplication.data.ClientsClass;
import com.example.myapplication.data.WorkerClass;
import com.example.myapplication.fragments.ClientViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AssistantActivity extends AppCompatActivity {
    private View view1;
    private TextView currentTime;
    private String email;
    private TextView name, district;
    private ClientViewModel aViewModel;
    private WorkerClass worker;
    private ProgressButton button;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<Double> list_latitude = new ArrayList<>();
    private ArrayList<Double> list_longtitude = new ArrayList<>();
    private Handler h;
    private ProgressBar progressBar;
    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_activity);
        view1 = findViewById(R.id.btn_start);
        name = findViewById(R.id.text_name_worker);
        progressBar = findViewById(R.id.progressBar3);
        image = findViewById(R.id.image_view);
        district = findViewById(R.id.text_district_worker);
        aViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        list = new ArrayList<>();
        currentTime = findViewById(R.id.currentTime);
        button = new ProgressButton(getBaseContext(), view1);
        view1.setEnabled(false);
        completeData();
        h = new Handler(message -> {
            if (message.what == 1) {
                Intent intent = new Intent(this, MapboxMap.class);
                Toast.makeText(this, "Email = " + list.size(), Toast.LENGTH_SHORT).show();
                intent.putExtra("list", list);
                intent.putExtra("latitude", list_latitude);
                intent.putExtra("longitude", list_longtitude);
                startActivity(intent);
            }
            if (message.what == 2) {
                Toast.makeText(this, "No data, please add", Toast.LENGTH_SHORT).show();
                button.buttonFinished(this);
            }
            return true;
        });

        view1.setOnClickListener(view -> {
            button.buttonActivated();
            onCompleteClientsData();
        });
    }

    private Completable load() {
        return Completable.create(emitter ->
                aViewModel.loadClientsForAssistant(worker.getDistrict())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<ArrayList<ClientsClass>>() {

                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull ArrayList<ClientsClass> clientsClasses) {
                                if (clientsClasses.size() == 0) {
                                    h.sendEmptyMessage(2);
                                    return;
                                }
                                list.clear();
                                list_latitude.clear();
                                list_longtitude.clear();
                                for (ClientsClass arr : clientsClasses) {
                                    list.add(arr.getAddress());
                                    list_latitude.add(arr.getLatitude());
                                    list_longtitude.add(arr.getLongtitude());
                                }
                                emitter.onComplete();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        }));

    }

    private Completable getDataForAssistant() {
        return Completable.create(emitter ->
                aViewModel.getDataForAssistant(email, this)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<WorkerClass>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onSuccess(@NonNull WorkerClass workerClass) {
                                worker = workerClass;
                                Toast.makeText(AssistantActivity.this, "worker = " + worker.getEmail(), Toast.LENGTH_SHORT).show();
                                emitter.onComplete();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                emitter.onComplete();
                            }
                        })
        );
    }

    private void onCompleteClientsData() {
        load().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(AssistantActivity.this, "list = " + list.get(0), Toast.LENGTH_SHORT).show();
                button.buttonFinished(AssistantActivity.this);
                h.sendEmptyMessage(1);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    private void completeData() {
        getDataForAssistant()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        view1.setEnabled(true);
                        image.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
