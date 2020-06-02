package com.example.homework17.ui.users

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework17.databinding.UsersDataBinding
import com.example.homework17.models.User

class UserAdapter(val context: Context):RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var users = arrayListOf<User>()
    inner class ViewHolder(private val binding: UsersDataBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(){
            binding.user = users[adapterPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UsersDataBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.onBind()
    }
    fun setData(users:ArrayList<User>){
        this.users = users
        notifyDataSetChanged()
    }
}