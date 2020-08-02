package com.example.tsaving.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsaving.ErrorName
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class VADetailsViewModel : ViewModel(), LifecycleObserver {
    var errorFlag = MutableLiveData<ErrorName>()
    var isDeleted = MutableLiveData<Boolean>()

    var repo = TsavingRepository()

    fun onDelete(vaNum: String){
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repo.deleteVa(vaNum)
                }
                isDeleted.setValue(true)
            } catch (t : Throwable) {
                when (t) {
                    is IOException -> {
                        isDeleted.setValue(false)
                        errorFlag.setValue(ErrorName.NetworkError)
                    }
                    is HttpException -> {
                        isDeleted.setValue(false)
                        errorFlag.setValue(ErrorName.UnableDeleteVA)
                    }
                }
            }
        }
    }
}