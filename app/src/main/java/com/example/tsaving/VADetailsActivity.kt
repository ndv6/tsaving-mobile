package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.tsaving.model.VirtualAccount
import kotlinx.android.synthetic.main.activity_va_details.*

class VADetailsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_details)

//        TODO : Change get string extra to get parcelable
//        val va = intent.getParcelableExtra("va_detail") as? VirtualAccount
        val va_label = intent.getStringExtra("va_label")
        val va_accnum = intent.getStringExtra("va_accnum")
        val va_balance = intent.getStringExtra("va_balance")
        val va_color = intent.getStringExtra("va_color")
        var color : Int = 0

        when (va_color) {
            "Red" -> {
                color = R.color.colorRed
            }
            "Blue" -> {
                color = R.color.colorBlue
            }
            "Green" -> {
                color = R.color.colorGreen
            }
            "Yellow" -> {
                color = R.color.colorYellow
            }
            "Orange" -> {
                color = R.color.colorOrange
            }
            "Purple" -> {
                color = R.color.colorPurple
            }
        }

        tv_vad_accnumber.text = va_accnum
        tv_vad_label.text = va_label
        tv_vad_vabalance.text = va_balance
        layout_vad.setBackgroundColor(color)

        btn_vad_back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn_vad_edit.setOnClickListener {
            val intent = Intent(this, EditVaActivity::class.java)
            startActivity(intent)
        }
        btn_vad_addbalance.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }
        btn_vad_trftomain.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

    }
}