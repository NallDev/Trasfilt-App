package com.example.trafilt.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trafilt.activity.SharedPreference
import com.example.trafilt.activity.UserModel
import kotlinx.coroutines.launch

class LoginViewModel (private val pref: SharedPreference) : ViewModel(){
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
}