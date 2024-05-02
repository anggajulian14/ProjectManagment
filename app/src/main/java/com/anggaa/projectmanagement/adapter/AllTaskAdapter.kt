package com.anggaa.projectmanagement.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.view.project.detailTask.DetailTaskYellow
import java.text.SimpleDateFormat
import java.util.Locale

class AllTaskAdapter(
    private val taskList: List<Task>,
    private val project: Project,
    private val projectList: List<Project>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val YELLOW = 1
        private const val BLUE = 2
        private const val GREEN = 3
        private const val RED = 4
        private const val PURPLE = 5
    }

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
                    val intent = Intent(itemView.context, DetailTaskYellow::class.java)
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

    inner class YellowTask(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val taskName: TextView = itemView.findViewById(R.id.TaskName)
        private val taskStatus: TextView = itemView.findViewById(R.id.TaskStatus)
        private val taskProject: TextView = itemView.findViewById(R.id.TaskProject)
        private val tanggalProject: TextView = itemView.findViewById(R.id.TaskDate)

        init {
            // Menambahkan OnClickListener ke itemView
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, DetailTaskYellow::class.java)
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

    inner class BlueTask(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.findViewById(R.id.TaskName)
        private val taskStatus: TextView = itemView.findViewById(R.id.TaskStatus)
        private val taskProject: TextView = itemView.findViewById(R.id.TaskProject)
        private val tanggalProject: TextView = itemView.findViewById(R.id.TaskDate)

        init {
            // Menambahkan OnClickListener ke itemView
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, DetailTaskYellow::class.java)
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

    inner class GreenTask(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.findViewById(R.id.TaskName)
        private val taskStatus: TextView = itemView.findViewById(R.id.TaskStatus)
        private val taskProject: TextView = itemView.findViewById(R.id.TaskProject)
        private val tanggalProject: TextView = itemView.findViewById(R.id.TaskDate)

        init {
            // Menambahkan OnClickListener ke itemView
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, DetailTaskYellow::class.java)
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

    inner class RedTask(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.findViewById(R.id.TaskName)
        private val taskStatus: TextView = itemView.findViewById(R.id.TaskStatus)
        private val taskProject: TextView = itemView.findViewById(R.id.TaskProject)
        private val tanggalProject: TextView = itemView.findViewById(R.id.TaskDate)

        init {
            // Menambahkan OnClickListener ke itemView
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, DetailTaskYellow::class.java)
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

    inner class PurpleTask(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.findViewById(R.id.TaskName)
        private val taskStatus: TextView = itemView.findViewById(R.id.TaskStatus)
        private val taskProject: TextView = itemView.findViewById(R.id.TaskProject)
        private val tanggalProject: TextView = itemView.findViewById(R.id.TaskDate)

        init {
            // Menambahkan OnClickListener ke itemView
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, DetailTaskYellow::class.java)
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

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when(position){
            YELLOW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_task_yellow, parent, false)
                YellowTask(view)
            }
            BLUE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_task_blue, parent, false)
                BlueTask(view)
            }
            GREEN -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_task_green, parent, false)
                GreenTask(view)
            }
            RED -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_task_red, parent, false)
                RedTask(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_task_purple, parent, false)
                PurpleTask(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = taskList[position]
        when(holder.itemViewType){
            YELLOW -> (holder as YellowTask).bind(currentItem)
            BLUE -> (holder as BlueTask).bind(currentItem)
            GREEN -> (holder as GreenTask).bind(currentItem)
            RED -> (holder as RedTask).bind(currentItem)
            else -> (holder as PurpleTask).bind(currentItem)
            }
    }

    override fun getItemCount() = taskList.size

    override fun getItemViewType(position: Int): Int {
        val currentItem = taskList[position]
        val project = projectList.filter { it.project_id == currentItem.id_proyek }
        return when (project[0].card_color) {
            "yellow" -> YELLOW
            "blue" -> BLUE
            "green" -> GREEN
            "red" -> RED
            else -> PURPLE
        }
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }
}
