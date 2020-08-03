package com.example.tsaving.vm

import android.util.Log
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
        Log.i("vaNum", vaNum)
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    val res = repo.deleteVa(vaNum)
                    if (res.isSuccessful) {
                        isDeleted.postValue(true)
                    }
                }
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