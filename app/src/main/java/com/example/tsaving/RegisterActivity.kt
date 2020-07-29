package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_register_signin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_register_signup.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, OTPActivity::class.java))
        }
    }
}