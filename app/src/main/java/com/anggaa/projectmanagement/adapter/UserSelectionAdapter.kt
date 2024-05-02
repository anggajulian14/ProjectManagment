package com.anggaa.projectmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anggaa.projectmanagement.R
import com.anggaa.projectmanagement.model.User

class UserSelectionAdapter(private val userList: List<User>, private val listener: UserSelectionListener) :
    RecyclerView.Adapter<UserSelectionAdapter.ViewHolder>(), Filterable {

    private var filteredUserList: List<User> = userList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_checkbox, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = filteredUserList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = filteredUserList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)

        fun bind(user: User) {
            textViewName.text = user.UserName
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = user.isSelected
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                user.isSelected = isChecked
                listener.onUserSelected(filteredUserList.filter { it.isSelected }.map { it.UserName })
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    userList
                } else {
                    userList.filter { it.UserName.contains(constraint, true) }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredUserList = results?.values as List<User>
                notifyDataSetChanged()
            }
        }
    }
}

interface UserSelectionListener {
    fun onUserSelected(selectedUserList: List<String>)
}
