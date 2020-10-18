package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.ClientsClass
import com.example.myapplication.R
import kotlinx.android.synthetic.main.itemview.view.*

class ClientsAdapter : RecyclerView.Adapter<ClientsAdapter.ClientsHolder>() {
    private var listClients = emptyList<ClientsClass>()

    inner class ClientsHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsHolder {
        return ClientsHolder(LayoutInflater.from(parent.context).inflate(R.layout.itemview, parent, false))
    }

    override fun onBindViewHolder(holder: ClientsHolder, position: Int) {
        val currentItem = listClients.get(position)
        holder.itemView.text_id.text = (position+1).toString()
        holder.itemView.text_rayon.text = currentItem.district
        holder.itemView.text_address.text = currentItem.address
    }

    override fun getItemCount(): Int {
        return listClients.size
    }

    fun setData(clients: List<ClientsClass>){
        listClients = clients
        notifyDataSetChanged()
    }

}