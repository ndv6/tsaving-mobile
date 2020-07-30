package com.example.tsaving.vm


import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import com.example.tsaving.model.request.UpdatePasswordRequestModel
import com.example.tsaving.model.response.UpdatePasswordResponseModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

enum class ErrorPassword(val message:String) {
    ISBLANK("Password Can't be Blank"),
    NOTENOUGHLENGTH("Password Must be at Least 6 Characters"),
    NOTMATCH("Passwords do not Match")
}

class UpdatePasswordViewModel : ViewModel(), CoroutineScope, LifecycleObserver {
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    fun onCreate(){
        job = Job()

    }

    var repo = TsavingRepository()
    var request = UpdatePasswordRequestModel("oldpassword", "newpassword")
    var isValid = MutableLiveData<Boolean>()
    var _error = MutableLiveData<String>()

    fun onCallApi() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repo.updatePassword(request)
                }
                println("hahaha "+result)
                if (result.status == "SUCCESS") {
                    isValid.setValue(true)
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        _error.setValue("Network Error")
                        isValid.setValue(true)
                    }
                    is HttpException -> {
                        val code = t.code()
                        val errMsg = t.response().toString()
                        _error.setValue("HTTP Error $code $errMsg ")
                        isValid.setValue(true)
                    }
                }
            }
        }
    }


    private val _oldPassword = MutableLiveData<String>()
    private val _newPassword = MutableLiveData<String>()
    private val _confirmPassword = MutableLiveData<String>()

    private val _errorOldPassword = MutableLiveData<String>()
    private val _errorNewPassword = MutableLiveData<String>()
    private val _errorConfirmPassword = MutableLiveData<String>()

    val oldPassword: LiveData<String> = _oldPassword
    val newPassword: LiveData<String> = _newPassword
    val confirmPassword: LiveData<String> = _confirmPassword

    val errorOldPassword: LiveData<String> = _errorOldPassword
    val errorNewPassword: LiveData<String> = _errorNewPassword
    val errorConfirmPassword: LiveData<String> = _errorConfirmPassword

    fun onValidate(oldPassword: String, newPassword: String, confirmPassword: String) :Int {
        var status: Int = 0
        if (oldPassword.isBlank()) {
            _errorOldPassword.value = ErrorPassword.ISBLANK.message
            status = 1
        }
        if (newPassword.isBlank()) {
            _errorNewPassword.value = ErrorPassword.ISBLANK.message
            status = 1
        }
        if (confirmPassword.isBlank()) {
            _errorConfirmPassword.value = ErrorPassword.ISBLANK.message
            status = 1
        }

        if (!oldPassword.isBlank() && !newPassword.isBlank() && !confirmPassword.isBlank()) {
            if (confirmPassword != newPassword) {
                _errorConfirmPassword.value = ErrorPassword.NOTMATCH.message
                status = 1
            }
        }
        return status


    }
}



