package com.example.tsaving.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.tsaving.DialogHandling
import com.example.tsaving.model.response.DashboardResponseModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DashboardViewModel (private val tsRepo: TsavingRepository, val triggerPgbr: (isVisible: Boolean) -> Unit): ViewModel(),
    CoroutineScope, LifecycleObserver {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private var _data = MutableLiveData<DashboardResponseModel>()
    var data : LiveData<DashboardResponseModel> = _data
    private var _errMessage = MutableLiveData<String>()
    var errMessage : LiveData<String> = _errMessage

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchDashboardData() {
        viewModelScope.launch {
            triggerPgbr(false)
            try {
                val result = withContext(Dispatchers.IO) {tsRepo.dashboard()}
                _data.value = result
                triggerPgbr(true)
            } catch (t: Throwable) {
                _errMessage.value = t.message
                when (t) {
                    is IOException -> println(t.message)
                    is HttpException -> println(t.message)
                }
            }
        }
    }
}
