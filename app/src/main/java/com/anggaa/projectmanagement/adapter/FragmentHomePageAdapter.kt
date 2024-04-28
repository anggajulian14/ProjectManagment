package com.anggaa.projectmanagement.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.view.fragment.AllTask
import com.anggaa.projectmanagement.view.fragment.DoneTask
import com.anggaa.projectmanagement.view.fragment.HomeAllTask
import com.anggaa.projectmanagement.view.fragment.HomeTask
import com.anggaa.projectmanagement.view.fragment.OngoingTask
import com.anggaa.projectmanagement.view.fragment.ToDoTask
import java.text.SimpleDateFormat
import java.util.Locale

class FragmentHomePageAdapter
    (fragmentManager: FragmentManager,
     lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var project: Project? = null
    private var ListProject: List<Project>? = null
    private var ListTask: List<Task>? = null

    constructor(fragmentManager: FragmentManager,
                lifecycle: Lifecycle,
                ListTask: List<Task>,
                project: Project,
                ListProject: List<Project>)
            : this(fragmentManager, lifecycle) {
        this.ListTask = ListTask
        this.ListProject = ListProject
        this.project = project
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        val filteredTasks = when (position) {
            0 -> ListTask!!
            1 -> ListTask!!.filter { it.status_tugas == "To Do" }
            2 -> ListTask!!.filter { it.status_tugas == "Ongoing" }
            else -> ListTask!!.filter { it.status_tugas == "Selesai" }
        }
        return HomeTask.newInstance(urutkanTugasBerdasarkanTanggal(filteredTasks), ListProject!!, project!!)
    }

    fun convertDateFormat(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }

    // Fungsi utama untuk mengurutkan daftar Tugas berdasarkan tanggal tenggat
    fun urutkanTugasBerdasarkanTanggal(tugasList: List<Task>): List<Task> {
        return tugasList.sortedBy { it.tanggal_selesai }
    }


    private fun logData(tag: String, data: String) {
        Log.d(tag, "$tag: $data")
    }
}