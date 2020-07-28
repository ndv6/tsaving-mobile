package com.example.tsaving


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update_password.*

class UpdatePassword : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        btn_up_back.setOnClickListener{
            finish()
        }

        btn_up_updatepassword.setOnClickListener {
            val oldPassword = et_up_oldpassword.text.toString()
            val newPassword = et_up_newpassword.text.toString()
            val confirmPassword = et_up_confirmpassword.text.toString()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(applicationContext,"please fill your password", Toast.LENGTH_SHORT).show()
            }
            if (newPassword != confirmPassword) {
                Toast.makeText(applicationContext,"password doesn't match", Toast.LENGTH_SHORT).show()

            }

        }
    }


}