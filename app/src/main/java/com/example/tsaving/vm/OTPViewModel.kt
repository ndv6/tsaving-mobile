package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.model.request.VerifyRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException


class OTPViewModel : ViewModel(),
    LifecycleObserver {

    var repo = TsavingRepository()
    var _error  = MutableLiveData<String>()
    var isValid = MutableLiveData<Boolean>()

    fun onValidate(otp: String) {
        if (otp.isBlank()) {
            _error.value = "OTP can not be empty"
            isValid.value = false
        } else {
//            TODO : get token and email from view
            var request = VerifyRequestModel("testing", "testing@apa.com")
            viewModelScope.launch{
                try {
                    val result = withContext(Dispatchers.IO) { repo.verifyAccount(request) }
                    if (result.status == "SUCCESS") {
                        isValid.setValue(true)
                    }
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> {
                            _error.setValue("Network Error")
                            isValid.setValue(false)
                        }
                        is HttpException -> {
                            val code = t.code()
                            val errMsg = t.response().toString()
                            _error.setValue("HTTP Error $code $errMsg")
                            isValid.setValue(false)
                        }
                    }
                }
            }
        }
    }
}