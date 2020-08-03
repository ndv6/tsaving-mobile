package com.example.tsaving.vm

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.RegisterRequestModel
import com.example.tsaving.model.response.DataLogin
import com.example.tsaving.model.response.GenericResponseModel
import com.example.tsaving.model.response.RegisterResponse
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class RegisterViewModel : ViewModel(), CoroutineScope, LifecycleObserver {

    lateinit var job: Job

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private val _flagStatus = MutableLiveData<ErrorName>()
    private val _dataRegister = MutableLiveData<GenericResponseModel<RegisterResponse>>()
    private var _progresBar = MutableLiveData<Boolean>()

    val progresBar: LiveData<Boolean> = _progresBar

    val flagStatus: LiveData<ErrorName> = _flagStatus
    val dataRegister: LiveData<GenericResponseModel<RegisterResponse>> = _dataRegister

    fun onValidate(name: String,address: String,phone: String,email: String,password: String,channel: String){
        var repo: TsavingRepository = TsavingRepository()
        job = Job()

        if (name.isBlank()){_flagStatus.value = ErrorName.NullName }
        if(address.isBlank()){_flagStatus.value = ErrorName.NullAddress}

        if(phone.isBlank()){ _flagStatus.value = ErrorName.NullPhone }

        if(email.isBlank()){ _flagStatus.value = ErrorName.NullEmail}
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ _flagStatus.value = ErrorName.InvalidEmail}

        if(password.isBlank()){ _flagStatus.value = ErrorName.NullPassword}
        else if (password.length < 6){ _flagStatus.value = ErrorName.NotValidLength}

        if(!name.isBlank() && !address.isBlank() && !phone.isBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length>=6) {
            _progresBar.setValue(true)
            var request: RegisterRequestModel =
                RegisterRequestModel(name, address, phone, email, password, channel)

            viewModelScope.launch {
                try {
                    val result = withContext(Dispatchers.IO) { repo.register(request) }
                    _flagStatus.value = ErrorName.Null
                    _dataRegister.value = result
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> {
                            _flagStatus.value  = ErrorName.ErrorNetwork
                        }
                        is HttpException -> {
                            _flagStatus.value = ErrorName.InvalidRegister
                        }
                    }
                }
            }
            _progresBar.setValue(false)
        }
    }
}
