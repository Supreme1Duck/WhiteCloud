package com.example.myapplication.presenter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.ClientsClass
import com.example.myapplication.data.WorkerClass
import com.example.myapplication.firebase.FirebaseWork
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class Repository(private val firebaseWork: FirebaseWork) {

    fun singIn(
        login: String,
        password: String,
        activity: AppCompatActivity,
        context: Context
    ): Completable {
        return firebaseWork.singIn(login, password, activity, context)
    }

    fun isLogged(activity: AppCompatActivity, context: Context) {
        firebaseWork.isLogged(activity, context)
    }

    fun register(login: String, password: String, activity: AppCompatActivity, context: Context) {
        firebaseWork.register(login, password, activity, context)
    }

    fun saveWorker(
        email: String,
        name: String,
        age: String,
        district: String,
        phoneNumber: String
    ): Completable {
        return firebaseWork.saveTheWorker(email, name, age, district, phoneNumber)
    }

    fun saveClient(
        name: String,
        address: String,
        phoneNumber: String,
        district: String,
        latitude: Double,
        longtitude: Double
    ) {
        firebaseWork.saveTheClient(name, address, phoneNumber, district, latitude, longtitude)
    }


    fun saveTheDistrict(district: String) {
        firebaseWork.saveDistrict(district)
    }

    fun workerChange(
        login: String,
        password: String,
        lastPassword: String,
        context: Context,
        int: Int,
        name: String,
        age: String,
        district: String,
        phoneNumber: String,
        adminsPassword: String
    ): Completable {
        return firebaseWork.workerChange(
            login,
            password,
            lastPassword,
            context,
            int,
            name,
            age,
            district,
            phoneNumber,
            adminsPassword
        )
    }

    fun getDataForAssistant(email: String, context: Context): Single<WorkerClass?> {
        return firebaseWork.getDataForAssistant(email, context)
    }

    fun loadClientsForAssistant(district: String): Single<ArrayList<ClientsClass>> {
        return firebaseWork.loadClientsForAssistant(district)
    }
}