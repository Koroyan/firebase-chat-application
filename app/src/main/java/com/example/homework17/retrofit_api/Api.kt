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

    @GET("/messages/{title}.json")
    fun getMessage(@Path("title") title: String?): Call<Message?>? // could be used for fetching details or checking if item already exists

    // note that we'll receive a Map here from firebase with key being the identifier
    @get:GET("/messages/.json")
    val allMessages: Call<HashMap<String, Message>>

    @DELETE("/deleteMessage/{title}.json")
    fun deleteMessage(@Path("title") title: String?): Call<Message?>?

    @PUT("users/{title}.json")
    fun createUser(@Path("title") title:String?,
                   @Body body: User?):Call<String>

    @get:GET("/users/.json")
    val allUsers: Call<HashMap<String, User>>


}