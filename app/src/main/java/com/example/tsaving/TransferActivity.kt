package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_transfer.*
import java.lang.StringBuilder
import java.text.NumberFormat

class TransferActivity: Activity() {
    lateinit var et_amount: EditText
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
            //will implemented soon
        }
    }

}