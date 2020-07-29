package com.example.tsaving.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.tsaving.model.ResponseModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class DashboardViewModel (private val tsRepo: TsavingRepository): ViewModel(), LifecycleObserver {
    var _data = MutableLiveData<ResponseModel>()
    var data : LiveData<ResponseModel> = _data

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchDashboardData() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {tsRepo.dashboard()}
                _data.value = result
                Log.i("result", result.toString())
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> println(t.message)
                    is HttpException -> println(t.message)
                }
            }
        }
    }
}