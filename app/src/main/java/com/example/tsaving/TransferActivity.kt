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
import kotlinx.android.synthetic.main.activity_transfer.*
import java.text.NumberFormat
import java.util.*

class TransferActivity: AppCompatActivity(), LifecycleOwner {
    lateinit var et_amount: EditText

    private val transferViewModel : TransferViewModel = TransferViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        et_amount = findViewById(R.id.et_tf_amount_input)
        et_amount.addTextChangedListener(textWatcher)
        lifecycle.addObserver(transferViewModel)

        //adding oncreate event from model
        transferViewModel.apply {
            labelFrom.observe(this@TransferActivity, Observer { tv_tf_from_name.text = it })
            numFrom.observe(this@TransferActivity, Observer { tv_tf_from_num.text = it })
            labelTo.observe(this@TransferActivity, Observer { tv_tf_to_name.text = it })
            numTo.observe(this@TransferActivity, Observer { tv_to_tf_num.text = it })
        }

        //logic for submit transfer
        btn_tf_transfer.setOnClickListener {
            val amount = et_tf_amount_input.text.toString()
            val checkAmount = transferViewModel.validateTransfer(amount)
            if(!checkAmount){
                DialogHandling().basicAlert(this@TransferActivity, "Notification", "Please Input Amount First", "Close")
            } else{
                Toast.makeText(applicationContext,"Good To Go", Toast.LENGTH_SHORT).show()
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
            et_amount.removeTextChangedListener(this);
            val stringAmount = s.toString()
            et_amount.setText(stringAmount.formatDecimal())
            et_amount.addTextChangedListener(this)
            et_amount.setSelection(et_amount.getText().toString().length);
        }

    }

}
fun String.formatDecimal() : String{
    var stringAmount = this.replace(",", "")
    if(stringAmount.length > 3){
        stringAmount = NumberFormat.getNumberInstance(Locale.getDefault()).format(stringAmount.toDouble())
    }
    return stringAmount
}