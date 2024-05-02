package com.anggaa.projectmanagement.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.model.User
import com.anggaa.projectmanagement.view.fragment.AllTask
import com.anggaa.projectmanagement.view.fragment.DetailTask
import com.anggaa.projectmanagement.view.fragment.DoneTask
import com.anggaa.projectmanagement.view.fragment.OngoingTask
import com.anggaa.projectmanagement.view.fragment.ToDoTask

class FragmentPageAdapter
    (fragmentManager: FragmentManager,
     lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

         private var project: Project? = null
         private var ListUser: List<User>? = null
         private var ListProject: List<Project>? = null
         private var ListTask: List<Task>? = null

    constructor(fragmentManager: FragmentManager,
                lifecycle: Lifecycle,
                ListTask: List<Task>,
                ListUser: List<User>,
                project: Project,
                ListProject: List<Project>)
            : this(fragmentManager, lifecycle) {
                this.ListTask = ListTask
                this.ListUser = ListUser
                this.ListProject = ListProject
                this.project = project
            }

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailTask.newInstance(ListTask!!, ListProject!!,ListUser!!, project!!)
            1 -> DetailTask.newInstance(ListTask!!.filter {
                it.status_tugas == "To Do"
            },ListProject!!,ListUser!! ,project!!)
            2 -> DetailTask.newInstance(ListTask!!.filter {
                it.status_tugas == "Ongoing"
            },ListProject!!,ListUser!! ,project!!)
            else -> DetailTask.newInstance(ListTask!!.filter {
                it.status_tugas == "Selesai"
            },ListProject!!,ListUser!! ,project!!)
        }
    }

    private fun logData(tag: String, data: String) {
        Log.d(tag, "$tag: $data")
    }
}