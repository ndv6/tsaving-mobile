package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_transfer.*

class TransferActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        txt_tf_from.setText("  "+ "TRANSFER FROM")
        txt_tf_to.setText("  "+ "TRANSFER TO")
        txt_tf_information.setText("  "+ "TRANSFER INFORMATION")


        //logic for submit transfer
        btn_transfer.setOnClickListener {
            val txtNumber = txt_tf_amount_input.text.toString()
            if(txtNumber.isBlank()){
                Toast.makeText(applicationContext,"Please Input The Amount", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(applicationContext,"Good To Go", Toast.LENGTH_SHORT).show()
            }
        }

        back_button_tf.setOnClickListener {
            this.finish()
        }
    }

}