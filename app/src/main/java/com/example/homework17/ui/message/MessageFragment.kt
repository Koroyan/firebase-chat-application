package com.example.homework17.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.homework17.BaseFragment
import com.example.homework17.R

class MessageFragment : Fragment() {

    private lateinit var messageViewModel: MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageViewModel =
            ViewModelProviders.of(this).get(MessageViewModel::class.java)

        messageViewModel.text.observe(this, Observer {
           // textView.text = it
        })
    }

}
