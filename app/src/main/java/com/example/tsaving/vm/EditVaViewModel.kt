package com.example.tsaving.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

class EditVaViewModel : ViewModel(), LifecycleObserver {
    fun validateEditVa(label: String) :Boolean = !label.isBlank()
}