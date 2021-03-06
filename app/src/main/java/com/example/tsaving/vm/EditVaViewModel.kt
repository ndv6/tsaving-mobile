package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.EditVaRequestModel
import com.example.tsaving.model.response.EditVaResponse
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class EditVaViewModel (private val tsRepo: TsavingRepository, var vaNum : String) : ViewModel(), CoroutineScope, LifecycleObserver {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private var _data = MutableLiveData<EditVaResponse>()
    var data : LiveData<EditVaResponse> = _data

    private var _statusPB = MutableLiveData<Boolean>()
    var statusPB : LiveData<Boolean> = _statusPB

    private var _flagStatus = MutableLiveData<ErrorName>()
    var flagStatus : LiveData<ErrorName> = _flagStatus

    fun validateEditVa(label: String, color: String){
        when{
            label.isBlank() -> {
                _flagStatus.value = ErrorName.Null
            }
            label.length > 95 -> {
                _flagStatus.value = ErrorName.NotValidLength
            }else -> fetchVaEditData(label, color)
        }
    }

    fun fetchVaEditData(vaLabel:String, vaColor:String){
        _statusPB.value = true
        val request = EditVaRequestModel(vaLabel, vaColor)

        viewModelScope.launch {
            try {
                _statusPB.value = false
                val result= withContext(Dispatchers.IO) {tsRepo.updateVa(vaNum, request)}
                if (result.status == "SUCCESS"){
                    _data.value = result
                    Log.i("result", result.toString())
                }
            }catch (t: Throwable){
                _statusPB.value = false
                when (t) {
                    is IOException -> {
                        _flagStatus.value = ErrorName.NetworkError
                        println(t.message)
                    }
                    is HttpException -> {
                        _flagStatus.value = ErrorName.NetworkError
                        println(t.message)
                    }
                }
            }
        }
    }

}