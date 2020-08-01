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
            //this one will modified after vadetail merged to develop
            intent.putExtra("tf_type", "main-to-va" )
            intent.putExtra("va_num", "2007307563001" )
            intent.putExtra("va_label", "Tabungan Rumah" )
            startActivity(intent)
        }
        btn_vad_trftomain.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            //this one will modified after vadetail merged to develop
            intent.putExtra("tf_type", "va-to-main" )
            intent.putExtra("va_num", "2007307563001" )
            intent.putExtra("va_label", "Tabungan Rumah" )
            startActivity(intent)
        }
    }
}