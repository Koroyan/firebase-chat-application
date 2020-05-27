package com.example.homework17.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val text = MutableLiveData<Int>().apply {
        value = 0
    }

    fun minus(){
        if (text.value != 0)
        text.value = text.value?.minus(1)
    }
    fun plus(){
        text.value = text.value?.plus(1)
    }
    fun getText():LiveData<Int>{
        return text
    }
}