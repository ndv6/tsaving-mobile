package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.model.ResponseModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class EditVaViewModel (private val tsRepo: TsavingRepository) : ViewModel(), CoroutineScope, LifecycleObserver {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private var _data = MutableLiveData<ResponseModel>()
    var data : LiveData<ResponseModel> = _data

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun fetchVaEditData(){
        viewModelScope.launch {
            try {
                val result= withContext(Dispatchers.IO) {tsRepo.updateVa(vaNum)}
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