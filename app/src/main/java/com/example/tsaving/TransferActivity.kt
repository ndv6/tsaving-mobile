package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

import com.example.tsaving.model.VirtualAccount
import com.example.tsaving.vm.TransferViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlinx.android.synthetic.main.activity_transfer.*
import java.util.*


class TransferActivity: AppCompatActivity(), LifecycleOwner, CoroutineScope{

    lateinit var job : Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    lateinit var et_amount: EditText

    private val transferViewModel = TransferViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        et_amount = findViewById(R.id.et_tf_amount_input)
        et_amount.addTextChangedListener(textWatcher)
        lifecycle.addObserver(transferViewModel)
        job = Job()
        val tf_type = intent.getStringExtra("tf_type")
        //set page data here because it from previous page so dont need set at viewmodel
        //        get virtual account data from intent
        val va = intent.getParcelableExtra<VirtualAccount>("va_detail") as? VirtualAccount ?: VirtualAccount(0, "", "", 0, "", "", Date(), Date())

        if(tf_type == "main-to-va"){
            tv_tf_from_name.text = "Main Account"
            tv_tf_from_num.text = BaseApplication.accNumber
            tv_tf_to_name.text = va.vaLabel
            tv_tf_to_num.text  = va.vaNum
        }
        else{
            tv_tf_from_name.text = va.vaLabel
            tv_tf_from_num.text = va.vaNum
            tv_tf_to_name.text = "Main Account"
            tv_tf_to_num.text  = BaseApplication.accNumber
        }

        transferViewModel.apply {
            dataTFVA.observe(this@TransferActivity, Observer {
                if(it.status == "SUCCESS"){
                    val intent = Intent(this@TransferActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    Toast.makeText(this@TransferActivity, it.message, Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
                else{
                    DialogHandling({}).basicAlert(this@TransferActivity, "Transfer Failed", it.message, "close")
                }
            })
            dataTFVAToMain.observe(this@TransferActivity, Observer {
                if(it.status == "SUCCESS"){
                    val intent = Intent(this@TransferActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    Toast.makeText(this@TransferActivity, it.message, Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
                else{
                    DialogHandling({}).basicAlert(this@TransferActivity, "Transfer Failed", it.message, "close")
                }
            })
            flagError.observe(this@TransferActivity, Observer {
                if(it == ErrorName.NullAmount){
                    layout_tf_amout.setError("Please Input Amount")
                }
                else if(it == ErrorName.ErrorNetwork){
                    DialogHandling({}).basicAlert(this@TransferActivity, "Notification", "Network Error", "close")
                }
                else if(it == ErrorName.InvalidTransferToVA){
                    DialogHandling({}).basicAlert(this@TransferActivity, "Notification", "Transfer To Virtual Account Failed", "close")
                }
                else if(it == ErrorName.InvalidTransferToMain){
                    DialogHandling({}).basicAlert(this@TransferActivity, "Notification", "Transfer To Main Account Failed", "close")
                }
            })
            statusPB.observe(this@TransferActivity, Observer {
                if(it == true){
                    isLoading(true)
                }
                else{
                    isLoading(false)
                }
            })
        }

        //logic for submit transfer
        btn_tf_transfer.setOnClickListener {
            val amount = et_tf_amount_input.text.toString().replace(",", "")
            if(tf_type.toString() == "main-to-va"){
                transferViewModel.callTransferToVa(va.vaNum.toString() , amount)
            }
            else if(tf_type.toString() == "va-to-main"){
                transferViewModel.callTransferToMain(va.vaNum.toString(),amount)
            }
        }
        btn_tf_back.setOnClickListener {
            finish()
        }
    } //end override

    //add event when typing money give auto thousand separator
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            layout_tf_amout.setError(null)
            et_amount.removeTextChangedListener(this);
            val stringAmount = s.toString().trimStart('0')
            et_amount.setText(stringAmount.FormatDecimal())
            et_amount.addTextChangedListener(this)
            et_amount.setSelection(et_amount.getText().toString().length);
        }

    }
    fun isLoading(isFetching: Boolean) {
        if (isFetching) {
            cl_tf.visibility = View.GONE
            pb_tf.visibility = View.VISIBLE
        } else {
            cl_tf.visibility = View.VISIBLE
            pb_tf.visibility = View.GONE
        }
    }

}
