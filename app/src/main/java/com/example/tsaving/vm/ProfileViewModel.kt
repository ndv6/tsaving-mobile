package com.example.tsaving.vm

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel : ViewModel(), LifecycleObserver {
    var accNum: String = "2007160004"

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _isVerified = MutableLiveData<Boolean>()
    val isVerified: LiveData<Boolean> = _isVerified

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    init {
        _name.value = "Init Sukirman"
        _email.value = "initsukirman@sukijan.com"
        _isVerified.value = false
        _phone.value = "085252525252"
        _address.value = "Init Jalan Mega Kuningan"
    }
}