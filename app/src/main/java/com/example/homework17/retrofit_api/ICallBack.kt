package com.example.homework17.retrofit_api

import com.example.homework17.models.Message

interface ICallBack {
    fun onFailure(e:String)
    fun onSuccess(body: Any){}
    fun onSuccess(body:HashMap<String, Message>){}
}