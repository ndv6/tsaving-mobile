package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_transfer.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class TransferActivity: Activity() {
    lateinit var et_amount: EditText

    var formatter: DecimalFormat = DecimalFormat("#,###,###")
    var yourFormattedString: String = formatter.format(100000)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        et_amount = findViewById(R.id.et_tf_amount_input)
        et_amount.addTextChangedListener(textWatcher)
        //logic for submit transfer
        btn_tf_transfer.setOnClickListener {
            val amount = et_tf_amount_input.text.toString()
            if(amount.isBlank()){
                Toast.makeText(applicationContext,"Please Input The Amount", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(applicationContext,"Good To Go", Toast.LENGTH_SHORT).show()
            }
        }
        btn_tf_back.setOnClickListener {
            finish()
        }
    } //end override

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