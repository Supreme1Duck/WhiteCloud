package com.example.myapplication.presenter

import android.content.Context
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.firebase.FirebaseWork

class Repository(private val firebaseWork: FirebaseWork){

    fun singIn(login: String, password: String, activity : AppCompatActivity, context: Context){
        firebaseWork.singIn(login,password,activity,context)
    }

    fun isLogged(activity: AppCompatActivity, context: Context){
        firebaseWork.isLogged(activity, context)
    }

    fun register(login: String, password: String, activity: AppCompatActivity, context: Context, progressBar: ProgressBar){
        firebaseWork.register(login, password, activity, context, progressBar)
    }

    fun saveWorker(email: String,name : String, age :String, district : String, phoneNumber : String){
        firebaseWork.saveTheWorker(email,name, age, district, phoneNumber)
    }

    fun saveClient(name : String, address:String, phoneNumber: String, district: String){
        firebaseWork.saveTheClient(name, address, phoneNumber, district)
    }

    fun deleteWorkerAccount(context: Context){
        firebaseWork.deleteTheUserAccount(context)
    }

    fun saveTheDistrict(district: String){
        firebaseWork.saveDistrict(district)
    }
}