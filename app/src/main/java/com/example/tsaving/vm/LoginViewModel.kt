package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.IsEmailValid
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.model.response.LoginResponseModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class LoginViewModel : ViewModel(), LifecycleObserver {

    val _flagStatus = MutableLiveData<ErrorName>()
    val _dataLogin = MutableLiveData<LoginResponseModel>()
    var _statusPB = MutableLiveData<Boolean>()

    val flagStatus : LiveData<ErrorName> = _flagStatus
    val dataLogin : LiveData<LoginResponseModel> = _dataLogin
    var statusPB : LiveData<Boolean> = _statusPB

    fun validateLogin(email: String, password: String ){
        when{
            email.isBlank() && password.isBlank()->{
                _flagStatus.value = ErrorName.NullEmailAndPass
            }
            email.isBlank() ->{
                _flagStatus.value = ErrorName.NullEmail
            }
            password.isBlank()->{
                _flagStatus.value = ErrorName.NullPassword
            }
            !email.IsEmailValid()->{
                _flagStatus.value = ErrorName.InvalidEmail
            }else -> apiLogin(email,password)
        }

    }

    fun apiLogin(email :String, password: String) {
        _statusPB.value = true
        var repo: TsavingRepository = TsavingRepository()

        //please change email & passwordnya from edit text then delete this comment
        var request: LoginRequestModel = LoginRequestModel(email, password)
        Log.i("login req :", request.toString())

        viewModelScope.launch {
            try {
                _statusPB.value = false
                val result = withContext(Dispatchers.IO) { repo.login(request) }
                Log.i("result", result.data.toString())
                if(result.status == "SUCCESS"){
                    _flagStatus.value = null
                    _dataLogin.value = result
                }
            } catch (t: Throwable) {
                _statusPB.value = false
                when (t) {
                    is IOException -> {
                        _flagStatus.value = ErrorName.ErrorNetwork
                    }
                    is HttpException -> {
                        //Please change this to handle error with Sekar's dialog box then delete this comment
                        val code = t.code()
                        val errMsg = t.response().toString()
                        Log.i("login error message", t.response().toString())
                        _flagStatus.value = ErrorName.InvalidLogin
                    }
                }
            }
        }
    }


}