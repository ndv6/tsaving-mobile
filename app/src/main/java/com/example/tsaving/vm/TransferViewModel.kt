package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.TransferToMainRequestModel
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
    val _statusPB = MutableLiveData<Boolean>()
    val _statusTransferMain = MutableLiveData<Boolean>()

    //imutable val
    val flagError: LiveData<ErrorName> = _flagError
    val statusTransfer: LiveData<Boolean> = _statusTransfer
    val statusPB : LiveData<Boolean> = _statusPB
    val statusTransferMain : LiveData<Boolean> = _statusTransferMain

    fun callTransferToVa(va_num: String, amount: String){
        if(amount.isBlank() || amount == "0"){
            _flagError.value = ErrorName.NullAmount
        }
        else{
            apiTransferToVa(va_num, amount.toInt())
        }
    }

    fun callTransferToMain(va_num: String, amount: String){
        if(amount.isBlank() || amount == "0"){
            _flagError.value = ErrorName.NullAmount
        }else{
            apiTransferToMain(va_num,amount.toInt())
        }
    }

    fun apiTransferToVa(va_num : String, va_balance: Int){
        var repo: TsavingRepository = TsavingRepository()
        var request: TransferToVaRequestModel = TransferToVaRequestModel(va_num, va_balance)
        Log.i("login req :", request.toString())

        viewModelScope.launch {
            _statusPB.value = true
            try {
                _statusPB.value = false
                val result = withContext(Dispatchers.IO) { repo.transferVa(request) }
                Log.i( "res :" ,result.toString())
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
                        _flagError.value = ErrorName.InvalidTransferToVA
                        _statusTransfer.setValue(false)
                    }
                }
            }
        }
    } // end fun apiTransferToVa

    fun apiTransferToMain(va_num : String, va_balance: Int){
        var repo: TsavingRepository = TsavingRepository()
        var request: TransferToMainRequestModel = TransferToMainRequestModel(va_balance)

        viewModelScope.launch {
            _statusPB.value = true
            try {
                _statusPB.value = false
                val result = withContext(Dispatchers.IO) { repo.transferVaToMainAccount(va_num,request) }
                Log.i( "res :" ,result.toString())
                if(result.status == "SUCCESS") {
                    _flagError.value = null
                    _statusTransferMain.setValue(true)
                }
                else {
                    _flagError.value = ErrorName.InvalidTransferToMain
                    _statusTransferMain.setValue(false)
                }
            }catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        _flagError.value = ErrorName.ErrorNetwork
                        _statusTransferMain.setValue(false)
                    }
                    is HttpException -> {
                        val code = t.code()
                        val errMsg = t.response().toString()
                        Log.i("Transfer to Main Error", t.response().toString())
                        _flagError.value = ErrorName.InvalidTransferToMain
                        _statusTransferMain.setValue(false)
                    }
                }
            }
        }
    } // end fun apiTransferToVa


}