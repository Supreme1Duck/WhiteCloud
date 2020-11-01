package com.example.myapplication.activities.other_fragment_activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.fragments.ClientViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class UpdateWorker : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edAge: EditText
    private lateinit var edDistrict: EditText
    private lateinit var edPhonenumber: EditText
    private lateinit var edPassword: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnUpdate: Button
    private lateinit var clientViewModel: ClientViewModel
    private lateinit var edLastPassword: EditText
    private lateinit var edAdminsPassword: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_worker_activity)
        edName = findViewById(R.id.text_name_change)
        edAge = findViewById(R.id.text_age_change)
        edDistrict = findViewById(R.id.text_district_change)
        edPhonenumber = findViewById(R.id.text_phone_number_change)
        edPassword = findViewById(R.id.text_password_change)
        edLastPassword = findViewById(R.id.text_last_password_change)
        edEmail = findViewById(R.id.text_email_change)
        progressBar = findViewById(R.id.progressBar3)
        btnUpdate = findViewById(R.id.btn_udpate_change)
        edAdminsPassword = findViewById(R.id.text_admins_password_change)
        clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        btnUpdate.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            if (isEmpty()) {
                clientViewModel.workerChange(
                    edEmail.text.toString().toLowerCase(Locale.getDefault()),
                    edPassword.text.toString(),
                    edLastPassword.text.toString(),
                    this,
                    1,
                    edName.text.toString(),
                    edAge.text.toString(),
                    edDistrict.text.toString(),
                    edPhonenumber.text.toString(),
                    edAdminsPassword.text.toString()
                ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        progressBar.visibility = View.GONE
                    }
            }
        }
    }

    private fun isEmpty(): Boolean {
        if (!TextUtils.isEmpty(
                edName.text.toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                edLastPassword.text.toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                edPassword.text.toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                edDistrict.text.toString().trim { it <= ' ' })
            && !TextUtils.isEmpty(
                edPhonenumber.text.toString().trim { it <= ' ' }) && !TextUtils.isEmpty(
                edEmail.text.toString().toLowerCase().trim { it <= ' ' }) && !TextUtils.isEmpty(
                edAge.text.toString().trim { it <= ' ' })
        ) {
            return true
        }
        Toast.makeText(applicationContext, "Empty fields", Toast.LENGTH_SHORT).show()
        return false
    }
}