package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.GetTokenRequest
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.model.request.SendMailRequest
import com.example.tsaving.model.request.VerifyRequestModel
import com.example.tsaving.model.response.DataLogin
import com.example.tsaving.model.response.GenericResponseModel
import com.example.tsaving.model.response.GetTokenResponse
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException


class OTPViewModel : ViewModel(),
    LifecycleObserver {

    var repo = TsavingRepository()
    var _error  = MutableLiveData<ErrorName>()
    var isValid = MutableLiveData<Boolean>()
    var statusPB = MutableLiveData<Boolean>()
    var dataLogin = MutableLiveData<GenericResponseModel<DataLogin>>()
    var dataToken = MutableLiveData<GenericResponseModel<GetTokenResponse>>()
    var statusLogin = MutableLiveData<Boolean>()
    var isResend = MutableLiveData<Boolean>()

    fun onValidate(otp: String, email: String) {
        statusPB.value = true
        if (otp.isBlank()) {
            _error.value = ErrorName.NullOTP
            isValid.value = false
            statusPB.value = false
        } else {
            var request = VerifyRequestModel(otp, email)
            viewModelScope.launch{
                try {
                    statusPB.value = false
                    val result = withContext(Dispatchers.IO) { repo.verifyAccount(request) }
                    if (result.status == "SUCCESS") {
                        _error.value = null
                        isValid.value = true
                    }
                    return@launch
                } catch (t: Throwable) {
                    statusPB.value = false
                    when (t) {
                        is IOException -> {
                            _error.value = ErrorName.NetworkError
                            isValid.value = false
                        }
                        is HttpException -> {
                            _error.value = ErrorName.FailedToRecognizeOTP
                            isValid.value = false
                        }
                    }
                }
            }
        }
    }

    fun login(email: String, password: String){
        statusLogin.value = false
        var request = LoginRequestModel(email, password)
        Log.i("isValid", isValid.value.toString())
        viewModelScope.launch {
            try {
                statusPB.value = false
                val result = withContext(Dispatchers.IO) { repo.login(request) }
                if(result.status == "SUCCESS"){
                    dataLogin.value = result
                    statusLogin.value = true
                }
            } catch (t: Throwable) {
                statusPB.value = false
                statusLogin.value = true
                when (t) {
                    is IOException -> {
                        _error.value = ErrorName.ErrorNetwork
                    }
                    is HttpException -> {
                        val code = t.code()
                        if (code == 401) {
                            _error.value = ErrorName.LoginUnAuthorized
                        } else {
                            _error.value = ErrorName.InvalidLogin
                        }
                    }
                }
            }
        }
    }

    fun getOTP(email: String) {
        statusPB.value = true
        var request = GetTokenRequest(email)
        viewModelScope.launch {
            try {
                statusPB.value = false
                val result = withContext(Dispatchers.IO) {
                    repo.getToken(request)
                }
                if (result.status == "SUCCESS") {
                    dataToken.value = result
                }
            } catch (t: Throwable) {
                statusPB.value = false
                when (t) {
                    is IOException -> {
                        _error.value = ErrorName.NetworkError
                        isValid.value = false
                    }
                    is HttpException -> {
                        isValid.value = false
                        _error.value = ErrorName.FailedToRecognizeOTP
                    }
                }
            }
        }
    }

    fun resendOTP(email: String, token: String){
        statusPB.value = true
        var request = SendMailRequest(email, token)
        viewModelScope.launch {
            try {
                statusPB.value = false
                val result = withContext(Dispatchers.IO) {
                    repo.sendEmail(request)
                }
                isValid.setValue(true)
                isResend.setValue(true)
            } catch (t: Throwable) {
                statusPB.value = false
                isResend.value = false
                when (t) {
                    is IOException -> {
                        _error.setValue(ErrorName.NetworkError)
                        isValid.setValue(false)
                    }
                    is HttpException -> {
                        _error.setValue(ErrorName.ErrorSendMail)
                        isValid.setValue(false)
                    }
                }
            }
        }
    }
}