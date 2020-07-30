package com.example.tsaving.vm

import androidx.lifecycle.*
import com.example.tsaving.model.request.VerifyRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException


class OTPViewModel : ViewModel(),
    LifecycleObserver {

    var repo = TsavingRepository()
    var _error  = MutableLiveData<String>()
    var isValid = MutableLiveData<Boolean>()

    fun onValidate(otp: String, email: String) {
        if (otp.isBlank()) {
            _error.value = "OTP can not be blank"
            isValid.value = false
        } else {
            var request = VerifyRequestModel(otp, email)
            viewModelScope.launch{
                try {
                    val result = withContext(Dispatchers.IO) { repo.verifyAccount(request) }
                    if (result.status == "SUCCESS") {
                        _error.setValue(null)
                        isValid.setValue(true)
                    }
                    return@launch
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> {
                            _error.setValue("Network Error")
                            isValid.setValue(false)
                        }
                        is HttpException -> {
                            val code = t.code()
                            val errMsg = t.response().toString()
                            _error.setValue("HTTP Error: $errMsg")
                            isValid.setValue(false)
                        }
                    }
                }
            }
        }
    }
}