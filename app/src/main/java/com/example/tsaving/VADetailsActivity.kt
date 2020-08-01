package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tsaving.model.VirtualAccount
import kotlinx.android.synthetic.main.activity_va_details.*
import java.util.*

class VADetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_details)

        val va = intent.getParcelableExtra<VirtualAccount>("va_detail") as? VirtualAccount ?: VirtualAccount(0, "asd", "asd", 0, "b", "asd", Date(), Date())
        Log.i("va detail", va.toString())

        btn_vad_edit.setOnClickListener {
            val intent = Intent(this, EditVaActivity::class.java)
            startActivity(intent)
        }
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