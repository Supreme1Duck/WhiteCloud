package com.example.myapplication.firebase

import android.content.Context
import android.content.Intent
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
    var WORKER_KEY: String
    var CLIENT_KEY: String
    var DISTRICT_KEY: String
    private var mWorkerBase: DatabaseReference
    private var mClientBase: DatabaseReference
    private var databaseAuth: FirebaseAuth
    var mDistrictBase: DatabaseReference

    init {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        databaseAuth = FirebaseAuth.getInstance()
        DISTRICT_KEY = "Districts"
        WORKER_KEY = "Worker"
        CLIENT_KEY = "Clients"
        mDistrictBase = FirebaseDatabase.getInstance().getReference(DISTRICT_KEY)
        mWorkerBase = FirebaseDatabase.getInstance().getReference(WORKER_KEY)
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
        databaseAuth.signInWithEmailAndPassword(
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

    fun workerChange(
        login: String,
        password: String,
        lastPassword: String,
        context: Context,
        int: Int,
        name: String,
        age: String,
        phoneNumber: String,
        district: String
    ) {
        val adminEmail = databaseAuth.currentUser!!.email
        databaseAuth.signInWithEmailAndPassword(
            login,
            lastPassword
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (login == "andrewduck1365@gmail.com")
                        Toast.makeText(context, "You are not a Worker", Toast.LENGTH_SHORT).show()
                    else {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                        val user = databaseAuth.currentUser
                        if (int == 1) {
                            if (login.isNotEmpty())
                                user!!.updateEmail(login).addOnCompleteListener {
                                    if (task.isSuccessful) {
                                        user.updatePassword(password).addOnCompleteListener {
                                            if (task.isSuccessful) {
                                                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT)
                                                    .show()
                                                val query =
                                                    mWorkerBase.orderByChild("email").equalTo(login)
                                                query.addListenerForSingleValueEvent(object :
                                                    ValueEventListener {
                                                    override fun onDataChange(snapshot: DataSnapshot) {
                                                        for (ds in snapshot.children)
                                                            ds.ref.setValue(null)
                                                    }

                                                    override fun onCancelled(error: DatabaseError) {
                                                        TODO("Not yet implemented")
                                                    }
                                                })

                                                saveTheWorker(
                                                    login,
                                                    name,
                                                    age,
                                                    district,
                                                    phoneNumber
                                                )

                                                databaseAuth.signOut()
                                                databaseAuth.signInWithEmailAndPassword(
                                                    adminEmail!!,
                                                    "SupremeDuck13"
                                                )
                                            }
                                        }
                                    }
                                }
                        }
                        if (int == 2) {
                            user!!.delete()
                            databaseAuth.signOut()
                            databaseAuth.signInWithEmailAndPassword(adminEmail!!, "SupremeDuck13")
                        }
                    }
                } else {
                    Toast.makeText(context, "Something wrong, try again", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    fun register(
        login: String,
        password: String,
        activity: AppCompatActivity,
        context: Context,
    ) {
        databaseAuth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Worker created", Toast.LENGTH_SHORT).show()
                    startActivity(context, Intent(activity, MainActivity::class.java), null)
                } else {
                    Toast.makeText(context, "Something wrong, try again", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun isLogged(activity: AppCompatActivity, context: Context) {
        if (databaseAuth.currentUser != null) {
            startActivity(context, Intent(activity, MainActivity::class.java), null)
        }
    }

    fun saveTheWorker(
        text_email: String,
        text_name: String,
        text_age: String,
        text_district: String,
        text_phoneNumber: String
    ) {
        val id = mWorkerBase.key
        val newWorker = WorkerClass(
            text_email,
            id!!,
            text_name,
            text_age,
            text_district,
            text_phoneNumber
        )
        mWorkerBase.push().setValue(newWorker)
    }

    fun saveTheClient(
        text_name: String,
        text_address: String,
        text_phone_number: String,
        text_district: String
    ) {
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

    fun signOut(context: Context) {
        databaseAuth.signOut()
        startActivity(context, Intent(context, SingInActivity::class.java), null)
    }


    fun saveDistrict(district: String) {
        val districtClass = DistrictClass(district)
        mDistrictBase.push().setValue(districtClass)
    }
}