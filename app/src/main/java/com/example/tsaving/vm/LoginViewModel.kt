package com.example.tsaving.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.tsaving.ErrorName
import com.example.tsaving.IsEmailValid
import java.util.regex.Pattern.compile

class LoginViewModel : ViewModel(), LifecycleObserver {

    fun validateLogin(email: String, password: String, errorName : (ErrorName) -> Unit ) : Boolean{
        return when{
            email.isBlank() ->{
                errorName(ErrorName.NullEmail)
                false
            }
            password.isBlank()->{
                errorName(ErrorName.NullPassword)
                false
            }
            !email.IsEmailValid()->{
                errorName(ErrorName.InvalidEmail)
                false
            }
            //should be from api
            email != "a@gmail.com" || password != "123" ->{
                errorName(ErrorName.InvalidLogin)
                false
            }
            else -> true
        }
    }


}