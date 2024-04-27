package com.anggaa.projectmanagement.view.home

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.adapter.FragmentHomePageAdapter
import com.anggaa.projectmanagement.adapter.FragmentPageAdapter
import com.anggaa.projectmanagement.adapter.ProjectAdapter
import com.anggaa.projectmanagement.model.Laporan
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.model.User
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectBlue
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectGreen
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectPurple
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectRed
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectYellow
import com.google.android.material.tabs.TabLayout

class HomeScreen : AppCompatActivity() {

    private lateinit var ProjectAdapter: ProjectAdapter
    private lateinit var ProjectRecyclerView: RecyclerView

    private lateinit var TabLayout: TabLayout
    private lateinit var ViewPager: ViewPager2
    private lateinit var FragmentManager: FragmentHomePageAdapter

    private lateinit var ListUser: ArrayList<User>
    private lateinit var ListProject: ArrayList<Project>
    private lateinit var ListTask: ArrayList<Task>
    private lateinit var ListLaporan: ArrayList<Laporan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ListUser = intent.getParcelableArrayListExtra<User>("userList")!!
        ListProject = intent.getParcelableArrayListExtra<Project>("projectList")!!
        ListTask = intent.getParcelableArrayListExtra<Task>("taskList")!!
        ListLaporan = intent.getParcelableArrayListExtra<Laporan>("laporanList")!!

        logData(
                "DATA LIST",
                "List User : $ListUser\nList Project : $ListProject\nList Task : $ListTask\nList Laporan : $ListLaporan"
        )

        initView()

    }

        private fun initView() {
            ProjectRecyclerView = findViewById<RecyclerView>(R.id.listProject)
            ProjectAdapter = ProjectAdapter(this, ListProject,ListTask) { project ->
                if (project.card_color == "yellow") {
                    val intent = android.content.Intent(this, DetailProjectYellow::class.java)
                    intent.putExtra("project", project)
                    intent.putParcelableArrayListExtra("userList", ListUser)
                    intent.putParcelableArrayListExtra("taskList", ListTask)
                    intent.putParcelableArrayListExtra("laporanList", ListLaporan)
                    intent.putParcelableArrayListExtra("projectList", ListProject)
                    startActivity(intent)
                } else if (project.card_color == "blue") {
                    val intent = android.content.Intent(this, DetailProjectBlue::class.java)
                    intent.putExtra("project", project)
                    intent.putParcelableArrayListExtra("userList", ListUser)
                    intent.putParcelableArrayListExtra("taskList", ListTask)
                    intent.putParcelableArrayListExtra("laporanList", ListLaporan)
                    intent.putParcelableArrayListExtra("projectList", ListProject)
                    startActivity(intent)
                } else if (project.card_color == "green") {
                    val intent = android.content.Intent(this, DetailProjectGreen::class.java)
                    intent.putExtra("project", project)
                    intent.putParcelableArrayListExtra("userList", ListUser)
                    intent.putParcelableArrayListExtra("taskList", ListTask)
                    intent.putParcelableArrayListExtra("laporanList", ListLaporan)
                    intent.putParcelableArrayListExtra("projectList", ListProject)
                    startActivity(intent)
                } else if (project.card_color == "red") {
                    val intent = android.content.Intent(this, DetailProjectRed::class.java)
                    intent.putExtra("project", project)
                    intent.putParcelableArrayListExtra("userList", ListUser)
                    intent.putParcelableArrayListExtra("taskList", ListTask)
                    intent.putParcelableArrayListExtra("laporanList", ListLaporan)
                    intent.putParcelableArrayListExtra("projectList", ListProject)
                    startActivity(intent)
                } else if (project.card_color == "purple") {
                    val intent = android.content.Intent(this, DetailProjectPurple::class.java)
                    intent.putExtra("project", project)
                    intent.putParcelableArrayListExtra("userList", ListUser)
                    intent.putParcelableArrayListExtra("taskList", ListTask)
                    intent.putParcelableArrayListExtra("laporanList", ListLaporan)
                    intent.putParcelableArrayListExtra("projectList", ListProject)
                    startActivity(intent)
                }
            }
            ProjectRecyclerView.adapter = ProjectAdapter
            val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            ProjectRecyclerView.layoutManager = horizontalLayoutManager

            TabLayout = findViewById(R.id.tabLayout)
            ViewPager = findViewById(R.id.viewPager)

            val project = Project(255, "HOMESCREEN", "1", "1", "HOMESCREEN", "AMAN", "blue")

            FragmentManager = FragmentHomePageAdapter(supportFragmentManager, lifecycle, ListTask, project, ListProject)

            TabLayout.addTab(TabLayout.newTab().setText("All"))
            TabLayout.addTab(TabLayout.newTab().setText("To Do"))
            TabLayout.addTab(TabLayout.newTab().setText("Ongoing"))
            TabLayout.addTab(TabLayout.newTab().setText("Done"))

            ViewPager.adapter = FragmentManager

            TabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(p0: TabLayout.Tab?) {
                    ViewPager.currentItem = p0!!.position
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {

                }

                override fun onTabReselected(p0: TabLayout.Tab?) {

                }
            })

            ViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    TabLayout.selectTab(TabLayout.getTabAt(position))
                }
            })

        }

        private fun logData(tag: String, data: String) {
            Log.d(tag, "$tag: $data")
        }
}