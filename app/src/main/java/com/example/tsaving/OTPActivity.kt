package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.verify_email.*

class OTPActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify_email)

        btn_otp_send.setOnClickListener {
            startActivity(Intent(this@OTPActivity, DashboardActivity::class.java))
            finish()
        }
    }
}