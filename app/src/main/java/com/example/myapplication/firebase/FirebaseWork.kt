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
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

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

    fun singIn(
        login: String,
        password: String,
        activity: AppCompatActivity,
        context: Context
    ): Completable {
        return Completable.create {
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
                        if (login.trim().toLowerCase() == "andrewduck1365@gmail.com") {
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra("Email", login)
                            startActivity(context, intent, null)
                            it.onComplete()
                        } else {
                            it.onComplete()
                            val intent = Intent(activity, AssistantActivity::class.java)
                            intent.putExtra("Email", login)
                            startActivity(
                                context,
                                intent,
                                null
                            )
                        }
                    } else {
                        Toast.makeText(context, "Wrong fields", Toast.LENGTH_SHORT)
                            .show()
                        it.onComplete()
                    }
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
        district: String,
        adminsPassword: String
    ): Completable {
        return Completable.create { completable ->
            val adminEmail = databaseAuth.currentUser!!.email
            databaseAuth.signInWithEmailAndPassword(
                login,
                lastPassword
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (login == "andrewduck1365@gmail.com")
                            Toast.makeText(context, "You are not a Worker", Toast.LENGTH_SHORT)
                                .show()
                        else {
                            val user = databaseAuth.currentUser
                            if (int == 1) {
                                if (login.isNotEmpty())
                                    user!!.updateEmail(login).addOnCompleteListener {
                                        if (task.isSuccessful) {
                                            user.updatePassword(password).addOnCompleteListener {
                                                if (task.isSuccessful) {
                                                    Toast.makeText(
                                                        context,
                                                        "Done!",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                    val query =
                                                        mWorkerBase.orderByChild("email")
                                                            .equalTo(login)
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
                                                        adminsPassword
                                                    )
                                                    completable.onComplete()
                                                }
                                            }
                                        }
                                    }
                            }
                            if (int == 2) {
                                val query = mWorkerBase.orderByChild("email").equalTo(login)
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
                                user!!.delete()
                                databaseAuth.signOut()
                                databaseAuth.signInWithEmailAndPassword(
                                    adminEmail!!,
                                    adminsPassword
                                )
                                completable.onComplete()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Something wrong, try again", Toast.LENGTH_SHORT)
                            .show()
                        completable.onComplete()
                    }
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
            if (databaseAuth.currentUser!!.email == "andrewduck1365@gmail.com")
                startActivity(context, Intent(activity, MainActivity::class.java), null)
            else
                startActivity(context, Intent(activity, AssistantActivity::class.java), null)
        }
    }

    fun saveTheWorker(
        text_email: String,
        text_name: String,
        text_age: String,
        text_district: String,
        text_phoneNumber: String
    ): Completable {
        return Completable.create {
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
            it.onComplete()
        }
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

    fun getDataForAssistant(email: String, context: Context): Single<WorkerClass?> {
        var worker: WorkerClass? = null
        val query = mWorkerBase.orderByChild("email").equalTo(email)
        return Single.create {
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ns in snapshot.children) {
                        worker = ns.getValue(WorkerClass::class.java)!!
                        it.onSuccess(worker)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    fun loadClientsForAssistant(district: String): Single<ArrayList<ClientsClass>> {
        val listClients: ArrayList<ClientsClass> = ArrayList()
        val query = mClientBase.child(district).orderByChild("district").equalTo(district)
        return Single.create {
            query.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ns in snapshot.children) {
                        listClients.add(ns.getValue(ClientsClass::class.java)!!)
                    }
                    it.onSuccess(listClients)
                }

                override fun onCancelled(error: DatabaseError) {
                    it.onSuccess(null)
                }
            })
        }
    }

}