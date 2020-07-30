package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.IsEmailValid
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class LoginViewModel : ViewModel(), LifecycleObserver {

    val _statusLogin = MutableLiveData<Boolean>()
    val _flagStatus = MutableLiveData<ErrorName>()

    val statusLogin : LiveData<Boolean> = _statusLogin
    val flagStatus : LiveData<ErrorName> = _flagStatus

    fun validateLogin(email: String, password: String ){
        when{
            email.isBlank() ->{
                _statusLogin.value = false
                _flagStatus.value = ErrorName.NullEmail
            }
            password.isBlank()->{
                _statusLogin.value = false
                _flagStatus.value = ErrorName.NullPassword
            }
            !email.IsEmailValid()->{
                _statusLogin.value = false
                _flagStatus.value = ErrorName.InvalidEmail
            }else -> apiLogin(email,password)
        }

    }

    fun apiLogin(email :String, password: String) {
        var repo: TsavingRepository = TsavingRepository()

        //please change email & passwordnya from edit text then delete this comment
        var request: LoginRequestModel = LoginRequestModel(email, password)
        Log.i("login req :", request.toString())

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { repo.login(request) }
                Log.i("result", result.toString())
                _statusLogin.value = true
                _flagStatus.value = null
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        _statusLogin.value = false
                        _flagStatus.value = ErrorName.ErrorNetwork
                    }
                    is HttpException -> {
                        //Please change this to handle error with Sekar's dialog box then delete this comment
                        val code = t.code()
                        val errMsg = t.response().toString()
                        Log.i("login error message", t.response().toString())
                        _statusLogin.value = false
                        _flagStatus.value = ErrorName.InvalidLogin
                    }
                }
            }
        }
        // Here, please code what the app will do if API call succeed, then delete this comment
//
    }


}