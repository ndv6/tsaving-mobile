package com.example.tsaving.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.tsaving.isEmailValid
import java.util.regex.Pattern.compile

class LoginViewModel : ViewModel(), LifecycleObserver {

    fun validateLogin(email: String, password: String) : Int{
        var res = 0
        if(email.isBlank() || password.isBlank()){
            res = 1
        }else if(!email.isEmailValid()){
            res = 2
        }
        return res
    }

}