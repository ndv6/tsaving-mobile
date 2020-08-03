package com.example.tsaving.vm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
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

    private val _label = MutableLiveData<String>()
    private val _errorLabel = MutableLiveData<String>()
    private val _status = MutableLiveData<Boolean>()
    private val _responseVA = MutableLiveData<GenericResponseModel<Any>>()

    val label: LiveData<String> = _label
    val errorLabel: LiveData<String> = _errorLabel
    val status: LiveData<Boolean> = _status
    val responseVA : LiveData<GenericResponseModel<Any>> = _responseVA

    //isblank return false kalau isblank.
    fun validateAddVa(label: String, color: String){
        var job: Job = Job ()
        if(label.isBlank()) {
            _errorLabel.value = "Please input this field"
        }
        else if(label.length > 95){
            _errorLabel.value = "Max. 95 character"
        }
        else{
            var repo = TsavingRepository()

            var request = AddVaRequestModel(color,label)

            viewModelScope.launch{
                try {
                    val result = withContext(Dispatchers.IO){repo.createVa(request)}
                    Log.i("result", result.message.toString())
                    _status.setValue(true)
                    _responseVA.value = result
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> Log.i("io exception", t.message.toString())
                        is HttpException -> _status.setValue(false)
                    }
                }
            }
        }


    }


}