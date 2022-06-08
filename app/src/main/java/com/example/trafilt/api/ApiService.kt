package com.example.trafilt.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("loginUser")
    fun postLogin(
        @Field("user_email") email : String,
        @Field("user_password") password : String
    ): Call<Login>

    @FormUrlEncoded
    @POST("createUser")
    fun postRegister(
        @Field("user_name") name : String,
        @Field("user_email") email : String,
        @Field("user_password") password: String
    ): Call<Register>

    @GET("readDatasell")
    fun getSellTrashLocation(): Call<List<SellTrashItem>>

    @GET("readDatapickup")
    fun getPickUp(): Call<List<PickUpItem>>
}