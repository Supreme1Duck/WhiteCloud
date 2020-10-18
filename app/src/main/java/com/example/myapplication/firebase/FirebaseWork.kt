package com.example.myapplication.firebase

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.myapplication.activities.AssistantActivity
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.activities.SingInActivity
import com.example.myapplication.data.ClientsClass
import com.example.myapplication.data.DistrictClass
import com.example.myapplication.data.WorkerClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseWork {
    var WORKER_KEY : String
    var CLIENT_KEY : String
    private var mDataBase: DatabaseReference
    private var mClientBase : DatabaseReference
    private var databaseInstance : FirebaseAuth

    init {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
         databaseInstance = FirebaseAuth.getInstance()
         WORKER_KEY = "Worker"
         CLIENT_KEY = "Clients"
         mDataBase = FirebaseDatabase.getInstance().getReference(WORKER_KEY)
         mClientBase = FirebaseDatabase.getInstance().getReference(CLIENT_KEY)
    }

    companion object {
        @Volatile
        private var INSTANCE: FirebaseWork? = null
        fun getInstance(): FirebaseWork {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = FirebaseWork()
                INSTANCE = instance
                return instance
            }
        }
    }

    fun singIn(login: String, password: String, activity: AppCompatActivity, context: Context) {
        databaseInstance.signInWithEmailAndPassword(
            login,
            password
        )
            .addOnCompleteListener(
                activity
            ) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Sing in Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (login == "andrewduck1365@gmail.com")
                        startActivity(context, Intent(activity, MainActivity::class.java), null)
                    else {
                        startActivity(
                            context,
                            Intent(activity, AssistantActivity::class.java),
                            null
                        )
                    }
                } else {
                    Toast.makeText(context, "Wrong fields", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    fun register(login: String, password: String, activity: AppCompatActivity, context: Context, progressBar : ProgressBar) {
        progressBar.visibility = View.VISIBLE
        databaseInstance.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(context, "Worker created", Toast.LENGTH_SHORT).show()
                    startActivity(context, Intent(activity, MainActivity::class.java), null)
                } else {
                    Toast.makeText(context, "Something wrong, try again", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun isLogged(activity: AppCompatActivity, context: Context) {
        if (databaseInstance.currentUser != null) {
            startActivity(context, Intent(activity, MainActivity::class.java), null)
        }
    }

    fun saveTheWorker(
        text_email : String,
        text_name: String,
        text_age: String,
        text_district: String,
        text_phoneNumber: String
    ) {
        val id = mDataBase.key
        val email = text_email
        val name = text_name
        val age = text_age
        val district = text_district
        val phoneNumber = text_phoneNumber
        val newWorker = WorkerClass(
            email,
            id!!,
            name,
            age,
            district,
            phoneNumber
        )
        mDataBase.push().setValue(newWorker)
    }

    fun saveTheClient(text_name: String, text_address: String, text_phone_number : String, text_district: String) {
        val id = mClientBase.key
        val name = text_name
        val address = text_address
        val district = text_district
        val newClient = ClientsClass(
            id!!,
            name,
            address,
            text_phone_number,
            district
        )
        val mClientChildDatabase = mClientBase.child(district)
        mClientChildDatabase.push().setValue(newClient)
    }

    fun signOut(context: Context){
        databaseInstance.signOut()
        startActivity(context,Intent(context, SingInActivity::class.java), null)
    }

    fun deleteTheUserAccount(context: Context){
        databaseInstance
            .currentUser!!.
            delete().
            addOnCompleteListener {
                Toast.makeText(context, "The user has been deleted", Toast.LENGTH_SHORT).show()
            }
    }

    fun saveDistrict(district : String){
        val districtClass = DistrictClass(district)
        mClientBase.push().setValue(districtClass)
    }

    fun deleteTheDistrict(district: String){
        mClientBase.child(district).removeValue()
    }
}