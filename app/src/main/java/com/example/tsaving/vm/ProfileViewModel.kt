package com.example.tsaving.vm

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import com.example.tsaving.model.response.GenericResponseModel
import com.example.tsaving.model.response.ProfileResponse
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(private val tsRepo: TsavingRepository) : ViewModel(), CoroutineScope, LifecycleObserver {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _data = MutableLiveData<GenericResponseModel<ProfileResponse>>()
    val data: LiveData<GenericResponseModel<ProfileResponse>> = _data

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun fetchProfileData() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {tsRepo.viewProfile()}
                Log.i("ProfileViewModel", result.toString())
                if (result.status == "SUCCESS") {
                    _data.value = result
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> println(t.message)
                    is HttpException -> println(t.message)
                }
            }
        }
        _loading.value = false
    }
}