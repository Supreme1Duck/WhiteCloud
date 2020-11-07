package com.example.myapplication.presenter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.ClientsClass;
import com.example.myapplication.data.DistrictClass;
import com.example.myapplication.data.WorkerClass;
import com.example.myapplication.firebase.FirebaseWork;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Repository2 {
    private static Repository2 instance;
    private ArrayList<WorkerClass> workers = new ArrayList<>();
    private ArrayList<ClientsClass> clients = new ArrayList<>();
    private MutableLiveData<ArrayList<WorkerClass>> data = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ClientsClass>> clients_data = new MutableLiveData<>();
    private ArrayList<DistrictClass> districts = new ArrayList<>();
    private MutableLiveData<ArrayList<DistrictClass>> liveData_districts = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> emailsData = new MutableLiveData<>();
    private ArrayList<String> emails = new ArrayList<>();

    public static Repository2 getInstance() {
        if (instance == null)
            instance = new Repository2();
        return instance;
    }

    public MutableLiveData<ArrayList<String>> getEmails() {
        loadEmails();

        emailsData.setValue(emails);

        return emailsData;
    }

    public void loadEmails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Worker").orderByChild("Email");

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ns : snapshot.getChildren())
                    emails.add(ns.getValue().toString());
                emailsData.postValue(emails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<ArrayList<WorkerClass>> getData() {
        loadWorkers();

        data.setValue(workers);

        return data;
    }

    private void loadWorkers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Worker");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workers.clear();
                for (DataSnapshot ns : snapshot.getChildren()) {
                    workers.add(ns.getValue(WorkerClass.class));
                }
                data.postValue(workers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<ArrayList<ClientsClass>> getClients() {
        loadClients();

        clients_data.setValue(clients);

        return clients_data;
    }

    private void loadClients() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Clients");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clients.clear();
                for (DataSnapshot ns : snapshot.getChildren()) {
                    for (DataSnapshot ne : ns.getChildren()) {
                        clients.add(ne.getValue(ClientsClass.class));
                    }
                }
                clients_data.postValue(clients);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadDistricts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Districts");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                districts.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    districts.add(ds.getValue(DistrictClass.class));
                }
                liveData_districts.postValue(districts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<ArrayList<DistrictClass>> getDistricts() {
        loadDistricts();
        liveData_districts.setValue(districts);
        return liveData_districts;
    }

    public void deleteDistrict(String district) {
        Query districtQuery = FirebaseWork.Companion.getInstance().getMDistrictBase().orderByChild("district").equalTo(district);

        districtQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
