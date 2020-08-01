package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.TransferToVaRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

//cuma bisa event lifecycle doang bukan on click UI
class TransferViewModel : ViewModel(), LifecycleObserver{
    //mutable val
    val _flagError = MutableLiveData<ErrorName>()
    val _statusTransfer = MutableLiveData<Boolean>()

    //imutable val
    val flagError: LiveData<ErrorName> = _flagError
    val statusTransfer: LiveData<Boolean> = _statusTransfer

    fun callTransferToMain( va_num: String,amount: String){
        if(amount.isBlank()){
            _flagError.value = ErrorName.NullAmount
        }
        else{
            apiTransferToVa(va_num, amount.toInt())
        }
    }

    fun apiTransferToVa(va_num : String, va_balance: Int){
        var repo: TsavingRepository = TsavingRepository()
        var request: TransferToVaRequestModel = TransferToVaRequestModel(va_num, va_balance)
        Log.i("login req :", request.toString())

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { repo.transferVa(request) }
                if(result.status == "SUCCESS") {
                    _flagError.value = null
                    _statusTransfer.setValue(true)
                }
                else {
                    _flagError.value = ErrorName.InvalidTransferToVA
                    _statusTransfer.setValue(false)
                }
            }catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        _flagError.value = ErrorName.ErrorNetwork
                         _statusTransfer.setValue(false)
                    }
                    is HttpException -> {
                        val code = t.code()
                        val errMsg = t.response().toString()
                        Log.i("login error message", t.response().toString())
                        _flagError.value = ErrorName.InvalidTransferToVA
                        _statusTransfer.setValue(false)
                    }
                }
            }
        }
    } // end fun apiTransferToVa
}