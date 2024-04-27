package com.anggaa.projectmanagement.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectYellow

class ProjectAdapter(
    private val context: Context,
    private val projectList: ArrayList<Project>,
    private val taskList: List<Task>,
    private val listener: (Project) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val YELLOW = 1
        private const val BLUE = 2
        private const val GREEN = 3
        private const val RED = 4
        private const val PURPLE = 5
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

            projectStatus.text = project.card_color
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"

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

            projectStatus.text = project.card_color
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"

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

            projectStatus.text = project.card_color
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"

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

            projectStatus.text = project.card_color
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"

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

            projectStatus.text = project.card_color
            projectName.text = project.nama_proyek
            projectTaskSize.text = "${taskList.filter { it.id_proyek == project.project_id }.size} Task"

            view.setOnClickListener {
                listener(project)
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
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.card_project_purple, parent, false)
                ProjectPurpleViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder.itemViewType){
            YELLOW -> (viewHolder as ProjectYellowViewHolder).bind(projectList[position])
            BLUE -> (viewHolder as ProjectBlueViewHolder).bind(projectList[position])
            GREEN -> (viewHolder as ProjectGreenViewHolder).bind(projectList[position])
            RED -> (viewHolder as ProjectRedViewHolder).bind(projectList[position])
            else -> (viewHolder as ProjectPurpleViewHolder).bind(projectList[position])
        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (projectList[position].card_color) {
            "yellow" -> YELLOW
            "blue" -> BLUE
            "green" -> GREEN
            "red" -> RED
            else -> PURPLE
        }
    }
}