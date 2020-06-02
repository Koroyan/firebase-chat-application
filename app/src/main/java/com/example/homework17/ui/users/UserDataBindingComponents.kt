package com.example.homework17.ui.users

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework17.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

object UserDataBindingComponents {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(imageView: ImageView, imageUrl:String){
        if(imageUrl.isEmpty())return
        //uuuf dzaan bevri fiqris mere mainc performance movige :D
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.mipmap.default_profile_image)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .fit()
            .into(imageView,object :Callback{
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    Picasso.get().load(imageUrl)
                        .placeholder(R.mipmap.default_profile_image)
                        .fit()
                        .centerCrop()
                        .into(imageView)
                }

            })
    }
    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: UserAdapter){
        recyclerView.layoutManager = LinearLayoutManager(adapter.context)
        recyclerView.addItemDecoration(DividerItemDecoration(adapter.context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }
}