package com.example.myapplication.fragments

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ClientsClass
import com.example.myapplication.data.DistrictClass
import com.example.myapplication.data.WorkerClass
import com.example.myapplication.firebase.FirebaseWork
import com.example.myapplication.presenter.Repository
import com.example.myapplication.presenter.Repository2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    private var workerData = MutableLiveData<ArrayList<WorkerClass>>()
    private var clientsData = MutableLiveData<ArrayList<ClientsClass>>()
    private var districtsData = MutableLiveData<ArrayList<DistrictClass>>()
    private var firebaseWork: FirebaseWork
    private var repository2: Repository2

    fun getClients(): MutableLiveData<ArrayList<ClientsClass>> {
        return clientsData
    }

    fun getDistricts(): MutableLiveData<ArrayList<DistrictClass>> {
        return districtsData
    }

    fun updateDistricts(district: String, wht: Int) {
        if (wht == 1)
            viewModelScope.launch(Dispatchers.IO) {
                repository.saveTheDistrict(district)
            }
        if (wht == 2)
            viewModelScope.launch(Dispatchers.IO) {
                repository2.deleteDistrict(district)
            }
    }

    init {
        firebaseWork = FirebaseWork.getInstance()
        repository2 = Repository2.getInstance()
        repository = Repository(firebaseWork)
        workerData = repository2.data
        clientsData = repository2.clients
        districtsData = repository2.districts
    }

    fun getWorkers(): LiveData<ArrayList<WorkerClass>> {
        return workerData
    }

    fun singIn(login: String, password: String, activity: AppCompatActivity, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.singIn(login, password, activity, context)
        }
    }

    fun isLogged(activity: AppCompatActivity, context: Context) {
        repository.isLogged(activity, context)
    }

    fun register(
        login: String,
        password: String,
        activity: AppCompatActivity,
        context: Context,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.register(login, password, activity, context)
        }
    }

    fun saveWorker(
        email: String,
        name: String,
        age: String,
        district: String,
        phoneNumber: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveWorker(email, name, age, district, phoneNumber)
        }
    }

    fun saveTheClient(name: String, address: String, phoneNumber: String, district: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveClient(name, address, phoneNumber, district)
        }
    }

    fun logout(context: Context) {
        firebaseWork.signOut(context)
    }

    fun workerChange(
        login: String,
        lastPassword: String,
        password: String,
        context: Context,
        int: Int,
        name: String,
        age: String,
        district: String,
        phoneNumber: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.workerChange(
                login,
                password,
                lastPassword,
                context,
                int,
                name,
                age,
                district,
                phoneNumber
            )
        }
    }
}