package com.example.trafilt.activity.pickup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trafilt.api.ApiConfig
import com.example.trafilt.api.PickUpItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickUpViewModel : ViewModel(){
    private val _pickUps = MutableLiveData<List<PickUpItem>>()
    val pickUps : LiveData<List<PickUpItem>> = _pickUps

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    fun showPickUp(){
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getPickUp()
        client.enqueue(object : Callback<List<PickUpItem>>{
            override fun onResponse(
                call: Call<List<PickUpItem>>,
                response: Response<List<PickUpItem>>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful){
                    if(response.body() != null){
                        Log.e("PickUpViewModel", "Data Tersedia")
                        _isSuccess.postValue(true)
                        _pickUps.postValue(response.body())
                    }
                    else{
                        Log.e("PickUpViewModel", "Data Null")
                        _isSuccess.postValue(false)
                    }
                } else{
                    Log.e("PickUpViewModel", "Data not success")
                    _isSuccess.postValue(false)
                }
            }

            override fun onFailure(call: Call<List<PickUpItem>>, t: Throwable) {
                Log.e("PickUpViewModel", "onFailure: ${t.message}")
            }
        })
    }
}