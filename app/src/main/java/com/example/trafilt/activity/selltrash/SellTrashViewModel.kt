package com.example.trafilt.activity.selltrash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trafilt.api.ApiConfig
import com.example.trafilt.api.SellTrashItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellTrashViewModel : ViewModel() {
    val listSellTrash = MutableLiveData<List<SellTrashItem>>()

    fun getLocation(): LiveData<List<SellTrashItem>> {return listSellTrash}

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun getAllSellTrash() {
        _isLoading.postValue(true)
        ApiConfig.getApiService().getSellTrashLocation()
            .enqueue(object : Callback<List<SellTrashItem>> {
                override fun onResponse(
                    call: Call<List<SellTrashItem>>,
                    response: Response<List<SellTrashItem>>
                ) {
                    _isLoading.postValue(false)
                    if (response.isSuccessful) {
                        Log.e("SellTrashViewModel", "data tersedia")
                        _isSuccess.postValue(true)
                        listSellTrash.postValue(response.body())
                    } else {
                        _isSuccess.postValue(false)
                        Log.e("SellTrashViewModel", "kosong")
                    }
                }

                override fun onFailure(call: Call<List<SellTrashItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("SellTrashViewModel", t.message.toString())
                }
            })
    }
}