package com.example.homework17.retrofit_api

import com.example.homework17.models.Message
import com.example.homework17.models.User

interface ICallBack {
    fun onFailure(e:String)
    fun onSuccess(body: Any){}
    fun onSuccess(body:ArrayList<User>){}
}