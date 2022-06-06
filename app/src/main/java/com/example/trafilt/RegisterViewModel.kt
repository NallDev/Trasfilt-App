package com.example.trafilt

import android.util.Log
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trafilt.api.ApiConfig
import com.example.trafilt.api.BaseResponse
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    companion object{
        private const val REGISTER = "RegisterViewModel"
        private const val REGISTER_SUCCESS = "Success"
    }
    private val _registerLoading = MutableLiveData<Boolean>()
    val registerLoading: LiveData<Boolean> = _registerLoading

    fun register(
        name: String,
        email: String,
        password: String,
        callbackString: ThemedSpinnerAdapter.Helper.ApiCallbackString){
        _registerLoading.value = true

        val service = ApiConfig().getApiService().register(name, email, password)
        service.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>){
                _registerLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error)
                        callbackString.onResponse(response.body() !=null, REGISTER_SUCCESS)
                }else{
                    Log.e(REGISTER,"onFailure: ${response.message()}")
                    val jsonObject = JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callbackString.onResponse(false, message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _registerLoading.value = false
                Log.e(REGISTER,"onFailure: ${t.message}")
                callbackString.onResponse(false,t.message.toString())
            }
        })
    }

}