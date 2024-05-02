package com.anggaa.projectmanagement.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.view.project.detailProject.AllProject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ProjectAdapter(
    private val context: Context,
    private val projectList: List<Project>,
    private val taskList: List<Task>,
    private val listener: (Project) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val YELLOW = 1
        private const val BLUE = 2
        private const val GREEN = 3
        private const val RED = 4
        private const val PURPLE = 5
        private const val WHITE = 6
    }

    class ProjectViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view){
        val projectStatus = view.findViewById<TextView>(R.id.ProjectStatus)
        val projectName = view.findViewById<TextView>(R.id.ProjectName)
        val projectTaskSize = view.findViewById<TextView>(R.id.ProjectTask)
    }

    inner class ProjectYellowViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view){
        fun bind(project: Project){
            val projectStatus = view.findViewById<TextView>(R.id.ProjectStatus)
            val projectName = view.findViewById<TextView>(R.id.ProjectName)
            val projectTaskSize = view.findViewById<TextView>(R.id.ProjectTask)
            val tenggatProject = view.findViewById<TextView>(R.id.TenggatProject)

            projectStatus.text = project.status_proyek
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"
            tenggatProject.text = hitungTenggat(project.tanggal_selesai)

            view.setOnClickListener {
                listener(project)
            }
        }
    }

    inner class ProjectBlueViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view){
        fun bind(project: Project){
            val projectStatus = view.findViewById<TextView>(R.id.ProjectStatus)
            val projectName = view.findViewById<TextView>(R.id.ProjectName)
            val projectTaskSize = view.findViewById<TextView>(R.id.ProjectTask)
            val tenggatProject = view.findViewById<TextView>(R.id.TenggatProject)

            projectStatus.text = project.status_proyek
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"
            tenggatProject.text = hitungTenggat(project.tanggal_selesai)

            view.setOnClickListener {
                listener(project)
            }
        }
    }

    inner class ProjectGreenViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view){
        fun bind(project: Project){
            val projectStatus = view.findViewById<TextView>(R.id.ProjectStatus)
            val projectName = view.findViewById<TextView>(R.id.ProjectName)
            val projectTaskSize = view.findViewById<TextView>(R.id.ProjectTask)
            val tenggatProject = view.findViewById<TextView>(R.id.TenggatProject)

            projectStatus.text = project.status_proyek
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"
            tenggatProject.text = hitungTenggat(project.tanggal_selesai)

            view.setOnClickListener {
                listener(project)
            }
        }
    }

    inner class ProjectRedViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view){
        fun bind(project: Project){
            val projectStatus = view.findViewById<TextView>(R.id.ProjectStatus)
            val projectName = view.findViewById<TextView>(R.id.ProjectName)
            val projectTaskSize = view.findViewById<TextView>(R.id.ProjectTask)
            val tenggatProject = view.findViewById<TextView>(R.id.TenggatProject)

            projectStatus.text = project.status_proyek
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"
            tenggatProject.text = hitungTenggat(project.tanggal_selesai)

            view.setOnClickListener {
                listener(project)
            }
        }
    }

    inner class ProjectPurpleViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view){
        fun bind(project: Project){
            val projectStatus = view.findViewById<TextView>(R.id.ProjectStatus)
            val projectName = view.findViewById<TextView>(R.id.ProjectName)
            val projectTaskSize = view.findViewById<TextView>(R.id.ProjectTask)
            val tenggatProject = view.findViewById<TextView>(R.id.TenggatProject)

            projectStatus.text = project.status_proyek
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"
            tenggatProject.text = hitungTenggat(project.tanggal_selesai)

            view.setOnClickListener {
                listener(project)
            }
        }
    }

    inner class moreProjectViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view){
        fun bind(project: Project){
            val moreButton = view.findViewById<Button>(R.id.MoreProject)

            moreButton.setOnClickListener {
                val intent = Intent(context, AllProject::class.java)
                startActivity(context, intent, null)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when(position){
            YELLOW -> {
                val view = LayoutInflater.from(context).inflate(R.layout.card_project_yellow, parent, false)
                ProjectYellowViewHolder(view)
            }
            BLUE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.card_project_blue, parent, false)
                ProjectBlueViewHolder(view)
            }
            GREEN -> {
                val view = LayoutInflater.from(context).inflate(R.layout.card_project_green, parent, false)
                ProjectGreenViewHolder(view)
            }
            RED -> {
                val view = LayoutInflater.from(context).inflate(R.layout.card_project_red, parent, false)
                ProjectRedViewHolder(view)
            }
            WHITE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.card_project_white, parent, false)
                moreProjectViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.card_project_purple, parent, false)
                ProjectPurpleViewHolder(view)
            }
        }
    }

    fun hitungTenggat(deadlineString: String): String {
        // Mengubah string deadline ke dalam format tanggal yang dapat diproses
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
        val deadlineDate: Date = sdf.parse(deadlineString)!!

        // Mendapatkan tanggal hari ini
        val currentDate = Calendar.getInstance().time

        // Menghitung selisih hari antara tanggal hari ini dan deadline
        val diffInMillies: Long = deadlineDate.time - currentDate.time
        val diffInDays: Long = diffInMillies / (1000 * 60 * 60 * 24)

        // Mengembalikan string informasi tenggat
        return "Tenggat $diffInDays hari lagi"
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder.itemViewType){
            YELLOW -> (viewHolder as ProjectYellowViewHolder).bind(projectList[position])
            BLUE -> (viewHolder as ProjectBlueViewHolder).bind(projectList[position])
            GREEN -> (viewHolder as ProjectGreenViewHolder).bind(projectList[position])
            RED -> (viewHolder as ProjectRedViewHolder).bind(projectList[position])
            WHITE -> (viewHolder as moreProjectViewHolder).bind(projectList[position])
            else -> (viewHolder as ProjectPurpleViewHolder).bind(projectList[position])
        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position > 4){
            WHITE
        }else{
            when(projectList[position].card_color){
                "yellow" -> YELLOW
                "blue" -> BLUE
                "green" -> GREEN
                "red" -> RED
                else -> PURPLE
            }
        }
    }
}