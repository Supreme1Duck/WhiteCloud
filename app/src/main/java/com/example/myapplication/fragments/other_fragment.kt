package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.activities.other_fragment_activities.ClientsActivity
import com.example.myapplication.activities.other_fragment_activities.LogOutActivity
import com.example.myapplication.activities.other_fragment_activities.ManageActivity
import com.example.myapplication.activities.other_fragment_activities.WorkersActivity

class other_fragment : Fragment() {

    private lateinit var btn_workers: CardView
    private lateinit var btn_clients: CardView
    private lateinit var btn_login: CardView
    private lateinit var btn_manage: CardView
    private var intent: Intent = Intent()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_other_fragment, container, false)
        btn_workers = view.findViewById(R.id.workers_CardView)
        btn_clients = view.findViewById(R.id.clients_CardView)
        btn_login = view.findViewById(R.id.login_CardView)
        btn_manage = view.findViewById(R.id.login1_CardView)

        btn_workers.setOnClickListener {
            intent = Intent(requireContext(), WorkersActivity::class.java)
            startActivity(intent)
        }

        btn_clients.setOnClickListener {
            intent = Intent(requireContext(), ClientsActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            intent = Intent(requireContext(), LogOutActivity::class.java)
            startActivity(intent)
        }

        btn_manage.setOnClickListener {
            intent = Intent(requireContext(), ManageActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}