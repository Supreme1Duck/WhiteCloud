package com.example.myapplication.activities.other_fragment_activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.DistrictClass
import com.example.myapplication.fragments.ClientViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ManageActivity : AppCompatActivity() {
    private lateinit var buttonDelete: Button
    private lateinit var editDistrict: EditText
    private lateinit var spinner: Spinner
    private lateinit var dialog: AlertDialog.Builder
    private lateinit var cViewModel: ClientViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var arrayAdapter: ArrayAdapter<DistrictClass>
    private lateinit var btnDeleteWorker: Button
    private lateinit var editWorkerEmail: EditText
    private lateinit var editWorkerPassword: EditText
    private lateinit var editAdminsPassword: EditText
    private lateinit var progressBar: ProgressBar

    fun isEmpty(str1: String): Boolean {
        return str1.length != 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_activity)
        val buttonAdd = findViewById<Button>(R.id.button_add_manage_act)
        buttonDelete = findViewById(R.id.button_delete)
        spinner = findViewById(R.id.spin_manage_act)
        editDistrict = findViewById(R.id.edit_district_manage)
        toolbar = findViewById(R.id.app_bar_manage)
        progressBar = findViewById(R.id.progressBar3)
        btnDeleteWorker = findViewById(R.id.btnDeleteWorker)
        editWorkerEmail = findViewById(R.id.email_of_worker)
        editWorkerPassword = findViewById(R.id.worker_password)
        editAdminsPassword = findViewById(R.id.admins_password)
        setSupportActionBar(toolbar)
        dialog = AlertDialog.Builder(this)
        cViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        var districts = cViewModel.getDistricts().value
        if (districts != null) {
            arrayAdapter =
                ArrayAdapter<DistrictClass>(
                    this@ManageActivity,
                    R.layout.style_spinner_layout,
                    districts
                )
        }
        spinner.adapter = arrayAdapter
        cViewModel.getDistricts().observe(this, {
            districts = it
            arrayAdapter.notifyDataSetChanged()
        })

        buttonAdd.setOnClickListener {
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            if (isEmpty(editDistrict.text.toString())) {
                cViewModel.updateDistricts(editDistrict.text.toString().trim(), 1)
                editDistrict.setText("")
                Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@ManageActivity, "Empty fields", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDelete.setOnClickListener {
            val district = spinner.selectedItem.toString()
            dialog = AlertDialog.Builder(this)
            dialog.setTitle("Are you sure?")
            dialog.setMessage("You want to delete the district")
            dialog.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                cViewModel.updateDistricts(district, 2)
                Toast.makeText(this@ManageActivity, "Successfully deleted!", Toast.LENGTH_SHORT)
                    .show()
            }
            dialog.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

            val alertDialog: AlertDialog = dialog.create()
            alertDialog.show()
        }

        btnDeleteWorker.setOnClickListener {
            btnDeleteWorker.isEnabled = false
            progressBar.visibility = View.VISIBLE

            dialog = AlertDialog.Builder(this)
            dialog.setTitle("Are you sure?")
            dialog.setMessage("You want to delete the Worker")
            dialog.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                cViewModel.workerChange(
                    editWorkerEmail.text.toString(),
                    editWorkerPassword.text.toString(),
                    "",
                    this,
                    2,
                    "",
                    "",
                    "",
                    "",
                    editAdminsPassword.text.toString()
                ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        progressBar.visibility = View.GONE
                        btnDeleteWorker.isEnabled = true
                    }) {
                        it.printStackTrace()
                    }

                Toast.makeText(this@ManageActivity, "Successfully deleted!", Toast.LENGTH_SHORT)
                    .show()
            }
            dialog.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

            val alertDialog: AlertDialog = dialog.create()
            alertDialog.show()
        }
    }
}