package com.example.tsaving.vm

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsaving.model.request.AddVaRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_add_va.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class AddVaViewModel : ViewModel(), LifecycleObserver,CoroutineScope {

    var status = MutableLiveData<Boolean?>()

    //isblank return false kalau isblank.
    fun validateAddVa(label: String) :Boolean = !label.isBlank()

    //buat nandain kalau coroutine ini thread
    lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    fun onCreate(savedInstanceState: Bundle?){

        job = Job()
        var repo = TsavingRepository()

        //bikin request
        var request = AddVaRequestModel("Red","Tabungan Harian")

        viewModelScope.launch{
            try{
                val result = withContext(Dispatchers.IO){repo.createVa(request)}
                if (result.status == "SUCCESS") {
                    isValid.setValue(true)
                }

            }catch(t: Throwable){
                when (t) {
                    is IOException -> {
                        _error.setValue("Network Error")
                        isValid.setValue(false)
                    }
                    is HttpException -> {
                        val code = t.code()
                        val errMsg = t.response().toString()
                        _error.setValue("HTTP Error $code $errMsg")
                        isValid.setValue(false)
                    }
                }

            }
        }
    }
}