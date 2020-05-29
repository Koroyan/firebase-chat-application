package com.example.homework17.data

import android.content.Context
import com.example.homework17.App

class SharedPreferenceData {
    companion object{
        val sharedPreferenceData by lazy {
            this
        }
        val sharedPreferences by lazy{
            App.instance.context!!.getSharedPreferences("userInfoData", Context.MODE_PRIVATE)
        }
        private val editor by lazy {
            sharedPreferences.edit()
        }
        fun pushString(key:String, value:String){
            editor.putString(key,value)
            editor.apply()
        }
        fun getString(key:String): String =  sharedPreferences.getString(key,"")!!

        fun removeString(key: String){
            if (sharedPreferences.contains(key)){
                editor.remove(key)
                editor.apply()
            }

        }

    }
}