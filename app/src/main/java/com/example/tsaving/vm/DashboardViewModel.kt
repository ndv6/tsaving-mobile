package com.example.tsaving.vm

import androidx.lifecycle.*
import com.example.tsaving.model.DashboardModel
import com.example.tsaving.model.DashboardResponseModel
import com.example.tsaving.model.response.GenericResponseModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DashboardViewModel (private val tsRepo: TsavingRepository, val triggerPgbr: (isVisible: Boolean) -> Unit): ViewModel(),
    CoroutineScope, LifecycleObserver {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private var _data = MutableLiveData<GenericResponseModel<DashboardModel>>()
    var data : LiveData<GenericResponseModel<DashboardModel>> = _data
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
