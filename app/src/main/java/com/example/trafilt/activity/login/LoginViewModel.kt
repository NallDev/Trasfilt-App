package com.example.trafilt.activity.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trafilt.api.ApiConfig
import com.example.trafilt.api.DataUser
import com.example.trafilt.api.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _response = MutableLiveData<List<DataUser>>()
    val response: LiveData<List<DataUser>> = _response

    fun postLogin(email: String, password: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _response.postValue(response.body()?.data)
                    _isSuccess.postValue(true)
                    Log.e(TAG, "login berhasil")
                } else {
                    Log.e(TAG, "login gagal")
                    _isSuccess.postValue(false)
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "LoginViewModel"
    }

}