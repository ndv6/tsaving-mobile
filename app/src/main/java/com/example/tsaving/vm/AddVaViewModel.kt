package com.example.tsaving.vm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.AddVaRequestModel
import com.example.tsaving.model.response.AddVaResponseModel
import com.example.tsaving.model.response.GenericResponseModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_add_va.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class AddVaViewModel : ViewModel(), LifecycleObserver,CoroutineScope {
    lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private val _statusPB = MutableLiveData<Boolean>()
    private val _responseVA = MutableLiveData<GenericResponseModel<Any>>()
    private val _flagError = MutableLiveData<ErrorName>()

    val flagError: LiveData<ErrorName> = _flagError
    val statusPB: LiveData<Boolean> = _statusPB
    val responseVA : LiveData<GenericResponseModel<Any>> = _responseVA

    //isblank return false kalau isblank.
    fun validateAddVa(label: String, color: String) {
        var job: Job = Job()
        if (label.isBlank()) {
            _flagError.value = ErrorName.Null
        } else {
            var repo = TsavingRepository()
            var request = AddVaRequestModel(color, label)

            viewModelScope.launch {
                _statusPB.value = true
                try {
                    _statusPB.setValue(false)
                    val result = withContext(Dispatchers.IO) { repo.createVa(request) }
                    Log.i("result", result.message.toString())
                    _responseVA.value = result
                } catch (t: Throwable) {
                    _statusPB.setValue(false)
                    when (t) {
                        is IOException -> _flagError.value = ErrorName.ErrorNetwork
                        is HttpException -> _flagError.value = ErrorName.ErrorBadRequest
                    }
                }
            }
        }
    }


}