package com.example.tsaving

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.DashboardViewModel
import com.example.tsaving.vm.TransferViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlinx.android.synthetic.main.activity_transfer.*


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
        val tf_type = intent.getStringExtra("tfType")
        val va_num = intent.getStringExtra("va_num")
        val va_label = intent.getStringExtra("va_label")
        //set page data here because it from previous page so dont need set at model
        if(tf_type == "main-to-va"){
            tv_tf_from_name.text = "Main Account"
            tv_tf_from_num.text = BaseApplication.accNumber
            tv_tf_to_name.text = va_label
            tv_tf_to_num.text  = va_num
        }
        else{
            tv_tf_from_name.text = va_label
            tv_tf_from_num.text = va_num
            tv_tf_to_name.text = "Main Account"
            tv_tf_to_num.text  = BaseApplication.accNumber
        }


        //live data must be handled here
        transferViewModel.apply {
            statusTransfer.observe(this@TransferActivity, Observer {
                if(statusTransfer.value == true){
                    finish()
                    startActivity(getIntent())
                    Toast.makeText(this@TransferActivity,"Transfer Success", Toast.LENGTH_SHORT).show()
                }else{
                    DialogHandling({}).basicAlert(this@TransferActivity, "Notification", "Transfer Failed", "Close")
                }
            })
            flagError.observe(this@TransferActivity)
        }

        //logic for submit transfer
        btn_tf_transfer.setOnClickListener {
            if(tf_type == "main-to-va"){
                transferViewModel.callTransferToMain(va_num.toString(), et_tf_amount_input.text.toString())
            }
            else if(tf_type == "main-to-va"){
                //semangat :)
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
            val stringAmount = s.toString()
            et_amount.setText(stringAmount.FormatDecimal())
            et_amount.addTextChangedListener(this)
            et_amount.setSelection(et_amount.getText().toString().length);
        }

    }

}
