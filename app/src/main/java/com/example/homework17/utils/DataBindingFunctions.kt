package com.example.homework17.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework17.adapters.MessageAdapter
import com.squareup.picasso.Picasso

object DataBindingFunctions {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, imageUrl:String){
        Picasso.get()
            .load(imageUrl)
            .into(imageView)
    }
    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(recyclerView: RecyclerView, adapter:MessageAdapter){
        recyclerView.layoutManager = LinearLayoutManager(adapter.context)
        recyclerView.addItemDecoration(DividerItemDecoration(adapter.context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }
}