package com.anggaa.projectmanagement

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anggaa.projectmanagement.model.Laporan
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.model.User
import com.anggaa.projectmanagement.view.home.HomeScreen
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getAllDataAndMoveToNextActivity()

    }

    private fun getAllDataAndMoveToNextActivity() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            val ListUser = getUser()
            val ListProject = getProject()
            val ListTask = getTask()
            val ListLaporan = getLaporan()
            logData("DATA LIST", "List User : $ListUser\nList Project : $ListProject\nList Task : $ListTask\nList Laporan : $ListLaporan")

            // Semua data telah diambil, tutup dialog
            withContext(Dispatchers.Main) {
                moveToNextActivity(
                    ListUser,
                    ListProject,
                    ListTask,
                    ListLaporan
                )
                dialog.dismiss()
            }
        }
    }


    private fun moveToNextActivity(
        userList: List<User>,
        projectList: List<Project>,
        taskList: List<Task>,
        laporanList: List<Laporan>
    ) {
        val intent = Intent(this@MainActivity, HomeScreen::class.java).apply {
            putParcelableArrayListExtra("userList", ArrayList(userList))
            putParcelableArrayListExtra("projectList", ArrayList(projectList))
            putParcelableArrayListExtra("taskList", ArrayList(taskList))
            putParcelableArrayListExtra("laporanList", ArrayList(laporanList))
        }
        startActivity(intent)
        finish()
    }


    private suspend fun getUser(): List<User> {
        val userList = mutableListOf<User>()

        val dbRef = FirebaseDatabase.getInstance().getReference("user")
        val snapshot = dbRef.get().await()

        if (snapshot.exists()) {
            for (snap in snapshot.children) {
                val data = snap.getValue(User::class.java)
                data?.let { userList.add(it) }
                logData("User", data.toString())
            }
        }

        return userList
    }

    private suspend fun getProject(): List<Project> {
        val projectList = mutableListOf<Project>()

        val dbRef = FirebaseDatabase.getInstance().getReference("project")
        val snapshot = dbRef.get().await()

        if (snapshot.exists()) {
            for (snap in snapshot.children) {
                val data = snap.getValue(Project::class.java)
                data?.let { projectList.add(it) }
                logData("Project", data.toString())
            }
        }

        return projectList
    }

    private suspend fun getTask(): List<Task> {
        val taskList = mutableListOf<Task>()

        val dbRef = FirebaseDatabase.getInstance().getReference("task")
        val snapshot = dbRef.get().await()

        if (snapshot.exists()) {
            for (snap in snapshot.children) {
                val data = snap.getValue(Task::class.java)
                data?.let { taskList.add(it) }
                logData("Task", data.toString())
            }
        }

        return taskList
    }

    private suspend fun getLaporan(): List<Laporan> {
        val laporanList = mutableListOf<Laporan>()

        val dbRef = FirebaseDatabase.getInstance().getReference("laporan")
        val snapshot = dbRef.get().await()

        if (snapshot.exists()) {
            for (snap in snapshot.children) {
                val data = snap.getValue(Laporan::class.java)
                data?.let { laporanList.add(it) }
                logData("Laporan", data.toString())
            }
        }

        return laporanList
    }


    private fun logData(tag: String, data: String) {
        Log.d(tag, "$tag: $data")
    }
}