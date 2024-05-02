package com.anggaa.projectmanagement.view.project.detailProject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.adapter.FragmentPageAdapter
import com.anggaa.projectmanagement.model.Laporan
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.model.User
import com.google.android.material.tabs.TabLayout

class DetailProjectGreen : AppCompatActivity() {

    private lateinit var projectName : TextView
    private lateinit var buttonBack : ImageButton
    private lateinit var projectTitle: TextView

    private lateinit var TabLayout: TabLayout
    private lateinit var ViewPager: ViewPager2
    private lateinit var FragmentManager: FragmentPageAdapter

    private lateinit var TaskCheckBox: CheckBox

    private lateinit var ListUser: ArrayList<User>
    private lateinit var ListProject: ArrayList<Project>
    private lateinit var ListTask: ArrayList<Task>
    private lateinit var ListLaporan: ArrayList<Laporan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_project_green)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getData()

        initView()
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun initView() {

        val project = intent.getParcelableExtra<Project>("project")
        projectName = findViewById<TextView>(R.id.projectName)
        projectName.text = project?.nama_proyek

        projectTitle = findViewById(R.id.ProjectName)
        projectTitle.text = project?.nama_proyek

        TabLayout = findViewById(R.id.tabLayout)
        ViewPager = findViewById(R.id.viewPager)

            FragmentManager = FragmentPageAdapter(supportFragmentManager, lifecycle, ListTask,ListUser, project!!, ListProject)

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

        val CheckBoxContainer = findViewById<LinearLayout>(R.id.CheckBoxContainer)

        val ProjectTask = ListTask.filter {
            it.id_proyek == project.project_id
        }

        for (i in 0 until ProjectTask.size) {
            val CheckBox = CheckBox(this)
            CheckBox.text = ListTask[i].nama_tugas
            CheckBox.buttonDrawable = resources.getDrawable(R.drawable.custom_checkbox)
            CheckBox.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            CheckBox.setPadding(20, 0, 5, 0)
            CheckBox.setTextSize(12f)
            CheckBox.typeface = resources.getFont(R.font.poppinsregular)
            CheckBox.id = View.generateViewId()

            CheckBoxContainer.addView(CheckBox)
        }

        buttonBack = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }

        val TaskCount = findViewById<TextView>(R.id.JumlahTask)
        TaskCount.text = "${ProjectTask.filter {
            it.status_tugas == "Selesai"
        }.size} out of ${ProjectTask.size} tasks"

    }

    private fun getData(){
        ListProject = intent.getParcelableArrayListExtra<Project>("projectList")!!
        ListTask = intent.getParcelableArrayListExtra<Task>("taskList")!!
        ListLaporan = intent.getParcelableArrayListExtra<Laporan>("laporanList")!!
        ListUser = intent.getParcelableArrayListExtra<User>("userList")!!
    }

    private fun logData(tag: String, data: String) {
        Log.d(tag, data)
    }
}