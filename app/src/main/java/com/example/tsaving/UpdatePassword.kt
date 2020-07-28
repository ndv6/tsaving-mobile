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

        back_button.setOnClickListener{
            finish()
        }

        btn_updatepassword.setOnClickListener {
            val oldPassword = edit_oldpassword.text.toString()
            val newPassword = edit_newpassword.text.toString()
            val confirmPassword = edit_confirmpassword.text.toString()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(applicationContext,"please fill your password", Toast.LENGTH_SHORT).show()
            }
            if (newPassword != confirmPassword) {
                Toast.makeText(applicationContext,"password doesn't match", Toast.LENGTH_SHORT).show()

            }

        }
    }


}