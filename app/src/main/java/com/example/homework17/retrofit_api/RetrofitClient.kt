package com.example.homework17.retrofit_api

import android.util.Log.d
import com.example.homework17.models.User
import com.example.homework17.retrofit_api.EndPoints.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var service: Api? = null
    private val instance: Api?
        get() {
            if (service == null) {
                service = retrofit.create(Api::class.java)
            }
            return service
        }

    fun getAllUsers(iCallBack: ICallBack){
        instance!!.allUsers.enqueue(object :Callback<HashMap<String,User>>{
            override fun onFailure(call: Call<HashMap<String,User>>, t: Throwable) {
                iCallBack.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<HashMap<String,User>>,
                response: Response<HashMap<String,User>>
            ) {
                if (response.isSuccessful){
                    val users:ArrayList<User> = arrayListOf()
                    for((_,value) in response.body()!!){
                        users.add(value)
                    }
                    iCallBack.onSuccess(users)
                }else{
                    this.onFailure(call, Throwable("error"))
                }
            }
        })
    }

    fun getUser(title:String,iCallBack: ICallBack){
        instance!!.getUser(title)
            .enqueue(callBack(iCallBack))
    }

    fun createUser(title:String, user: User, iCallBack: ICallBack){
        instance!!.createUser(title,user)
            .enqueue(callBack(iCallBack))
    }

   private fun callBack(iCallBack: ICallBack): Callback<User?> {
        return object :Callback<User?>{
            override fun onFailure(call: Call<User?>, t: Throwable) {
                iCallBack.onFailure(t.message.toString())
            }
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.isSuccessful){
                    d("infoinfo"," name ${response.body()?.name}")
                    iCallBack.onSuccess(response.body()!!)
                }else{
                    this.onFailure(call, Throwable("http request error"))
                }
            }
        }
    }
}