package com.example.homework17.ui.home

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.homework17.BaseFragment
import com.example.homework17.R

class HomeFragment : BaseFragment(),View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    override fun itemResource(): Int = R.layout.fragment_home

    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       init()
    }

    private fun init(){
        itemView?.findViewById<Button>(R.id.minusButton)?.setOnClickListener(this)
        itemView?.findViewById<Button>(R.id.plusButton)?.setOnClickListener(this)
        val numberTextView = itemView?.findViewById<TextView>(R.id.numberTextView)
        homeViewModel.getText().observe(viewLifecycleOwner, Observer {
            d("infoinfo",it.toString())
            numberTextView?.text = it.toString()
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.minusButton ->{
                d("infoinfo","minus")
                homeViewModel.minus()
            }
            R.id.plusButton ->{
                d("infoinfo","plus")
                homeViewModel.plus()
            }
        }
    }


}
