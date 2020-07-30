package com.example.tsaving.view_model

import androidx.lifecycle.*
import com.example.tsaving.model.ResponseModelList
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class TransactionHistoryViewModel(
    private val tsavingRepository: TsavingRepository
) : ViewModel(), CoroutineScope, LifecycleObserver {
    //val request: JSONObject = JSONObject("{}")
    var currentPage = 1
    var transactionHistoryList: MutableLiveData<ResponseModelList> = MutableLiveData()
    var dataWrapper: LiveData<ResponseModelList> = transactionHistoryList

    var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun listTransactionHistory() {
        viewModelScope.launch {
            try {
                val response =
                    withContext(Dispatchers.IO) {
                        tsavingRepository.listTransactionHistory(
                            currentPage
                        )
                    }
                transactionHistoryList.value = response
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> println(t.message)
                    is HttpException -> println(t.message)
                }
            }
        }
    }

    //1/2layar load page nextnya
    //1/2layar :posisi x posisi y item kepake berapa

}