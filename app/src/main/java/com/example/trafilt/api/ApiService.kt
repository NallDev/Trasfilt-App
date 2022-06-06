package com.example.trafilt.api

import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("readDatasell")
    fun getSellTrashLocation(): Call<List<SellTrashItem>>

    @GET("readDatapickup")
    fun getPickUp(): Call<List<PickUpItem>>
}