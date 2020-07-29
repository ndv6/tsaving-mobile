package com.example.tsaving.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern.compile

class LoginViewModel : ViewModel(), LifecycleObserver {

    private val emailRegex = compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun validateLogin(email: String, password: String) : Int{
        var res = 0
        if(email.isBlank() || password.isBlank()){
            res = 1
        }else if(!emailRegex.matcher(email).matches()){
            res = 2
        }
        return res
    }

}