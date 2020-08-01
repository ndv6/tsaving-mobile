package com.example.tsaving

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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
        val transferType = intent.getStringExtra("tfType")
        //adding oncreate event from model

        //live data must be handled here
        transferViewModel.apply {
            statusTransfer.observe(this@TransferActivity, Observer {
                if(statusTransfer.value == true){
//                    DialogHandling().basicAlert(this@TransferActivity, "Notification", "Transfer Success", "Close")
                    finish()
                    startActivity(getIntent())
                    Toast.makeText(this@TransferActivity,"Transfer Success", Toast.LENGTH_SHORT).show()
                }else{
                    DialogHandling().basicAlert(this@TransferActivity, "Notification", "Transfer Failed", "Close")
                }
            })
            labelFrom.observe(this@TransferActivity, Observer { tv_tf_from_name.text = it })
            numFrom.observe(this@TransferActivity, Observer { tv_tf_from_num.text = it })
            labelTo.observe(this@TransferActivity, Observer { tv_tf_to_name.text = it })
            numTo.observe(this@TransferActivity, Observer { tv_to_tf_num.text = it })
        }

        //logic for submit transfer
        btn_tf_transfer.setOnClickListener {
            val checkAmount = transferViewModel.validateTransfer(et_tf_amount_input.text.toString())
            if(!checkAmount){
                layout_tf_amout.setError("Please Input Amount")
//                DialogHandling().basicAlert(this@TransferActivity, "Notification", "Please Input Amount First", "Close")
            } else{
                layout_tf_amout.setError(null)
                transferViewModel.apiTransferToVa("2007307563001", 5000)
//                val statusTf = transferViewModel.statusTransfer // this is wrong bro
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
