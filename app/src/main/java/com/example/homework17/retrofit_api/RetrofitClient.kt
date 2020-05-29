package com.example.homework17.retrofit_api

import com.example.homework17.models.Message
import com.example.homework17.models.User
import com.example.homework17.retrofit_api.EndPoints.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private var service: Api? = null
    val instance: Api?
        get() {
            if (service == null) {
                service = retrofit.create(Api::class.java)
            }
            return service
        }
    fun createMessage(title:String, message: Message, iCallBack: ICallBack){
        instance!!.createMessage(title, message)
            .enqueue(object :Callback<Message>{
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    iCallBack.onFailure(t.message.toString())
                }

                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful){
                        iCallBack.onSuccess(response.body()!!)
                    }else{
                        this.onFailure(call, Throwable(response.errorBody()!!.string()))
                    }
                }

            })
    }
    fun getAllMessages(iCallBack: ICallBack){
        instance!!.allMessages.enqueue(object :Callback<HashMap<String,Message>>{
            override fun onFailure(call: Call<HashMap<String, Message>>, t: Throwable) {
                iCallBack.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<HashMap<String, Message>>,
                response: Response<HashMap<String, Message>>
            ) {
                if (response.isSuccessful){
                    iCallBack.onSuccess(response.body()!!)
                }else{
                    this.onFailure(call,Throwable("error get all messages"))
                }
            }

        })
    }

    fun createUser(title:String, user: User, iCallBack: ICallBack){
        instance!!.createUser(title,user)
            .enqueue(callBack(iCallBack))
    }

   private fun callBack(iCallBack: ICallBack): Callback<String> {
        return object :Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                iCallBack.onFailure(t.message.toString())
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    iCallBack.onSuccess(response.body().toString())
                }else{
                    this.onFailure(call, Throwable("http request error"))
                }
            }

        }
    }
}