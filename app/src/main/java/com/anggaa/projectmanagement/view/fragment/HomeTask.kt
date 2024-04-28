package com.anggaa.projectmanagement.view.fragment

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

class HomeTask : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllTaskAdapter

    companion object {
        private const val ARG_DATA_PROJECT = "dataProject"
        private const val ARG_DATA_LIST = "dataList"
        private const val ARG_PROJECT = "project"

        fun newInstance(ListTask: List<Task>, ListProject: List<Project>, project: Project): HomeTask {
            val fragment = HomeTask()
            val args = Bundle()
            args.putParcelableArrayList(ARG_DATA_LIST, ArrayList(ListTask))
            args.putParcelableArrayList(ARG_DATA_PROJECT, ArrayList(ListProject))
            args.putParcelable(ARG_PROJECT, project)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout fragment
        val view = inflater.inflate(R.layout.fragment_home_task, container, false)

        val dataList = arguments?.getParcelableArrayList<Task>(ARG_DATA_LIST)
        val dataProject = arguments?.getParcelableArrayList<Project>(ARG_DATA_PROJECT)
        val project = arguments?.getParcelable<Project>(ARG_PROJECT)

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