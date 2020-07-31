package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.VerifyRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException


class OTPViewModel : ViewModel(),
    LifecycleObserver {

    var repo = TsavingRepository()
    var _error  = MutableLiveData<ErrorName>()
    var isValid = MutableLiveData<Boolean>()

    fun onValidate(otp: String, email: String) {
        if (otp.isBlank()) {
            _error.value = ErrorName.OTPCanNotBeBlank
            isValid.value = false
        } else {
            var request = VerifyRequestModel(otp, email)
            viewModelScope.launch{
                try {
                    val result = withContext(Dispatchers.IO) { repo.verifyAccount(request) }
                    Log.i("response", result.status)
                    if (result.status == "SUCCESS") {
                        _error.setValue(null)
                        isValid.setValue(true)
                    } else {
                        _error.setValue(ErrorName.FailedToRecognizeOTP)
                        isValid.setValue(false)
                    }
                    return@launch
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> {
                            _error.setValue(ErrorName.NetworkError)
                            isValid.setValue(false)
                        }
                        is HttpException -> {
                            _error.setValue(ErrorName.FailedToRecognizeOTP)
                            isValid.setValue(false)
                        }
                    }
                }
            }
        }
    }
}