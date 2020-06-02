package com.example.homework17.retrofit_api

import com.example.homework17.models.Message
import com.example.homework17.models.User
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @PUT("/messages/{title}.json")
    fun createMessage(
        @Path("title") title: String?,
        @Body task: Message
    ): Call<Message>

    @PUT("users/{title}.json")
    fun createUser(@Path("title") title:String?,
                   @Body body: User?):Call<User>

    @get:GET("/users/.json")
    val allUsers: Call<HashMap<String,User>>
    @GET("/users/{title}.json")
    fun getUser(@Path("title") title: String?): Call<User?>

}