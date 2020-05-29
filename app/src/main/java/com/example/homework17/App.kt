package com.example.homework17


import android.app.Application
import android.content.Context

class App: Application() {
    var context: Context? = null
    companion object{
        val instance by lazy {
            App()
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}