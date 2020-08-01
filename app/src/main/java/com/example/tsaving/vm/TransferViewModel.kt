package com.example.tsaving.vm

import androidx.lifecycle.*
//cuma bisa event lifecycle doang bukan on click UI
class TransferViewModel : ViewModel(), LifecycleObserver{
    //mutable val
    val _labelFrom = MutableLiveData<String>()
    val _numFrom = MutableLiveData<String>()
    val _labelTo = MutableLiveData<String>()
    val _numTo = MutableLiveData<String>()

    //imutable val
    val labelFrom : LiveData<String> = _labelFrom
    val numFrom : LiveData<String> = _numFrom
    val labelTo : LiveData<String> = _labelTo
    val numTo : LiveData<String> = _numTo

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setDataPage(){
        //later data must from API or parse from previous form
        _labelFrom.value = "VA Tabungan Berjangka"
        _numFrom.value = "202020001001"
        _labelTo.value = "Main Account"
        _numTo.value = "202020001"
    }

    fun validateTransfer(amount: String) :Boolean = !amount.isBlank()

}
