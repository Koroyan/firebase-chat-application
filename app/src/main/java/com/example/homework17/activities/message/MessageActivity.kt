package com.example.homework17.activities.message


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework17.R
import com.example.homework17.activities.message.adapter.MessageAdapter
import com.example.homework17.databinding.ActivityMessageBinding
import com.example.homework17.models.Message
import com.example.homework17.utils.DateConverter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_message.view.*
import java.util.*

class MessageActivity : AppCompatActivity() {
    lateinit var messageAdapter: MessageAdapter
    lateinit var layout: RecyclerView.LayoutManager
    lateinit var messages: ArrayList<Message>
    lateinit var binding: ActivityMessageBinding
    private val user = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.getReference("messages")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        init()
        messageListener()
    }

    private fun init() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_message)
        messages = arrayListOf()
        messageAdapter =
            MessageAdapter(this)
        messageAdapter.setData(messages)
        binding.adapter = messageAdapter

        messageSendButton.setOnClickListener {
            val message = messageEditText.text.toString()
            if (message.isEmpty()) {
                return@setOnClickListener
            }
            val id = user.uid.toString()
            var image = "empty"
            if (user.currentUser?.photoUrl != null) {
                image = user.currentUser?.photoUrl.toString()
            }
            val userName = user.currentUser?.displayName
            val messageModel =
                Message(id, message, image, userName!!, DateConverter.date(Date().toString()))
            messageEditText.text = null
            myRef.push().setValue(messageModel)
        }
    }

    private fun messageListener() {
        myRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message =
                    p0.getValue(Message::class.java)
                if (message != null) {
                    messages.add(message)
                    messageAdapter.setData(messages)
                    binding.root.recyclerView.smoothScrollToPosition(messages.size)
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }
}
