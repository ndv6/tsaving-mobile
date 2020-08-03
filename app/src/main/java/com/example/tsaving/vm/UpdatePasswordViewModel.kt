package com.example.tsaving.vm


import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.UpdatePasswordRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
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

    private val _errorOldPassword = MutableLiveData<String>()
    private val _errorNewPassword = MutableLiveData<String>()
    private val _errorConfirmPassword = MutableLiveData<String>()
    private val _status = MutableLiveData<Boolean>()
    private var _errorstatus = MutableLiveData<ErrorName>()

    val errorOldPassword: LiveData<String> = _errorOldPassword
    val errorNewPassword: LiveData<String> = _errorNewPassword
    val errorConfirmPassword: LiveData<String> = _errorConfirmPassword
    val status: LiveData<Boolean> = _status
    val errorstatus: LiveData<ErrorName> = _errorstatus


    fun onValidate(oldPassword: String, newPassword: String, confirmPassword: String) {
        job = Job()
        var repo = TsavingRepository()
        var confirm = true
        if (oldPassword.isBlank()) {
            _errorOldPassword.value = ErrorPassword.ISBLANK.message
            confirm = false
        }
        if (newPassword.isBlank()) {
            _errorNewPassword.value = ErrorPassword.ISBLANK.message
            confirm = false
        }
        if (confirmPassword.isBlank()) {
            _errorConfirmPassword.value = ErrorPassword.ISBLANK.message
            confirm = false
        }

        if (!oldPassword.isBlank() && !newPassword.isBlank() && !confirmPassword.isBlank()) {
            if (confirmPassword != newPassword) {
                _errorConfirmPassword.value = ErrorPassword.NOTMATCH.message
                confirm = false
            }
        }

        if (confirm == true) {
            var request = UpdatePasswordRequestModel(oldPassword, newPassword)
            Log.i("request", request.toString())
            viewModelScope.launch {
                try {
                    val result = withContext(Dispatchers.IO) {
                        repo.updatePassword(request)
                    }
                    if(result.status == "SUCCESS") {
                        _status.value = true
                    }
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> {
                            _status.value = false
                            _errorstatus.value = ErrorName.NetworkError
                        }

                        is HttpException -> {
                            _status.value = false
                            _errorstatus.value = ErrorName.UnableUpdatePassword
                        }
                    }
                }
            }
        } else {
            Log.i("Error", "Error in Update Password Confirmation")
            _status.setValue(false)
        }
    }
}



