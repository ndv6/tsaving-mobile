package com.example.tsaving.vm

import android.util.Log
import androidx.lifecycle.*
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
    val _labelFrom = MutableLiveData<String>()
    val _numFrom = MutableLiveData<String>()
    val _labelTo = MutableLiveData<String>()
    val _numTo = MutableLiveData<String>()
    val _statusTransfer = MutableLiveData<Boolean>()

    //imutable val
    val labelFrom : LiveData<String> = _labelFrom
    val numFrom : LiveData<String> = _numFrom
    val labelTo : LiveData<String> = _labelTo
    val numTo : LiveData<String> = _numTo
    val statusTransfer: LiveData<Boolean> = _statusTransfer

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setDataPage(){
        //later data must from API or parse from previous form
        _labelFrom.value = "VA Tabungan Berjangka"
        _numFrom.value = "202020001001"
        _labelTo.value = "Main Account"
        _numTo.value = "202020001"
    }

    fun validateTransfer(amount: String) :Boolean = !amount.isBlank()

    fun apiTransferToVa(va_num : String, va_balance: Int){
        var repo: TsavingRepository = TsavingRepository()

        //please change email & passwordnya from edit text then delete this comment
        var request: TransferToVaRequestModel = TransferToVaRequestModel(va_num, va_balance)
        Log.i("login req :", request.toString())

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { repo.transferVa(request) }
                Log.i("result", result.toString())
                if(result.status == "SUCCESS") {
                    Log.i("a","hahahaha")
                    _statusTransfer.setValue(true)
                }
                else {
                    Log.i("a","huhuhu")
                    _statusTransfer.setValue(false)
                }
            }catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                         _statusTransfer.setValue(false)
//                        _flagStatus.value = ErrorName.ErrorNetwork
                    }
                    is HttpException -> {
                        //Please change this to handle error with Sekar's dialog box then delete this comment
                        val code = t.code()
                        val errMsg = t.response().toString()
                        Log.i("login error message", t.response().toString())
                        _statusTransfer.setValue(false)
//                        _flagStatus.value = ErrorName.InvalidLogin
                    }
                }
            }
        }
        // Here, please code what the app will do if API call succeed, then delete this comment
//
    }
}