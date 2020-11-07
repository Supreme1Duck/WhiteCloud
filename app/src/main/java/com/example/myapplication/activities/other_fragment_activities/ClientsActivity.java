package com.example.myapplication.activities.other_fragment_activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.customViews.ProgressButton;
import com.example.myapplication.data.DistrictClass;
import com.example.myapplication.fragments.ClientViewModel;

import java.util.ArrayList;

public class ClientsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_address, ed_name, ed_phoneNumber, ed_latitude, ed_longtitude;
    private Spinner spin_disctrict;
    private ClientViewModel cViewModel;
    private View btn_add;
    private ArrayList<DistrictClass> districts;
    private ArrayAdapter<DistrictClass> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_clients_activity);
        Toolbar toolbar = findViewById(R.id.clients_app_bar);
        ed_address = findViewById(R.id.text_address_add_clietns);
        ed_name = findViewById(R.id.text_name_add_clietns);
        ed_phoneNumber = findViewById(R.id.text_phone_number_add_clietns);
        ed_latitude = findViewById(R.id.text_latitude);
        ed_longtitude = findViewById(R.id.text_longtitude);
        spin_disctrict = findViewById(R.id.clients_spinner);
        cViewModel = new ClientViewModel(getApplication());
        toolbar.setTitle("Add a client");
        setSupportActionBar(toolbar);
        btn_add = findViewById(R.id.btn_add_client);
        districts = cViewModel.getDistricts().getValue();
        if (districts != null) {
            arrayAdapter = new ArrayAdapter(this, R.layout.style_spinner_layout, districts);
        }
        cViewModel.getDistricts().observe(this, strings -> {
            districts = strings;
            arrayAdapter.notifyDataSetChanged();
        });
        spin_disctrict.setAdapter(arrayAdapter);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_client) {
            animate();
            if (isEmpty()) {
                cViewModel.saveTheClient(
                        ed_name.getText().toString().trim(),
                        ed_address.getText().toString().trim(),
                        ed_phoneNumber.getText().toString().trim(),
                        spin_disctrict.getSelectedItem().toString(),
                        Double.parseDouble(ed_latitude.getText().toString()),
                        Double.parseDouble(ed_longtitude.getText().toString()));
            }
        }
    }

    private void animate() {
        ProgressButton button = new ProgressButton(getBaseContext(), btn_add);
        button.buttonActivated();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            button.buttonFinished(ClientsActivity.this);
        }, 1500);
    }

    private boolean isEmpty() {
        if (!TextUtils.isEmpty(ed_name.getText().toString().trim())
                && !TextUtils.isEmpty(ed_address.getText().toString().trim())
                && !TextUtils.isEmpty(ed_phoneNumber.getText().toString().trim())) {
            return true;
        }
        Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT).show();
        return false;
    }
}
