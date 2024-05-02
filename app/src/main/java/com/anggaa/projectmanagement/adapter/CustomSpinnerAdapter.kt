package com.anggaa.projectmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import com.anggaa.projectmanagement.R

class CustomSpinnerAdapter(context: Context, resource: Int, private val items: List<String>) :
    ArrayAdapter<String>(context, resource, items) {

    // Struktur data untuk menyimpan status checkbox
    private val selectedCheckboxes = mutableMapOf<String, Boolean>()
    private val selectedUser: MutableList<String> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_spinner_item, parent, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        checkBox.text = "   ${items[position]}"

        // Tambahkan pendengar acara pada setiap checkbox
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            selectedCheckboxes[items[position]] = isChecked
            selectedUser.add(items[position])
            Toast.makeText(context, "${items[position]}: $isChecked", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "${selectedUser}", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    // Metode untuk mendapatkan data checkbox yang dipilih
    fun getSelectedCheckboxes(): Map<String, Boolean> {
        return selectedCheckboxes
    }

    fun getSelectedUser(): MutableList<String> {
        return selectedUser
    }
}
