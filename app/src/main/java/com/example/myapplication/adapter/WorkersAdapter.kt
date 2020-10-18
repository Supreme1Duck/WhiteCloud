package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.WorkerClass

import kotlinx.android.synthetic.main.worker_item_view.view.*

class WorkersAdapter(workerlist : ArrayList<WorkerClass>, context : Context) : RecyclerView.Adapter<WorkersAdapter.WorkerViewHolder>() {

    private var listWorkers = emptyList<WorkerClass>()
    var mContext : Context = context

    init {
        listWorkers = workerlist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        return WorkerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.worker_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val currentItem = listWorkers.get(position)
        if (currentItem.name.length>15) holder.itemView.text_name.textSize = 15F;
        holder.itemView.text_name.text = currentItem.name
        holder.itemView.text_age.text = currentItem.age
        holder.itemView.text_district.text = currentItem.district
        holder.itemView.text_phone_number.text = currentItem.phoneNumber
        holder.itemView.findViewById<LinearLayout>(R.id.main_linear_layout).setOnClickListener{
            Toast.makeText(mContext, "name = " + currentItem.name + "age = " + currentItem.age, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listWorkers.size
    }

    inner class WorkerViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview)

    fun setData(list: List<WorkerClass>){
        listWorkers = list
        notifyDataSetChanged()
    }
}