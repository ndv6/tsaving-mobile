package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_va_details.*

class VADetailsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_details)

        btn_vad_addbalance.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            //for modified later
//            val dataParam = listOf("VA Tabungan Rumah", "200728001001", "Main Account", "200728001" )
//            intent.putExtra("data_page", "ha")
//            intent.putExtra("test", arrayOf("2","3"))
            startActivity(intent)
        }
        btn_vad_trftomain.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }
    }
}