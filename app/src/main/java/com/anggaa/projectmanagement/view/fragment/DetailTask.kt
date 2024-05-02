package com.anggaa.projectmanagement.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.adapter.AllTaskAdapter
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.model.User
import com.anggaa.projectmanagement.view.project.detailTask.TambahTask
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailTask : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllTaskAdapter


    private lateinit var fab: FloatingActionButton

    companion object {
        private const val ARG_DATA_USER = "dataUser"
        private const val ARG_DATA_PROJECT = "dataProject"
        private const val ARG_DATA_LIST = "dataList"
        private const val ARG_PROJECT = "project"

        fun newInstance(ListTask: List<Task>, ListProject: List<Project>, ListUser: List<User>, project: Project): DetailTask {
            val fragment = DetailTask()
            val args = Bundle()
            args.putParcelableArrayList(ARG_DATA_LIST, ArrayList(ListTask))
            args.putParcelableArrayList(ARG_DATA_PROJECT, ArrayList(ListProject))
            args.putParcelableArrayList(ARG_DATA_USER, ArrayList(ListUser))
            args.putParcelable(ARG_PROJECT, project)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_detail_task, container, false)

        fab = view.findViewById(R.id.fab)

        val dataList = arguments?.getParcelableArrayList<Task>(ARG_DATA_LIST)
        val dataProject = arguments?.getParcelableArrayList<Project>(ARG_DATA_PROJECT)
        val dataUser = arguments?.getParcelableArrayList<User>(ARG_DATA_USER)
        val project = arguments?.getParcelable<Project>(ARG_PROJECT)

        fab.setOnClickListener {
            val intent = Intent(context, TambahTask::class.java)
            intent.putExtra("dataUser", dataUser)
            intent.putExtra("project", project)
            startActivity(intent)
        }

        if (dataList != null && project != null) {
            if (project.nama_proyek == "HOMESCREEN"){
                recyclerView = view.findViewById(R.id.RecyclerView)

                adapter = AllTaskAdapter(dataList, project, dataProject!!)

                recyclerView.layoutManager = LinearLayoutManager(requireContext())

                recyclerView.adapter = adapter
            } else {
                recyclerView = view.findViewById(R.id.RecyclerView)

                adapter = AllTaskAdapter(dataList.filter {
                    it.id_proyek == project.project_id
                }, project, dataProject!!)

                recyclerView.layoutManager = LinearLayoutManager(requireContext())

                recyclerView.adapter = adapter
            }
        } else {
            logData("ERROR", "Task Data unvailable in Fragment")
        }

        return view
    }

    private fun logData(tag: String, data: String) {
        Log.d(tag, "$tag: $data")
    }
}