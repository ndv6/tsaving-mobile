package com.example.tsaving.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsaving.model.ErrorModel
import com.example.tsaving.model.response.GenericResponseModel
import com.example.tsaving.model.response.TransactionHistoryResponseData
import com.example.tsaving.webservice.TsavingRepository
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class TransactionHistoryViewModel(
    private val tsavingRepository: TsavingRepository
) : ViewModel(), CoroutineScope, LifecycleObserver {
    private var transactionHistoryList: MutableList<TransactionHistoryResponseData> =
        mutableListOf()

    var transactionHistoryMutableLiveData: MutableLiveData<MutableList<TransactionHistoryResponseData>> =
        MutableLiveData()
    var isLastPageMutableLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorMutableLiveData: MutableLiveData<ErrorModel> = MutableLiveData()

    var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    fun listTransactionHistory(currentPage: Int) {
        viewModelScope.launch {
            try {
                val response =
                    withContext(Dispatchers.IO) {
                        tsavingRepository.listTransactionHistory(
                            currentPage
                        )
                    }

                /*
                Despite the IDE's suggestion, response.data != null is not always true in this case
                    This is because transaction history API's behavior.
                    When all data are already fetched from DB and next page is requested from API,
                        the API will return 200 status code with null value for data field in response body.
                 */
                if (response.data != null) {
                    if (response.data != transactionHistoryList) {
                        transactionHistoryList.addAll(response.data)
                        transactionHistoryMutableLiveData.value = transactionHistoryList
                    }
                } else {
                    isLastPageMutableLiveData.value = true
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> errorMutableLiveData.value =
                        ErrorModel(t.message ?: "", t.toString())
                    is HttpException -> {
                        val errorBody = Gson().fromJson(
                            t.response()?.errorBody()?.charStream(),
                            GenericResponseModel::class.java
                        )
                        errorMutableLiveData.value = ErrorModel(t.message(), errorBody.message)
                    }
                }
            }
        }
    }
}