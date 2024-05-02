package com.anggaa.projectmanagement.view.project.detailTask

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class DetailTaskYellow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {

        val Task = intent.getParcelableExtra<Task>("task")

        val viewTaskName = findViewById<TextView>(R.id.TaskName)
        viewTaskName.text = Task?.nama_tugas

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.TaskStatus).text = Task?.status_tugas
        findViewById<TextView>(R.id.JudulTask).text = Task?.nama_tugas
        findViewById<TextView>(R.id.TaskStatus).text = Task?.status_tugas
        findViewById<TextView>(R.id.TaskDimulai).text = formatDate(Task?.tanggal_mulai!!)
        findViewById<TextView>(R.id.TaskBerakhir).text = formatDate(Task.tanggal_selesai)
        findViewById<TextView>(R.id.DeskripsiTask).text = Task.deskripsi_tugas

    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }
}