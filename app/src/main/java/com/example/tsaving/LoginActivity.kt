package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.tsaving.vm.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
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
            val statusLogin = loginViewModel.validateLogin(et_login_email.text.toString(), et_login_password.text.toString(), {
                    errorName: ErrorName ->
                    when (errorName){
                        ErrorName.NullEmail ->{
                            layout_login_email.setError("Please Input Email")
//                            DialogHandling().basicAlert(this@LoginActivity, "Notification", "Please Input Email", "Close")
                        }
                        ErrorName.NullPassword ->{
                            DialogHandling().basicAlert(this@LoginActivity, "Notification", "Please Input Password", "Close")
                        }
                        ErrorName.InvalidEmail ->{
                            DialogHandling().basicAlert(this@LoginActivity, "Notification", "Invalid Email Format", "Close")
                        }
                        ErrorName.InvalidLogin ->{
                            DialogHandling().basicAlert(this@LoginActivity, "Notification", "Wrong Username / Passowrd", "Close")
                        }
                    }
            })
            if(statusLogin){
                Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
    }
}



