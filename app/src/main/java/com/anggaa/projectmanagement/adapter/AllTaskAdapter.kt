package com.anggaa.projectmanagement.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.view.project.detailTask.DetailTask
import java.text.SimpleDateFormat
import java.util.Locale

class AllTaskAdapter(
    private val taskList: List<Task>,
    private val project: Project,
    private val projectList: List<Project>
) : RecyclerView.Adapter<AllTaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.findViewById(R.id.TaskName)
        private val taskStatus: TextView = itemView.findViewById(R.id.TaskStatus)
        private val taskProject: TextView = itemView.findViewById(R.id.TaskProject)
        private val tanggalProject: TextView = itemView.findViewById(R.id.TaskDate)

        init {
            // Menambahkan OnClickListener ke itemView
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, DetailTask::class.java)
                    intent.putExtra("task", taskList[position])
                    intent.putExtra("project", project)
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(task: Task) {
            val x = projectList.firstOrNull { it.project_id == task.id_proyek }

            taskName.text = task.nama_tugas
            taskStatus.text = task.status_tugas
            taskProject.text = x?.nama_proyek ?: project.nama_proyek
            tanggalProject.text = formatDate(task.tanggal_selesai)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_task_yellow, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = taskList.size

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }
}
