package com.example.homework17.activities.message

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework17.R
import com.example.homework17.activities.message.adapter.MessageAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

object MessageDataBindingComponents {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(imageView: ImageView, imageUrl:String){
        if(imageUrl.isEmpty())return
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .fit()
            .into(imageView,object : Callback {
                override fun onSuccess() {
                }
                override fun onError(e: Exception?) {
                    Picasso.get().load(imageUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(imageView)
                }
            })
    }
    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: MessageAdapter){
        recyclerView.layoutManager = LinearLayoutManager(adapter.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        recyclerView.addItemDecoration(DividerItemDecoration(adapter.context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }
}