package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tsaving.model.VirtualAccount
import kotlinx.android.synthetic.main.activity_va_details.*
import java.util.*

class VADetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_details)

        btn_vad_back.setOnClickListener{
            finish()
        }

        val va = intent.getParcelableExtra<VirtualAccount>("va_detail") as? VirtualAccount ?: VirtualAccount(0, "", "", 0, "", "", Date(), Date())
        tv_vad_accnumber.text = va.vaNum
        tv_vad_label.text = va.vaLabel
        tv_vad_vabalance.text = "Rp " + va.vaBalance.toString().FormatDecimal()
        layout_vad.setBackgroundColor(ContextCompat.getColor(this, colorStringToColor(va.vaColor)))

        btn_vad_edit.setOnClickListener {
            val intent = Intent(this, EditVaActivity::class.java)
            intent.putExtra("va_detail", va)
            startActivity(intent)
        }

        btn_vad_addbalance.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("va_detail", va)
            intent.putExtra("tf_type", "main-to-va")
            startActivity(intent)
        }

        btn_vad_trftomain.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("va_detail", va)
            intent.putExtra("tf_type", "va-to-main")
            startActivity(intent)
        }

    }

    fun colorStringToColor(color: String) : Int {
        when (color) {
            "Red" -> {
                return R.color.colorRed
            }
            "Blue" -> {
                return R.color.colorBlue
            }
            "Green" -> {
                return R.color.colorGreen
            }
            "Yellow" -> {
                return R.color.colorYellow
            }
            "Orange" -> {
                return R.color.colorOrange
            }
            "Purple" -> {
                return R.color.colorPurple
            }
        }
        return R.color.colorLight
    }
}