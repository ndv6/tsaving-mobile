package com.example.tsaving.vm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.tsaving.model.request.AddVaRequestModel
import com.example.tsaving.model.response.AddVaResponseModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_add_va.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class AddVaViewModel : ViewModel(), LifecycleObserver,CoroutineScope {


    private val _label = MutableLiveData<String>()
    private val _errorLabel = MutableLiveData<String>()

    val label: LiveData<String> = _label
    val errorLabel: LiveData<String> = _errorLabel


    //isblank return false kalau isblank.
    fun validateAddVa(label: String){
        if(label.isBlank()) {
            _errorLabel.value = "Please input this field"
        }
    }

    //buat nandain kalau coroutine ini thread
    var job: Job = Job ()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    var repo = TsavingRepository()

    var _data: MutableLiveData<AddVaResponseModel> = MutableLiveData<AddVaResponseModel>()
    var data: LiveData<AddVaResponseModel> = _data

    fun addVaRequest(){
        //bikin request
        var request = AddVaRequestModel("Red","Tabungan Harian")

        viewModelScope.launch{
//            try{
            try {
                val result = withContext(Dispatchers.IO){repo.createVa(request)}
                _data.value = result
                Log.i("result", result.message.toString())
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> Log.i("io exception", t.message.toString())
                    is HttpException -> Log.i("http exception", t.message.toString())
                }
            }


//                if (result.status == "SUCCESS") {
//                    isValid.setValue(true)
//                }

//            }catch(t: Throwable){
//                when (t) {
//                    is IOException -> {
//                        _error.setValue("Network Error")
//                        isValid.setValue(false)
//                    }
//                    is HttpException -> {
//                        val code = t.code()
//                        val errMsg = t.response().toString()
//                        _error.setValue("HTTP Error $code $errMsg")
//                        isValid.setValue(false)
//                    }
//                }
//
//            }
        }
    }
}