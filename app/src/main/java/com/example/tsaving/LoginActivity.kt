package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_login_signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btn_login_signin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }
}