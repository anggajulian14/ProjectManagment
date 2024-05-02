package com.anggaa.projectmanagement.model

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var UserID: Long,

    @get:PropertyName("nama_pengguna")
    @set:PropertyName("nama_pengguna")
    var UserName: String,

    @get:PropertyName("kontak")
    @set:PropertyName("kontak")
    var Contact: String,

    @get:PropertyName("posisi")
    @set:PropertyName("posisi")
    var Position: String,

    @get:PropertyName("gudang_akses")
    @set:PropertyName("gudang_akses")
    var WarehouseAccess: String,

    var isSelected: Boolean

) : Parcelable {
    constructor() : this(0, "", "", "", "", false)}

@Parcelize
data class Project(
    val project_id: Int,
    val nama_proyek: String,
    val tanggal_mulai: String,
    val tanggal_selesai: String,
    val deskripsi_proyek: String,
    val status_proyek: String,
    val card_color: String
) : Parcelable {
    constructor() : this(0, "", "","", "", "", "")
}

@Parcelize
data class Task(
    val task_id: Int,
    val id_proyek: Int,
    val nama_tugas: String,
    val tanggal_mulai: String,
    val tanggal_selesai: String,
    var status_tugas: String,
    val id_pengguna: List<Int>,
    val deskripsi_tugas: String
) : Parcelable{
    constructor() : this(0, 0, "", "","", "", emptyList(), "")
}

@Parcelize
data class Laporan(
    val laporan_id: Int,
    val task_id: Int,
    val deskripsi: String,
    val media: String?,
    val status_laporan: String
) : Parcelable{
    constructor() : this(0, 0, "", "", "")
}