package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.TransferToVaRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.EntityReference
import retrofit2.HttpException
import java.io.IOException

//cuma bisa event lifecycle doang bukan on click UI
class TransferViewModel : ViewModel(), LifecycleObserver{
    //mutable val
    val _flagError = MutableLiveData<ErrorName>()
    val _statusTransfer = MutableLiveData<Boolean>()

    //imutable val
    val flagError : LiveData<ErrorName> = _flagError
    val statusTransfer: LiveData<Boolean> = _statusTransfer


    fun validateTransfer(amount: String){

    }

    fun apiTransferToVa(va_num : String, va_balance: Int){
        var repo: TsavingRepository = TsavingRepository()

        var request: TransferToVaRequestModel = TransferToVaRequestModel(va_num, va_balance)
        Log.i("login req :", request.toString())

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { repo.transferVa(request) }
                if(result.status == "SUCCESS") {
                    _statusTransfer.setValue(true)
                }
                else {
                    _statusTransfer.setValue(false)
                }
            }catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                         _statusTransfer.setValue(false)
//                        _flagStatus.value = ErrorName.ErrorNetwork
                    }
                    is HttpException -> {
                        val code = t.code()
                        val errMsg = t.response().toString()
                        Log.i("login error message", t.response().toString())
                        _statusTransfer.setValue(false)

                    }
                }
            }
        }
    }
}