package com.example.tsaving.vm

import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_add_va.*
import kotlin.coroutines.coroutineContext

class AddVaViewModel : ViewModel(), LifecycleObserver {

    var status = MutableLiveData<Boolean?>()

    //isblank return false kalau isblank.
    fun validateAddVa(label: String) :Boolean = !label.isBlank()
}