package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ClientsAdapter
import kotlinx.android.synthetic.main.fragment_list_fragment.view.*

class list_fragment : Fragment() {

    private lateinit var clientViewModel: ClientViewModel
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_fragment, container, false)
        val adapter = ClientsAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        clientViewModel.getClients().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        toolbar = view.findViewById(R.id.app_bar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)

        return view
    }

}