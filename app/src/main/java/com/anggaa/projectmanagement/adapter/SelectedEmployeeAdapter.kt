package com.anggaa.projectmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R

class SelectedEmployeeAdapter(private val selectedUserList: MutableList<String>) :
    RecyclerView.Adapter<SelectedEmployeeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selected_employee, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userName = selectedUserList[position]
        holder.bind(userName)
    }

    override fun getItemCount(): Int = selectedUserList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val buttonRemove: Button = itemView.findViewById(R.id.buttonRemove)

        fun bind(userName: String) {
            textViewName.text = userName
            buttonRemove.setOnClickListener {
                selectedUserList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }
}
