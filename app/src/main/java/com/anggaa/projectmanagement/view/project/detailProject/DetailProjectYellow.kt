package com.anggaa.projectmanagement.view.project.detailProject

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.adapter.FragmentPageAdapter
import com.anggaa.projectmanagement.adapter.ProjectAdapter
import com.anggaa.projectmanagement.model.Laporan
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.model.User
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.math.log

class DetailProjectYellow : AppCompatActivity() {

    private lateinit var projectName : TextView
    private lateinit var buttonBack : ImageButton
    private lateinit var projectTitle: TextView

    private lateinit var project:Project

    private lateinit var TabLayout: TabLayout
    private lateinit var ViewPager: ViewPager2
    private lateinit var FragmentManager: FragmentPageAdapter

    private lateinit var TaskCheckBox: CheckBox

    private lateinit var ListUser: List<User>
    private lateinit var ListProject: List<Project>
    private lateinit var ListTask: List<Task>
    private lateinit var ListLaporan: List<Laporan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_project_yellow)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<LinearLayout>(R.id.main).visibility = View.GONE

        getData()
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun initView() {

        logData("LAUNCH INIT VIEW", "LAUNCH INIT VIEW")

        findViewById<LinearLayout>(R.id.main).visibility = View.VISIBLE

        project = intent.getParcelableExtra<Project>("project")!!
        projectName = findViewById<TextView>(R.id.projectName)
        projectName.text = project?.nama_proyek

        projectTitle = findViewById(R.id.ProjectName)
        projectTitle.text = project?.nama_proyek

        TabLayout = findViewById(R.id.tabLayout)
        ViewPager = findViewById(R.id.viewPager)

        if (project != null && ListProject.isNotEmpty() && ListTask.isNotEmpty() && ListUser.isNotEmpty()) {
            FragmentManager = FragmentPageAdapter(supportFragmentManager, lifecycle, ListTask,ListUser,
                project, ListProject)
        }else{
            logData("DATA EMPTY", "$project ${ListLaporan.toString()} ${ListProject.toString()} ${ListTask.toString()} ${ListUser.toString()}")
        }

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
            it.id_proyek == project?.project_id
        }

        logData("ProjectTask", ProjectTask.toString())

        for (i in 0 until ProjectTask.size) {
            val task = ProjectTask[i]
            val checkBox = CheckBox(this)
            checkBox.text = task.nama_tugas
            checkBox.buttonDrawable = resources.getDrawable(R.drawable.custom_checkbox)
            checkBox.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            checkBox.setPadding(20, 0, 5, 0)
            checkBox.setTextSize(12f)
            checkBox.typeface = resources.getFont(R.font.poppinsregular)
            checkBox.id = View.generateViewId()

            // Set status checkbox berdasarkan status tugas
            checkBox.isChecked = task.status_tugas == "Selesai"

            // Menangani perubahan status checkbox
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Jika checkbox dicentang, ubah status tugas menjadi "Selesai"
                    task.status_tugas = "Selesai"
                    val database = FirebaseDatabase.getInstance()
                    val dbRef = database.getReference("task").child("${task.task_id}")
                    val taskUpdates = hashMapOf<String, Any>("status_tugas" to task.status_tugas)

                    dbRef.updateChildren(taskUpdates).addOnSuccessListener {
                        // Panggil kembali activity saat operasi berhasil
                        val intent = Intent(this, DetailProjectYellow::class.java)
                        intent.putExtra("project", project)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener { e ->
                        // Tangani jika terjadi kesalahan saat menulis ke database
                        Toast.makeText(this, "Failed to update task status: ${e.message}", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    if (task.status_tugas != "Selesai") {
                        // Jika checkbox tidak dicentang, ubah status tugas menjadi "Belum Selesai" atau yang sesuai
                        task.status_tugas = task.status_tugas
                        val database = FirebaseDatabase.getInstance()
                        val dbRef = database.getReference("task").child("${task.task_id}")
                        val taskUpdates = hashMapOf<String, Any>("status_tugas" to task.status_tugas)

                        dbRef.updateChildren(taskUpdates).addOnSuccessListener {
                            // Panggil kembali activity saat operasi berhasil
                            val intent = Intent(this, DetailProjectYellow::class.java)
                            intent.putExtra("project", project)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener { e ->
                            // Tangani jika terjadi kesalahan saat menulis ke database
                            Toast.makeText(this, "Failed to update task status: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Jika checkbox tidak dicentang, ubah status tugas menjadi "Belum Selesai" atau yang sesuai
                        task.status_tugas = "Ongoing"
                        val database = FirebaseDatabase.getInstance()
                        val dbRef = database.getReference("task").child("${task.task_id}")
                        val taskUpdates = hashMapOf<String, Any>("status_tugas" to task.status_tugas)

                        dbRef.updateChildren(taskUpdates).addOnSuccessListener {
                            // Panggil kembali activity saat operasi berhasil
                            val intent = Intent(this, DetailProjectYellow::class.java)
                            intent.putExtra("project", project)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener { e ->
                            // Tangani jika terjadi kesalahan saat menulis ke database
                            Toast.makeText(this, "Failed to update task status: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


            CheckBoxContainer.addView(checkBox)
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

    private fun getData() {

        val activity = intent.getStringExtra("activity")
        logData("activity", activity.toString())
        if (activity == "home") {
            // Ambil data dari intent jika activity adalah "home"
            ListTask = intent.getParcelableArrayListExtra<Task>("taskList")!!
            ListProject = intent.getParcelableArrayListExtra<Project>("projectList")!!
            ListLaporan = intent.getParcelableArrayListExtra<Laporan>("laporanList")!!
            ListUser = intent.getParcelableArrayListExtra<User>("userList")!!
            initView() // Panggil initView() karena data sudah ada
        } else {
           getDataFromFirebase()
        }
    }

    private fun getDataFromFirebase() {
        val db = FirebaseDatabase.getInstance()

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Ambil data user
                val userSnapshot = db.getReference("user").get().await()
                if (userSnapshot.exists()) {
                    val userList = mutableListOf<User>()
                    for (snap in userSnapshot.children) {
                        val user = snap.getValue(User::class.java)
                        user?.let { userList.add(it) }
                        logData("User", user.toString())
                    }
                    ListUser = userList
                }

                // Ambil data project
                val projectSnapshot = db.getReference("project").get().await()
                if (projectSnapshot.exists()) {
                    val projectList = mutableListOf<Project>()
                    for (snap in projectSnapshot.children) {
                        val project = snap.getValue(Project::class.java)
                        project?.let { projectList.add(it) }
                        logData("Project", project.toString())
                    }
                    ListProject = projectList
                }

                // Ambil data task
                val taskSnapshot = db.getReference("task").get().await()
                if (taskSnapshot.exists()) {
                    val taskList = mutableListOf<Task>()
                    for (snap in taskSnapshot.children) {
                        val task = snap.getValue(Task::class.java)
                        task?.let { taskList.add(it) }
                        logData("Task", task.toString())
                    }
                    ListTask = taskList
                }

                // Ambil data laporan
                val laporanSnapshot = db.getReference("laporan").get().await()
                if (laporanSnapshot.exists()) {
                    val laporanList = mutableListOf<Laporan>()
                    for (snap in laporanSnapshot.children) {
                        val laporan = snap.getValue(Laporan::class.java)
                        laporan?.let { laporanList.add(it) }
                        logData("Laporan", laporan.toString())
                    }
                    ListLaporan = laporanList
                }

                // Sembunyikan dialog loading setelah semua data diambil
                withContext(Dispatchers.Main) {
                 dialog.dismiss()
                    // Panggil initView setelah semua data diambil
                    initView()
                }
            } catch (e: Exception) {
                // Tangani jika terjadi kesalahan
                withContext(Dispatchers.Main) {
                    dialog.dismiss()
                    logData("ERROR DATA", e.stackTraceToString())
                    Toast.makeText(this@DetailProjectYellow, "Something went wrong $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun logData(tag: String, data: String) {
        Log.d(tag, data)
    }
}