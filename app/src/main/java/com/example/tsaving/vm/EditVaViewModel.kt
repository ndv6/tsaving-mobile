package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.model.ResponseModel
import com.example.tsaving.model.request.EditVaRequestModel
import com.example.tsaving.model.response.EditVaResponse
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class EditVaViewModel (var vaNum : String) : ViewModel(), CoroutineScope {
    var _data = MutableLiveData<EditVaResponse>()
    var data : LiveData<EditVaResponse> = _data

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    var tsRepo = TsavingRepository()

    fun fetchVaEditData(){

        var request = EditVaRequestModel("Tabungan", "Red")

        viewModelScope.launch {
            try {
                val result= withContext(Dispatchers.IO) {tsRepo.updateVa(vaNum, request)}
                _data.value = result
                Log.i("result", result.toString())
            }catch (t: Throwable){
                when (t) {
                    is IOException -> println(t.message)
                    is HttpException -> println(t.message)
                }
            }
        }
    }

    fun validateEditVa(label: String) :Boolean = !label.isBlank()
}