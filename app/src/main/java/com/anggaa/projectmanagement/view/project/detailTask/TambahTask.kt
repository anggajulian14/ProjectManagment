package com.anggaa.projectmanagement.view.project.detailTask

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.adapter.CustomSpinnerAdapter
import com.anggaa.projectmanagement.adapter.SelectedEmployeeAdapter
import com.anggaa.projectmanagement.adapter.UserSelectionAdapter
import com.anggaa.projectmanagement.adapter.UserSelectionListener
import com.anggaa.projectmanagement.model.Project
import com.anggaa.projectmanagement.model.Task
import com.anggaa.projectmanagement.model.User
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectBlue
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectGreen
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectPurple
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectRed
import com.anggaa.projectmanagement.view.project.detailProject.DetailProjectYellow
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class TambahTask : AppCompatActivity() {

    private lateinit var project: Project
    private lateinit var NamaTask: EditText
    private lateinit var DeskripsiTask: EditText
    private lateinit var TanggalMulai: String
    private lateinit var TanggalSelesai: String
    private lateinit var User: MutableList<Int>
    private lateinit var NamaUser: MutableList<String>
    private lateinit var dataUser: ArrayList<User>
    private lateinit var selectedEmployeeAdapter: SelectedEmployeeAdapter

    private val selectedUserList: MutableList<String> = mutableListOf()

    private var selectedDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    private var selectedMonth = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedYear = Calendar.getInstance().get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
        project = intent.getParcelableExtra<Project>("project")!!
        dataUser = intent.getParcelableArrayListExtra<User>("dataUser")!!

        val dataNamaUser = ArrayList<String>()
        for (nama in dataUser!!) {
            dataNamaUser.add(nama.UserName)
        }

        NamaUser = arrayListOf()
        User = arrayListOf()

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }

        val namaProject = findViewById<TextView>(R.id.TaskName)
        namaProject.text = project.nama_proyek ?: "Project Tidak Ditemukan"

        NamaTask = findViewById(R.id.EDTNamaTask)
        DeskripsiTask = findViewById(R.id.EDTDescTask)
        val edtStartTask = findViewById<TextView>(R.id.TanggalMulai)
        val edtEndTask = findViewById<TextView>(R.id.TaskBerakhir)

        edtStartTask.setOnClickListener { showDatePicker(edtStartTask) }
        edtEndTask.setOnClickListener { showDatePicker(edtEndTask) }

        val cardSelectedUser = findViewById<CardView>(R.id.CardTambahKaryawan)
        if (selectedUserList.isEmpty()) {
            cardSelectedUser.visibility = View.GONE
        }

        // Mengatur RecyclerView dan Adapter di onCreate atau fungsi lain yang sesuai
        val recyclerViewSelectedEmployee = findViewById<RecyclerView>(R.id.SelectedKaryawan)
        selectedEmployeeAdapter = SelectedEmployeeAdapter(selectedUserList)
        recyclerViewSelectedEmployee.adapter = selectedEmployeeAdapter
        recyclerViewSelectedEmployee.layoutManager = LinearLayoutManager(this)

        // Mengatur onClickListener untuk menampilkan dialog pilihan karyawan
        findViewById<Button>(R.id.ButtonTambahKaryawan).setOnClickListener {
            showUserSelectionDialog(this, dataUser, object : UserSelectionListener {
                override fun onUserSelected(selectedUserList: List<String>) {
                    // Menambahkan karyawan yang dipilih ke dalam selectedUserList
                    this@TambahTask.selectedUserList.clear()
                    this@TambahTask.selectedUserList.addAll(selectedUserList)
                    // Menampilkan cardSelectedUser jika ada pengguna yang dipilih
                    if (selectedUserList.isNotEmpty()) {
                        cardSelectedUser.visibility = View.VISIBLE
                    } else {
                        cardSelectedUser.visibility = View.GONE
                    }
                    // Memberi tahu adapter bahwa data telah berubah
                    selectedEmployeeAdapter.notifyDataSetChanged()
                    // Menampilkan toast dengan daftar karyawan yang dipilih
                    Toast.makeText(this@TambahTask, selectedUserList.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }

        val buttonSubmit = findViewById<Button>(R.id.TambahTugas)
        buttonSubmit.setOnClickListener {

            dataUser.forEach { user ->
                if (selectedUserList.contains(user.UserName)) {
                    User.add(user.UserID.toInt())
                }
            }

            tambahTugas(User)
        }
    }

    fun showUserSelectionDialog(context: Context, userList: List<User>, listener: UserSelectionListener) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_user_selection)

        val searchView = dialog.findViewById<SearchView>(R.id.searchView)
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = UserSelectionAdapter(userList, listener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        dialog.show()
    }


    private fun showDatePicker(editText: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val formattedDate = formatDate("$year-${monthOfYear + 1}-$dayOfMonth")
                editText.text = formattedDate
                if (editText.id == R.id.TanggalMulai) {
                    TanggalMulai = "$year-${monthOfYear + 1}-$dayOfMonth"
                } else {
                    TanggalSelesai = "$year-${monthOfYear + 1}-$dayOfMonth"
                }
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun tambahTugas(ListUser: List<Int>) {

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("task")

        val id = "${System.currentTimeMillis()}${UUID.randomUUID()}"
        val ID = id.hashCode()

        if (ID != null && ::TanggalMulai.isInitialized && ::TanggalSelesai.isInitialized &&
            NamaTask.text.isNotBlank() && DeskripsiTask.text.isNotBlank() && project.project_id != null) {

            val task = Task(
                ID,
                project.project_id,
                NamaTask.text.toString(),
                TanggalMulai,
                TanggalSelesai,
                "To Do",
                ListUser,
                DeskripsiTask.text.toString()
            )

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_loading)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            reference.child(task.task_id.toString()).setValue(task).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    dialog.dismiss()
                    Toast.makeText(this, "Tugas Berhasil", Toast.LENGTH_SHORT).show()
                    if (project.card_color == "yellow"){
                        val intent = Intent(this, DetailProjectYellow::class.java)
                        intent.putExtra("activity", "tambahTask")
                        intent.putExtra("project", project)
                        startActivity(intent)
                    }else if (project.card_color == "green"){
                        val intent = Intent(this, DetailProjectGreen::class.java)
                        intent.putExtra("activity", "tambahTask")
                        intent.putExtra("project", project)
                        startActivity(intent)
                    } else if (project.card_color == "blue"){
                        val intent = Intent(this, DetailProjectBlue::class.java)
                        intent.putExtra("activity", "tambahTask")
                        intent.putExtra("project", project)
                        startActivity(intent)
                    }else if (project.card_color == "red"){
                        val intent = Intent(this, DetailProjectRed::class.java)
                        intent.putExtra("activity", "tambahTask")
                        intent.putExtra("project", project)
                        startActivity(intent)
                    }else if (project.card_color == "purple"){
                        val intent = Intent(this, DetailProjectPurple::class.java)
                        intent.putExtra("activity", "tambahTask")
                        intent.putExtra("project", project)
                        startActivity(intent)
                    }

                } else {
                    dialog.dismiss()
                    Toast.makeText(this, "Tugas Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            logData("Tugas", task.toString())

        } else {
            Toast.makeText(this, "Data Tidak Lengkap", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }

    private fun logData(tag: String, data: String){
        Log.d(tag, "$tag: $data")
    }
}
