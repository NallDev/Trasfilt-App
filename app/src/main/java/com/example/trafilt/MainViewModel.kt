package com.example.trafilt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trafilt.activity.UserModel
import com.example.trafilt.activity.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUsers(): LiveData<UserModel>{
        return pref.getUsers().asLiveData()
    }
    fun logOut() {
        viewModelScope.launch {
            pref.logOut()
        }
    }
}