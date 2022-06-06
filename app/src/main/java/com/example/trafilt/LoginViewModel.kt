package com.example.trafilt

import android.util.Log
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.ApiConfig
import com.example.data.helper.Helper
import com.example.data.response.LoginResponse
import com.example.model.user.UserModel
import com.example.model.user.UserPreference
import com.example.trafilt.activity.UserModel
import com.example.trafilt.activity.UserPreference
import com.example.trafilt.api.ApiConfig
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val pref: UserPreference) : ViewModel() {

    companion object {
        private const val LOGIN = "LoginViewModel"
        private const val SUCCESS = "success"
    }

    private val loginIsLoading = MutableLiveData<Boolean>()
    val loginLoading: LiveData<Boolean> = loginIsLoading

    fun login(
        email: String,
        password: String,
        callback: ThemedSpinnerAdapter.Helper.ApiCallbackString) {
        loginIsLoading.value = true

        val service = ApiConfig().getApiService().login(email, password)
        service.enqueue(object : Callback<Response> {
            override fun onResponse(
                call: Call<Response>,
                response: Response<Response>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {

                        callback.onResponse(response.body() != null, SUCCESS)

                        val model = UserModel(
                            responseBody.loginResult.name,
                            email,
                            password,
                        )
                        saveUser(model)
                    }
                } else {
                    Log.e(LOGIN, "onFailure: ${response.message()}")
                    val jsonObject = JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callback.onResponse(false, message)
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                loginIsLoading.value = false
                Log.e(LOGIN, "onFailure: ${t.message}")
                callback.onResponse(false, t.message.toString())
            }
        })
    }
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}