package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.tsaving.ErrorName
import com.example.tsaving.model.request.TransferToMainRequestModel
import com.example.tsaving.model.request.TransferToVaRequestModel
import com.example.tsaving.model.response.DataLogin
import com.example.tsaving.model.response.GenericResponseModel
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
    val _statusPB = MutableLiveData<Boolean>()
    val _dataTFVAToMain = MutableLiveData<GenericResponseModel<Any>>()
    val _dataTFVA = MutableLiveData<GenericResponseModel<Any>>()

    //imutable val
    val flagError: LiveData<ErrorName> = _flagError
    val statusPB : LiveData<Boolean> = _statusPB
    val dataTFVAToMain : MutableLiveData<GenericResponseModel<Any>> = _dataTFVAToMain
    val dataTFVA : MutableLiveData<GenericResponseModel<Any>> = _dataTFVA

    fun callTransferToVa(va_num: String, amount: String){
        if(amount.isBlank()){
            _flagError.value = ErrorName.NullAmount
        }
        else{
            apiTransferToVa(va_num, amount.toInt())
        }
    }

    fun callTransferToMain(va_num: String, amount: String){
        if(amount.isBlank()){
            _flagError.value = ErrorName.NullAmount
        }else{
            apiTransferToMain(va_num,amount.toInt())
        }
    }

    fun apiTransferToVa(va_num : String, va_balance: Int){
        var repo: TsavingRepository = TsavingRepository()
        var request: TransferToVaRequestModel = TransferToVaRequestModel(va_num, va_balance)

        viewModelScope.launch {
            _statusPB.value = true
            try {
                _statusPB.value = false
                val result = withContext(Dispatchers.IO) { repo.transferVa(request) }
                _dataTFVA.value = result
            } catch (t: Throwable) {
                _statusPB.value = false
                when (t) {
                    is IOException -> {
                        _flagError.value = ErrorName.ErrorNetwork
                    }
                    is HttpException -> {
                        _flagError.value = ErrorName.InvalidTransferToVA
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
                _dataTFVAToMain.value = result
            }catch (t: Throwable) {
                _statusPB.value = false
                when (t) {
                    is IOException -> {
                        _flagError.value = ErrorName.ErrorNetwork
                    }
                    is HttpException -> {
                        _flagError.value = ErrorName.InvalidTransferToMain
                    }
                }
            }
        }
    } // end fun apiTransferToMain


}