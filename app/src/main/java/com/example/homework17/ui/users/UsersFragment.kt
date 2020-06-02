package com.example.homework17.ui.users

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homework17.databinding.FragmentUsersBinding
import com.example.homework17.models.User
import com.example.homework17.retrofit_api.ICallBack
import com.example.homework17.retrofit_api.RetrofitClient
import kotlinx.android.synthetic.main.fragment_users.view.*

class UsersFragment : Fragment() {
    private var itemView: View? = null
    private var binding: FragmentUsersBinding? = null
    private var users: ArrayList<User>? = null
    lateinit var userAdapter: UserAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (itemView == null) {
            binding = FragmentUsersBinding.inflate(inflater, container, false)
            itemView = binding!!.root
        }
        return itemView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        users = arrayListOf()
        userAdapter = UserAdapter(activity!!)
        binding?.adapter = userAdapter
        getData()
        binding!!.root.swipeRefreshLayout.setOnRefreshListener {
            binding!!.root.swipeRefreshLayout.isRefreshing = true
            if (users != null) {
                users!!.clear()
                userAdapter.setData(users!!)
            }
            Handler().postDelayed({
                getData()
                binding!!.root.swipeRefreshLayout.isRefreshing = false
            }, 2000)

        }
    }

    private fun getData() {
        RetrofitClient.getAllUsers(object : ICallBack {
            override fun onFailure(e: String) {
                Toast.makeText(activity, e, Toast.LENGTH_LONG).show()
            }

            override fun onSuccess(body: ArrayList<User>) {
                users = body
                userAdapter.setData(users!!)
            }
        })
    }
}
