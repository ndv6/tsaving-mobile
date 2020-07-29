package com.example.tsaving

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.tsaving.vm.LoginViewModel
import com.example.tsaving.vm.TransferViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.log

class LoginActivity: AppCompatActivity(), LifecycleOwner {
    private val loginViewModel : LoginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        lifecycle.addObserver(loginViewModel)

        tv_login_signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btn_login_signin.setOnClickListener {
            val statusLogin = loginViewModel.validateLogin(et_login_email.text.toString(), et_login_password.text.toString())
//            println(status)
            if(statusLogin == 1){
                DialogHandling().basicAlert(this@LoginActivity, "Notification", "Please Input Email & Password", "Close")
            }
            else if(statusLogin == 2){
                DialogHandling().basicAlert(this@LoginActivity, "Notification", "Invalid Email Format", "Close")
            }
            else{
                Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
    }


}

