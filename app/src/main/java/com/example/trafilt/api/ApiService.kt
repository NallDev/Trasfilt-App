package com.example.trafilt.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ): Call<Response>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password: String
    ): Call<BaseResponse>

    @GET("readDatasell")
    fun getSellTrashLocation(): Call<List<SellTrashItem>>

    @GET("readDatapickup")
    fun getPickUp(): Call<List<PickUpItem>>
}