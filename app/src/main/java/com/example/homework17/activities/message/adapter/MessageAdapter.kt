package com.example.homework17.activities.message.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework17.databinding.OtherMessageDataBinding
import com.example.homework17.databinding.UserMessageDataBinding
import com.example.homework17.models.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        const val USER_MESSAGE = 1
        const val OTHER_MESSAGE = 2
    }
    val user: FirebaseAuth? = FirebaseAuth.getInstance()
    private var userID: String? = null
    init {
         userID = user?.uid
           }
    private var messages:ArrayList<Message> = arrayListOf()
    inner class UserMessageViewHolder(var binding: UserMessageDataBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(){
            binding.message = messages[adapterPosition]
            binding.executePendingBindings()
        }
    }
    inner class OtherMessageViewHolder(var binding: OtherMessageDataBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(){
            binding.message = messages[adapterPosition]
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.
    ViewHolder {
        return if (viewType == USER_MESSAGE){
            UserMessageViewHolder(UserMessageDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
        }else{
            OtherMessageViewHolder(
                OtherMessageDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == USER_MESSAGE){
            (holder as UserMessageViewHolder).onBind()
        }else{
            (holder as OtherMessageViewHolder).onBind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(messages[position].id == userID) {
            USER_MESSAGE
        }else {
            OTHER_MESSAGE
        }
    }
    fun setData(messages:ArrayList<Message>) {
        this.messages = messages
        notifyItemInserted(this.messages.size)
    }
}