package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.WorkersAdapter
import kotlinx.android.synthetic.main.fragment_home_fragment.view.*

class home_fragment : Fragment() {

    private lateinit var workerViewModel: ClientViewModel
    private lateinit var adapter : WorkersAdapter
    private var bundle : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workerViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        adapter = WorkersAdapter(workerViewModel.getWorkers().value!!, requireContext())
        workerViewModel.getWorkers().observe(this, {
            adapter.setData(it)
        })
        Toast.makeText(context, "Is shown", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.list_fragment_app_bar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        val recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        return view
    }
}