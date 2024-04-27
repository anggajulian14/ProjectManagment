package com.anggaa.projectmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.model.Task

class ProjectTaskAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<ProjectTaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.TaskName)
        val taskStatus: TextView = itemView.findViewById(R.id.TaskStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_task_yellow, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.taskName.text = currentItem.nama_tugas
        holder.taskStatus.text = currentItem.status_tugas
    }
}