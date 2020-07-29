package com.example.tsaving

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tsaving.model.VirtualAccount

class VADetailsViewModel : ViewModel() {

    val virtualAccount = MutableLiveData<VirtualAccount>()
    private var counter = 0

    fun onCreate() {
//        TODO : get data virtual account from main activity intent
        virtualAccount.value = null
    }
}
